package edu.utep.cs.watson.braincomplain;


public class Answer
{
    int id;
    String text;
    String pipeline;
    double confidence;
    String entityTypes;//JSON string

    public Answer(int id, String text, String pipeline, double confidence, String entityTypes) {
        this.id = id;
        this.text = text;
        this.pipeline = pipeline;
        this.confidence = confidence;
        this.entityTypes = entityTypes;
    }

    public Answer()
    {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getEntityTypes() {
        return entityTypes;
    }

    public void setEntityTypes(String entityTypes) {
        this.entityTypes = entityTypes;
    }
}
