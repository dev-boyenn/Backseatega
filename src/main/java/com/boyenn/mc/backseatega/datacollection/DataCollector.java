package com.boyenn.mc.backseatega.datacollection;

import com.boyenn.mc.backseatega.render.Color;
import net.minecraft.client.MinecraftClient;

public abstract class DataCollector<T extends  SampleResult> {
    abstract public String getDisplayText();
    abstract public Color getDisplayColor();
    abstract T performSample();


    private T sampleResult;

    public T getSample() {
        if(MinecraftClient.getInstance().world == null){
            return null;
        }
        long time = MinecraftClient.getInstance().world.getTime();
        if(this.sampleResult == null || time - 20 > this.sampleResult.getWorldTime()){
            return this.sampleResult = this.performSample();
        }
        return this.sampleResult;
    }
}

