package edu.utep.cs.watson.braincomplain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WatsonResponseParser
{
    public static WatsonResponse parseWatsonJSON(String jsonStr)
    {
        WatsonResponse watsonResponse = new WatsonResponse();
        try {


            if (jsonStr != null) {
                JSONObject mainJsonObj = new JSONObject(jsonStr);
                JSONObject question = mainJsonObj.getJSONObject("question");

                watsonResponse.setAnswers(getAnswers(question));
                watsonResponse.setCategory(getStringField(question, "category"));
                watsonResponse.setDisambiguatedEntities(getStringField(question, "disambiguatedEntities"));
                watsonResponse.setErrorNotifications(getStringField(question, "errorNotifications"));
                watsonResponse.setEvidenceList(getEvidenceList(question));
                watsonResponse.setEvidenceRequest(getEvidenceRequest(question));
                watsonResponse.setFocuslist(getFocusList(question));
                watsonResponse.setFormattedAnswer(getBooleanField(question, "formattedAnswer"));
                watsonResponse.setId(getStringField(question, "id"));
                watsonResponse.setItems(getIntegerField(question, "id"));
                watsonResponse.setLatlist(getLatList(question));
                watsonResponse.setPassthru(getStringField(question, "passthru"));
                watsonResponse.setPipelineid(getStringField(question, "pipelineid"));
                watsonResponse.setQclasslist(getQclassList(question));
                watsonResponse.setQuestionText(getStringField(question, "questionText"));
                watsonResponse.setStatus(getStringField(question, "status"));
                watsonResponse.setSynonymList(getStringField(question, "synonymList"));
            }

        }
        catch (Exception e)
        {
            Log.e("Watson", "Error: " + e.getMessage());
        }
        return watsonResponse;
    }

    private static String getStringField(JSONObject jsonObject, String fieldName)
    {
        try
        {
            return jsonObject.get(fieldName).toString();
        }
        catch ( Exception e)
        {
            return "";
        }
    }

    private static boolean getBooleanField(JSONObject jsonObject, String fieldName)
    {
        try {
            return Boolean.parseBoolean(jsonObject.get(fieldName).toString());
        }
        catch ( Exception e)
        {
            return false;
        }
    }

    private static double getDoubleField(JSONObject jsonObject, String fieldName)
    {
        try {
            return Double.parseDouble(jsonObject.get(fieldName).toString());
        }
        catch ( Exception e)
        {
            return 0.0;
        }
    }

    private static int getIntegerField(JSONObject jsonObject, String fieldName)
    {
        try {
            return Integer.parseInt(jsonObject.get(fieldName).toString());
        }
        catch ( Exception e)
        {
            return 0;
        }
    }

    private static List<Answer> getAnswers(JSONObject question)
    {
        List<Answer> answers = new ArrayList<Answer>();
        try
        {

            JSONArray answersJsonArray = question.getJSONArray("answers");

            for (int i = 0; i < answersJsonArray.length(); i++)
            {
                JSONObject currentJsonAnswer = answersJsonArray.getJSONObject(i);
                Answer currentAnswer = getAnswerFromJsonObj(currentJsonAnswer);
                answers.add(currentAnswer);
            }
        }
        catch (Exception e)
        {

        }

        return answers;



    }

    private static Answer getAnswerFromJsonObj(JSONObject jsonAnswer)
    {
        Answer answer = new Answer();

        answer.setConfidence(getDoubleField(jsonAnswer, "confidence"));
        answer.setEntityTypes(getStringField(jsonAnswer, "entityTypes"));
        answer.setId(getIntegerField(jsonAnswer, "id"));
        answer.setPipeline(getStringField(jsonAnswer, "pipeline"));
        answer.setText(getStringField(jsonAnswer, "text" ));

        return answer;
    }

    private static List<AnswerEvidence> getEvidenceList(JSONObject question)
    {
        List<AnswerEvidence> answerEvidenceList = new ArrayList<AnswerEvidence>();
        try
        {

            JSONArray answerEvidenceJsonArray = question.getJSONArray("evidencelist");

            for (int i = 0; i < answerEvidenceJsonArray.length(); i++)
            {
                JSONObject currentJsonAnswerEvidence = answerEvidenceJsonArray.getJSONObject(i);
                AnswerEvidence currentAnswerEvidence = getAnswerEvidenceFromJsonObj(currentJsonAnswerEvidence);
                answerEvidenceList.add(currentAnswerEvidence);
            }
        }
        catch (Exception e)
        {

        }

        return answerEvidenceList;

    }

    private static AnswerEvidence getAnswerEvidenceFromJsonObj(JSONObject jsonAnswerEvidence)
    {
        AnswerEvidence answerEvidence = new AnswerEvidence();

        answerEvidence.setCopyright(getStringField(jsonAnswerEvidence, "copyright"));
        answerEvidence.setDocument(getStringField(jsonAnswerEvidence, "document"));
        answerEvidence.setId(getStringField(jsonAnswerEvidence, "id"));
        answerEvidence.setMetadataMap(getMetadataMap(jsonAnswerEvidence));
        answerEvidence.setTitle(getStringField(jsonAnswerEvidence, "title"));
        answerEvidence.setTermsOfUse(getStringField(jsonAnswerEvidence, "termsOfUse"));
        answerEvidence.setText(getStringField(jsonAnswerEvidence, "text"));
        answerEvidence.setValue(getDoubleField(jsonAnswerEvidence, "value"));


        return answerEvidence;
    }

    private static MetadataMap getMetadataMap(JSONObject jsonObject)
    {
        MetadataMap metadataMap = new MetadataMap();

        try
        {
            jsonObject = jsonObject.getJSONObject("metadataMap");
            metadataMap.setAbstractStr(getStringField(jsonObject, "abstract"));
            metadataMap.setAuthor(getStringField(jsonObject, "author"));
            metadataMap.setCorpusName(getStringField(jsonObject, "corpusName"));
            metadataMap.setDeepqaid(getStringField(jsonObject, "deepqaid"));
            metadataMap.setDescription(getStringField(jsonObject, "description"));
            metadataMap.setDOCNO(getStringField(jsonObject, "DOCNO"));
            metadataMap.setFileName(getStringField(jsonObject, "fileName"));
            metadataMap.setOriginalfile(getStringField(jsonObject, "originalfile"));
            metadataMap.setTitle(getStringField(jsonObject, "title"));

        }
        catch (Exception e)
        {

        }
        return metadataMap;
    }

    private static EvidenceRequest getEvidenceRequest(JSONObject question)
    {

        EvidenceRequest evidenceRequest = new EvidenceRequest();

        try
        {
            JSONObject evidenceRequestJsonObj = question.getJSONObject("evidenceRequest");
            evidenceRequest.setItems(getIntegerField(evidenceRequestJsonObj, "items"));
            evidenceRequest.setProfile(getStringField(evidenceRequestJsonObj, "profile"));
        }
        catch (Exception e)
        {}

        return evidenceRequest;

    }

    private static List<String> getFocusList(JSONObject question)
    {
        List<String> focusList = new ArrayList<String>();

        try
        {
            JSONArray focusListJsonArray = question.getJSONArray("focuslist");

            focusList.addAll(getListFromJsonArray(focusListJsonArray, "value"));
        }
        catch (Exception e)
        {}

        return focusList;

    }

    private static List<String> getLatList(JSONObject question)
    {
        List<String> latList = new ArrayList<String>();

        try
        {
            JSONArray latListJsonArray = question.getJSONArray("latlist");

            latList.addAll(getListFromJsonArray(latListJsonArray, "value"));
        }
        catch (Exception e)
        {}

        return latList;

    }


    private static List<String> getQclassList(JSONObject question)
    {
        List<String> qClassList = new ArrayList<String>();

        try
        {
            JSONArray qClassListJsonArray = question.getJSONArray("qclasslist");

            qClassList.addAll(getListFromJsonArray(qClassListJsonArray, "value"));
        }
        catch (Exception e)
        {}

        return qClassList;

    }

    private static List<String> getListFromJsonArray(JSONArray jsonArray, String valueToExtract)
    {
        List<String> list = new ArrayList<String>();

        try
        {

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject latListJsonObj = jsonArray.getJSONObject(i);
                list.add(getStringField(latListJsonObj, valueToExtract));
            }
        }
        catch (Exception e)
        {}

        return list;
    }

}

