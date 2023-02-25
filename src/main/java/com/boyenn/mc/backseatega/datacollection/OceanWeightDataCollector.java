package com.boyenn.mc.backseatega.datacollection;

import com.boyenn.mc.backseatega.render.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.biome.*;

public class OceanWeightDataCollector extends DataCollector<BinarySampleResult> {

    
    @Override
    public String getDisplayText() {
        BinarySampleResult binarySampleResult = this.getSample();
        if(binarySampleResult == null){
            return "";
        }
        return "OceanWeight: " + binarySampleResult.getPositiveSamples() + "/"+binarySampleResult.getTotalSamples();
    }

    @Override
    public Color getDisplayColor() {
        BinarySampleResult binarySampleResult = this.getSample();

        if(binarySampleResult== null){
            return Color.RED;
        }
        return new Color(java.awt.Color.HSBtoRGB((float)binarySampleResult.getPositiveSamples()/ binarySampleResult.getTotalSamples()/3f, 1f, 1f));

    }
    BinarySampleResult performSample(){
        final ClientWorld world = MinecraftClient.getInstance().world;
        return SamplingDataCollectionHelper.binarySampleAroundPlayer(10,16,(blockPos -> {
            Biome biome = world.getBiome(blockPos);
            return biome instanceof OceanBiome
                    || biome instanceof DeepOceanBiome
                    || biome instanceof WarmOceanBiome
                    || biome instanceof DeepWarmOceanBiome
                    || biome instanceof LukewarmOceanBiome
                    || biome instanceof DeepLukewarmOceanBiome
                    || biome instanceof ColdOceanBiome
                    || biome instanceof DeepColdOceanBiome
                    || biome instanceof FrozenOceanBiome
                    || biome instanceof DeepFrozenOceanBiome;
        }));

    }

}