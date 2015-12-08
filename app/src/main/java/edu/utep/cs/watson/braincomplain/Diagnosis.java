package edu.utep.cs.watson.braincomplain;

/**
 * Created by Jason on 12/7/2015.
 */
public class Diagnosis implements Comparable {
    public final String name;
    private double weight = 0.0;

    public Diagnosis(String name) {
        this.name = name;
    }

    public void addWeight(double w) {
        weight += w;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object another) {
        if(another == null)
            return 0;
        if(!(another instanceof Diagnosis))
            return 0;
        Diagnosis compare = (Diagnosis)another;
        if(weight < compare.weight)
            return -1;
        if(weight > compare.weight)
            return 1;
        return 0;
    }
}
