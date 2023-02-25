package com.boyenn.mc.backseatega.datacollection;

import com.boyenn.mc.backseatega.render.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AverageDepthDataCollector extends DataCollector<AverageSampleResult> {

    
    @Override
    public String getDisplayText() {
        AverageSampleResult sampleResult = this.getSample();
        if(sampleResult == null){
            return "";
        }
        return "OceanDepth: " +sampleResult.getAverage();
    }
    
    @Override
    public Color getDisplayColor() {
        AverageSampleResult sampleResult = this.getSample();

        if(sampleResult== null){
            return Color.RED;
        }

        float oceanDepthLiking = Math.max(1-(Math.max(sampleResult.getAverage() - 30,0)/20),0);
        return new Color(java.awt.Color.HSBtoRGB(oceanDepthLiking/3f, 1f, 1f));

    }

    AverageSampleResult performSample(){
        final ClientWorld world = MinecraftClient.getInstance().world;
        return SamplingDataCollectionHelper.averageSampleAroundPlayer(10,16,(blockPos -> {
            Biome biome = world.getBiome(blockPos);
            if( biome instanceof OceanBiome
                    || biome instanceof DeepOceanBiome
                    || biome instanceof WarmOceanBiome
                    || biome instanceof DeepWarmOceanBiome
                    || biome instanceof LukewarmOceanBiome
                    || biome instanceof DeepLukewarmOceanBiome
                    || biome instanceof ColdOceanBiome
                    || biome instanceof DeepColdOceanBiome
                    || biome instanceof FrozenOceanBiome
                    || biome instanceof DeepFrozenOceanBiome){

                try {

                    WorldChunk chunk = world.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);

                    if(chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ())<0){
                        Heightmap.populateHeightmaps(chunk, Collections.singleton(Heightmap.Type.OCEAN_FLOOR));
                    }
                    return Optional.of((float) chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR,blockPos.getX(),blockPos.getZ()));
                }catch (Exception ex){

                }
            }
            return Optional.empty();
        }));

    }

}