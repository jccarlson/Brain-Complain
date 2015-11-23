package edu.utep.cs.watson.braincomplain;


public class AnswerEvidence
{
    double value;
    String text;
    String id;
    String title;
    String document;
    String copyright;
    String termsOfUse;
    MetadataMap metadataMap;

    public AnswerEvidence(double value, String text, String id, String title, String document, String copyright, String termsOfUse, MetadataMap metadataMap) {
        this.value = value;
        this.text = text;
        this.id = id;
        this.title = title;
        this.document = document;
        this.copyright = copyright;
        this.termsOfUse = termsOfUse;
        this.metadataMap = metadataMap;
    }

    public AnswerEvidence() {
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public MetadataMap getMetadataMap() {
        return metadataMap;
    }

    public void setMetadataMap(MetadataMap metadataMap) {
        this.metadataMap = metadataMap;
    }
}

