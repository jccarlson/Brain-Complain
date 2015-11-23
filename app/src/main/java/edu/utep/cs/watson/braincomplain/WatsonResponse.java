package edu.utep.cs.watson.braincomplain;

import java.util.List;

public class WatsonResponse
{
    List<String> qclasslist;
    List<String> focuslist;
    List<String> latlist;
    List<AnswerEvidence> evidenceList;
    String synonymList; //Notice that this string is still a JSON string. You need to extend the project if you want this to be further processed
    String disambiguatedEntities; // JSON string
    String pipelineid;
    boolean formattedAnswer;
    String category;
    int items;
    String status;
    String id;
    String questionText;
    EvidenceRequest evidenceRequest;
    List<Answer> answers;
    String errorNotifications;//JSON string
    String passthru;

    public WatsonResponse(List<String> qclasslist, List<String> focuslist, List<String> latlist, List<AnswerEvidence> evidenceList, String synonymList, String disambiguatedEntities, String pipelineid, boolean formattedAnswer, String category, int items, String status, String id, String questionText, EvidenceRequest evidenceRequest, List<Answer> answers, String errorNotifications, String passthru) {
        this.qclasslist = qclasslist;
        this.focuslist = focuslist;
        this.latlist = latlist;
        this.evidenceList = evidenceList;
        this.synonymList = synonymList;
        this.disambiguatedEntities = disambiguatedEntities;
        this.pipelineid = pipelineid;
        this.formattedAnswer = formattedAnswer;
        this.category = category;
        this.items = items;
        this.status = status;
        this.id = id;
        this.questionText = questionText;
        this.evidenceRequest = evidenceRequest;
        this.answers = answers;
        this.errorNotifications = errorNotifications;
        this.passthru = passthru;
    }

    public WatsonResponse() {
    }

    public List<String> getQclasslist() {
        return qclasslist;
    }

    public void setQclasslist(List<String> qclasslist) {
        this.qclasslist = qclasslist;
    }

    public List<String> getFocuslist() {
        return focuslist;
    }

    public void setFocuslist(List<String> focuslist) {
        this.focuslist = focuslist;
    }

    public List<String> getLatlist() {
        return latlist;
    }

    public void setLatlist(List<String> latlist) {
        this.latlist = latlist;
    }

    public List<AnswerEvidence> getEvidenceList() {
        return evidenceList;
    }

    public void setEvidenceList(List<AnswerEvidence> evidenceList) {
        this.evidenceList = evidenceList;
    }

    public String getSynonymList() {
        return synonymList;
    }

    public void setSynonymList(String synonymList) {
        this.synonymList = synonymList;
    }

    public String getDisambiguatedEntities() {
        return disambiguatedEntities;
    }

    public void setDisambiguatedEntities(String disambiguatedEntities) {
        this.disambiguatedEntities = disambiguatedEntities;
    }

    public String getPipelineid() {
        return pipelineid;
    }

    public void setPipelineid(String pipelineid) {
        this.pipelineid = pipelineid;
    }

    public boolean isFormattedAnswer() {
        return formattedAnswer;
    }

    public void setFormattedAnswer(boolean formattedAnswer) {
        this.formattedAnswer = formattedAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public EvidenceRequest getEvidenceRequest() {
        return evidenceRequest;
    }

    public void setEvidenceRequest(EvidenceRequest evidenceRequest) {
        this.evidenceRequest = evidenceRequest;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getErrorNotifications() {
        return errorNotifications;
    }

    public void setErrorNotifications(String errorNotifications) {
        this.errorNotifications = errorNotifications;
    }

    public String getPassthru() {
        return passthru;
    }

    public void setPassthru(String passthru) {
        this.passthru = passthru;
    }
}  
