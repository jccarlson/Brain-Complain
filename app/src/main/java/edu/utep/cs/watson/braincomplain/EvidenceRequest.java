package edu.utep.cs.watson.braincomplain;

public class EvidenceRequest {
    int items;
    String profile;

    public EvidenceRequest(int items, String profile) {
        this.items = items;
        this.profile = profile;
    }

    public EvidenceRequest()
    {}

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}

