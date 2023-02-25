package com.boyenn.mc.backseatega.datacollection;

import com.boyenn.mc.backseatega.render.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Collections;
import java.util.Optional;

public class RavineCountDataCollector extends DataCollector<BinarySampleResult> {

    
    @Override
    public String getDisplayText() {
        if( this.getSample() == null){
            return "";
        }
        return "RavineSamples: " +this.getSample().getPositiveSamples();
    }
    
    @Override
    public Color getDisplayColor() {

        if(this.getSample()== null){
            return Color.RED;
        }

        float ravineLiking = Math.max(1-Math.max(10f - this.getSample().getPositiveSamples(),0)/10,0);
        return new Color(java.awt.Color.HSBtoRGB(ravineLiking/3f, 1f, 1f));

    }
    BinarySampleResult performSample(){
        final ClientWorld world = MinecraftClient.getInstance().world;
        return SamplingDataCollectionHelper.binarySampleAroundPlayer(100,2,(blockPos -> {
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
                    || biome instanceof DeepFrozenOceanBiome) {

                try {
                    WorldChunk chunk = world.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
                    if(chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ())<0){
                        Heightmap.populateHeightmaps(chunk, Collections.singleton(Heightmap.Type.OCEAN_FLOOR));
                    }
                    return chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ()) == 10;
                } catch (Exception ex) {

                }
            }
            return false;
        }));

    }

}