package com.boyenn.mc.backseatega;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class DataLogger {

    public void writeLogFile(List<Record> recordList, UUID uuid){
        File recordFile = new File(new File(System.getProperty("user.home")), uuid + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("date", System.currentTimeMillis());
        JsonArray recordArray = new JsonArray();
        recordList.forEach(record -> {
            JsonObject recordObject = new JsonObject();
            recordObject.addProperty("time",record.worldTime);

            JsonArray splitsArray = new JsonArray();
            record.splits.forEach(split->{
                JsonObject splitObject = new JsonObject();
                splitObject.addProperty("name",split.name);
                splitObject.addProperty("time",split.worldTime);
                splitsArray.add(splitObject);
            });

            recordObject.add("splits",splitsArray);
            recordObject.add("surroundingData",record.surroundingData);
            recordArray.add(recordObject);
        });
        jsonObject.add("records",recordArray);
        try {
            FileUtils.writeStringToFile(recordFile, gson.toJson(jsonObject), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
