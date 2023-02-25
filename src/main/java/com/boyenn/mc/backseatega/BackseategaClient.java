package com.boyenn.mc.backseatega;

import com.boyenn.mc.backseatega.datacollection.*;
import com.boyenn.mc.backseatega.render.DataRenderer;
import com.google.common.collect.Lists;

import java.util.List;

public class BackseategaClient {
    public static DataRenderer DATA_RENDERER = new DataRenderer();
    public static List<DataCollector> DATA_COLLECTORS =  Lists.newArrayList(new TestDataCollector(), new OceanWeightDataCollector(), new AverageDepthDataCollector(), new RavineCountDataCollector());

    public static DataLogger DATA_LOGGER = new DataLogger();
    public static SplitTracker SPLIT_TRACKER = new SplitTracker();
}
