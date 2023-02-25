package com.boyenn.mc.backseatega.datacollection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.function.Function;

public class SamplingDataCollectionHelper {
    public static BinarySampleResult binarySampleAroundPlayer(int radius, int step, Function<BlockPos, Boolean> sampler) {

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;
        if (player == null || world == null) {
            return null;
        }
        BlockPos blockPos = player.getBlockPos();
        int totalSamples = 0;
        int positiveSamples = 0;
        for (int x = blockPos.getX() - radius * step; x < blockPos.getX() + radius * step; x += step) {
            for (int z = blockPos.getZ() - radius * step; z < blockPos.getZ() + radius * step; z += step) {
                BlockPos targetBlockPos = new BlockPos(x, blockPos.getY(), z);
                if(!world.getChunkManager().isChunkLoaded(world.getChunk(targetBlockPos).getPos().x, world
                        .getChunk(targetBlockPos).getPos().z)){
                    continue;
                }
                totalSamples ++;
                if (sampler.apply(targetBlockPos)) {
                    positiveSamples++;

                }

            }
        }
        return SampleResult.Binary(positiveSamples, totalSamples, world.getTime());
    }

    public static AverageSampleResult averageSampleAroundPlayer(int radius, int step, Function<BlockPos, Optional<Float>> sampler) {

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;
        if (player == null || world == null) {
            return null;
        }
        BlockPos blockPos = player.getBlockPos();
        AverageSampleResult sampleResult = SampleResult.Average(world.getTime());
        for (int x = blockPos.getX() - radius * step; x < blockPos.getX() + radius * step; x += step) {
            for (int z = blockPos.getZ() - radius * step; z < blockPos.getZ() + radius * step; z += step) {
                BlockPos targetBlockPos = new BlockPos(x, blockPos.getY(), z);
                if(!world.getChunkManager().isChunkLoaded(world.getChunk(targetBlockPos).getPos().x, world
                        .getChunk(targetBlockPos).getPos().z)){
                    continue;
                }
                Optional<Float> apply = sampler.apply(targetBlockPos);
                apply.ifPresent(sampleResult::addSample);
            }
        }
        return sampleResult;
    }
}


