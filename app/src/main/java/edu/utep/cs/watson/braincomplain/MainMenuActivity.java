package edu.utep.cs.watson.braincomplain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {
    ArrayList<String> menu_list = new ArrayList<String>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        menu_list.add(getString(R.string.ask_watson));
        menu_list.add(getString(R.string.maps_psych));
        menu_list.add(getString(R.string.call_SAMHSA));
        menu_list.add("Watson Diagnose");
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_list);
        listView.setAdapter(arrayAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdapterView.OnItemClickListener
                mMessageClickedHandler =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent,
                                            View v,
                                            int position,
                                            long id) {
                        switch(position) {
                            case 0: startAskWatson();
                                    break;
                            case 1: startMapIntent();
                                    break;
                            case 2: startCallIntent();
                                    break;
                            case 3: startDiagnoseIntent();
                        }
                    }
                };

        listView.setOnItemClickListener(mMessageClickedHandler);
    }

    private void startAskWatson() {
        Intent intent = new Intent(this, WatsonQueryActivity.class);
        startActivity(intent);

    }

    private void startMapIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=psychiatrist+office+near+me"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void startCallIntent() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + getString(R.string.SAMHSA_number)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private void startDiagnoseIntent() {
        Intent intent = new Intent(this, WatsonDiagnose.class);
        startActivity(intent);
    }

    private void startQuitIntent() {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.universal_expandable_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ask_watson) {
            startAskWatson();
        }
        if (id == R.id.action_maps_psych) {
            startMapIntent();
        }
        if (id == R.id.action_call_apa) {
            startCallIntent();
        }
        if (id == R.id.action_watson_diagnose) {
            startDiagnoseIntent();
        }
        if (id == R.id.action_quit) {
            startQuitIntent();
        }
        return super.onOptionsItemSelected(item);
    }

}
