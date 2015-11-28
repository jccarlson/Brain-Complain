package edu.utep.cs.watson.braincomplain;
import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class WatsonDiagnoseFragment extends Fragment {

    private final String mLogTag = "Watson: ";
    private String mWatsonQueryString = "";
    private String mWatsonAnswerString = "";
    private boolean mIsQuerying = false;
    private ListView symptomDisplay;
    private ArrayList<Symptom> symptomList = new ArrayList<>();

    public class Symptom {
        public String symptom = "Symptom";
        public String duration = "Duration";
    }

    static interface WatsonQueryCallbacks {
        void onPreExecute();

        void onProgressUpdate(int percent);

        void onCancelled();

        void onPostExecute();
    }

    private WatsonDiagnose mCallbacks;
    private WatsonQuery mQuery;

    public WatsonDiagnoseFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (WatsonDiagnose) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_watson_diagnose, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // restore answer text if it exists in memory
        if (mWatsonAnswerString.length() > 0) {
            TextView watsonResponse = (TextView) getActivity().findViewById(R.id.WatsonDiagnoseResponse);
            watsonResponse.setText(mWatsonAnswerString);
        }

        symptomDisplay = (ListView) getActivity().findViewById(R.id.SymptomList);
        symptomDisplay.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
        symptomDisplay.setItemsCanFocus(true);
        getActivity().findViewById(R.id.AddSymptomButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSymptom();
            }
        });

        if (symptomList.size() == 0) {
            symptomList.add(new Symptom());
        }

        symptomDisplay.setAdapter(new SymptomAdapter(this.getContext(), symptomList));

        getActivity().findViewById(R.id.RemoveSymptomButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSymptom();
            }
        });
        // event binding for submit button
        getActivity().findViewById(R.id.DiagnoseSymptomButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomDisplay.setAdapter(new SymptomAdapter(v.getContext(), symptomList));
                if (!mIsQuerying) {
                    mIsQuerying = true;
                    mWatsonQueryString = buildQuestion();
                    mQuery = new WatsonQuery();
                    mQuery.execute();
                }
                hideSoftKeyboard(getActivity());

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    private class WatsonQuery extends AsyncTask<Void, Integer, String> {

        private SSLContext context;
        private HttpsURLConnection connection;
        private String jsonData;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... ignore) {

            // establish SSL trust (insecure for demo)
            try {
                context = SSLContext.getInstance("TLS");
                context.init(null, trustAllCerts, new java.security.SecureRandom());
            } catch (java.security.KeyManagementException e) {
                e.printStackTrace();
            } catch (java.security.NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            try {
                // Default HTTPS connection option values
                URL watsonURL = new URL(getString(R.string.user_watson_server_instance));
                int timeoutConnection = 30000;
                connection = (HttpsURLConnection) watsonURL.openConnection();
                connection.setSSLSocketFactory(context.getSocketFactory());
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(timeoutConnection);
                connection.setReadTimeout(timeoutConnection);

                // Watson specific HTTP headers
                connection.setRequestProperty("X-SyncTimeout", "30");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Basic " + getEncodedValues(getString(R.string.user_id), getString(R.string.user_password)));
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Cache-Control", "no-cache");

                OutputStream out = connection.getOutputStream();
                String query = "{\"question\": {\"questionText\": \"" + mWatsonQueryString + "\"}}";
                out.write(query.getBytes());
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            int responseCode;
            try {
                if (connection != null) {
                    responseCode = connection.getResponseCode();
                    Log.i(mLogTag, "Server Response Code: " + Integer.toString(responseCode));

                    switch (responseCode) {
                        case 200:
                            // successful HTTP response state
                            InputStream input = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                                response.append('\r');

                            }
                            reader.close();

                            Log.i(mLogTag, "Watson Output: " + response.toString());
                            jsonData = response.toString();

                            break;
                        default:
                            // Do Stuff
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // received data, deliver JSON to PostExecute
            if (jsonData != null) {
                return jsonData;
            }

            // else, hit HTTP error, handle in PostExecute by doing null check
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... percent) {
            if (mCallbacks != null) {
                mCallbacks.onProgressUpdate(percent[0]);
            }
        }

        @Override
        protected void onCancelled() {
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
        }

        @Override
        protected void onPostExecute(String json) {
            mIsQuerying = false;
            if (mCallbacks != null) {
                mCallbacks.onPostExecute();
            }

            try {
                if (json != null) {
                    JSONObject watsonResponse = new JSONObject(json);
                    JSONObject question = watsonResponse.getJSONObject("question");
                    JSONArray evidenceArray = question.getJSONArray("evidencelist");
                    JSONObject mostLikelyValue = evidenceArray.getJSONObject(0);
                    mWatsonAnswerString = mostLikelyValue.get("text").toString();
                    TextView textView = (TextView) getActivity().findViewById(R.id.WatsonDiagnoseResponse);
                    textView.setText(mWatsonAnswerString);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                // No valid answern
                printTryDifferentQuestion();
            }
        }

        /*
         *  Accepts all HTTPS certs. Do NOT use in production!!!
         */
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        }};
    }

    private void printTryDifferentQuestion() {
        TextView textView = (TextView) getActivity().findViewById(R.id.WatsonDiagnoseResponse);
        textView.setText("Please try a different question.");
    }

    private String getEncodedValues(String user_id, String user_password) {
        String textToEncode = user_id + ":" + user_password;
        byte[] data = null;
        try {
            data = textToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

     void addNewSymptom() {
        symptomList.add(new Symptom());
        symptomDisplay.setAdapter(new SymptomAdapter(symptomDisplay.getContext(), symptomList));
    }

    private void removeSymptom() {
        if(symptomList.size() > 1){
            symptomList.remove(symptomList.size()-1);
            symptomDisplay.setAdapter(new SymptomAdapter(symptomDisplay.getContext(), symptomList));
        }
    }

    private String buildQuestion() {
        String question = "What disorder has symptoms ";
        int i;
        for(i = 0; i < symptomList.size() - 1; i++) {
            String s = symptomList.get(i).symptom;
            String d = symptomList.get(i).duration;
            question += (s + " of duration " + d + ", ");
        }

        String s = symptomList.get(i).symptom;
        String d = symptomList.get(i).duration;
        question += ("and " + s + " of duration " + d + "?");
        TextView textView = (TextView) getActivity().findViewById(R.id.WatsonDiagnoseResponse);
        textView.setText("Asking Watson: " + question);
        return question;
    }

    public class SymptomAdapter extends ArrayAdapter<Symptom> {
        private final Context context;
        private final ArrayList<Symptom> values;

        public SymptomAdapter(Context context, ArrayList<Symptom> values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_view, parent, false);

            EditText symptomField = (EditText)rowView.findViewById(R.id.symptomField);
            EditText durationField = (EditText)rowView.findViewById(R.id.durationField);

            symptomField.setText(values.get(position).symptom);
            symptomField.setId(position);
            durationField.setText(values.get(position).duration);
            durationField.setId(position);

            symptomField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        symptomList.get(v.getId()).symptom = (String) ((EditText) v).getText().toString();
                    } else {
                        ((EditText) v).selectAll();
                    }

                }
            });

            durationField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        symptomList.get(v.getId()).duration = (String) ((EditText) v).getText().toString();
                    } else {
                        ((EditText) v).selectAll();
                    }
                }
            });

            return rowView;
        }
    }
}