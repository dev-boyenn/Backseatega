package com.boyenn.mc.backseatega.datacollection;

import com.boyenn.mc.backseatega.render.Color;

public class TestDataCollector extends DataCollector<SampleResult> {
    @Override
    public String getDisplayText() {
        return "Test Data Collector";
    }

    @Override
    public Color getDisplayColor() {
        return Color.RED;
    }

    @Override
    BinarySampleResult performSample() {
        return null;
    }
}