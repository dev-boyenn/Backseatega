package com.boyenn.mc.backseatega.datacollection;

public class SampleResult {
    protected long worldTime;

    public long getWorldTime() {
        return worldTime;
    }

    public static SampleResult NULL = new SampleResult();

    protected SampleResult() {
        this.worldTime = 0;
    }

    public static BinarySampleResult Binary(int positiveSamples, int totalSamples, long worldTime) {
        BinarySampleResult sampleResult = new BinarySampleResult();
        sampleResult.setPositiveSamples(positiveSamples);
        sampleResult.setTotalSamples(totalSamples);
        sampleResult.worldTime = worldTime;
        return sampleResult;
    }

    public static AverageSampleResult Average(long worldTime) {
        AverageSampleResult sampleResult = new AverageSampleResult();
        sampleResult.worldTime = worldTime;
        return sampleResult;
    }
}
