package com.boyenn.mc.backseatega.datacollection;

public class AverageSampleResult extends SampleResult {
    public int totalSamples;
    public float totalValues;

    public float getAverage() {
        return this.totalValues / (float) totalSamples;
    }

    public void addSample(float value) {
        this.totalValues += value;
        this.totalSamples++;

    }
}
