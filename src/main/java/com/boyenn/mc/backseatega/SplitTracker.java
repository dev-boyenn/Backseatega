package com.boyenn.mc.backseatega;

import com.boyenn.mc.backseatega.datacollection.AverageDepthDataCollector;
import com.boyenn.mc.backseatega.datacollection.OceanWeightDataCollector;
import com.boyenn.mc.backseatega.datacollection.RavineCountDataCollector;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SplitTracker {

    private List<Record> recordList;
    private List<Split> achievedSplits;
    private final int TICK_INTERVAL = 100;
    private int lastRecordedTick = 1;
    private UUID uuid;
    private boolean isRunning = false;
    public void registerSplit(String splitName){
        if(!isRunning){
            return;
        }
        this.achievedSplits.add(new Split(splitName,lastRecordedTick));
        this.addRecord();
        BackseategaClient.DATA_LOGGER.writeLogFile(this.recordList, this.uuid);
    }

    private void addRecord(){
        JsonObject surroundingData = new JsonObject();

        AverageDepthDataCollector averageDepthDataCollector = new AverageDepthDataCollector();
        OceanWeightDataCollector oceanWeightDataCollector = new OceanWeightDataCollector();
        RavineCountDataCollector ravineCountDataCollector = new RavineCountDataCollector();

        surroundingData.addProperty("averageOceanDepth",averageDepthDataCollector.getSample().getAverage());
        surroundingData.addProperty("oceanWeight",oceanWeightDataCollector.getSample().getPercentage());
        surroundingData.addProperty("ravineSampleCount",ravineCountDataCollector.getSample().getPositiveSamples());
        this.recordList.add(new Record(this.lastRecordedTick, new ArrayList<>(this.achievedSplits),surroundingData));

    }


    public void start(){
        this.uuid = UUID.randomUUID();
        this.recordList = new ArrayList<>();
        this.achievedSplits = new ArrayList<>();
        this.isRunning = true;
        this.lastRecordedTick = 0;

    }
    public void complete(){
        this.isRunning = false;
        BackseategaClient.DATA_LOGGER.writeLogFile(this.recordList,this.uuid);
    }

    public void tick() {
        if(!isRunning){
            return;
        }
        if (++this.lastRecordedTick % TICK_INTERVAL == 0) {
            addRecord();
        }
    }
}
class Split implements Serializable{
    public String name;
    public long worldTime;

    public Split(String name, long worldTime) {
        this.name = name;
        this.worldTime = worldTime;
    }
}
class Record implements Serializable {
    public long worldTime;

    public Record(long worldTime, List<Split> splits, JsonObject surroundingData) {
        this.worldTime = worldTime;
        this.splits = splits;
        this.surroundingData = surroundingData;
    }

    public List<Split> splits;
    public JsonObject surroundingData;
}