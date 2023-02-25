package com.boyenn.mc.backseatega.datacollection;

public class BinarySampleResult extends SampleResult {
    public void setTotalSamples(int totalSamples) {
        this.totalSamples = totalSamples;
    }

    private int totalSamples;

    public void setPositiveSamples(int positiveSamples) {
        this.positiveSamples = positiveSamples;
    }

    private int positiveSamples;


    public int getTotalSamples() {
        return totalSamples;
    }

    public int getPositiveSamples() {
        return positiveSamples;
    }

    public float getPercentage(){
        return (float)positiveSamples / (float)totalSamples;
    }
}
