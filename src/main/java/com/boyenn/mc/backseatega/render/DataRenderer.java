package com.boyenn.mc.backseatega.render;

import com.boyenn.mc.backseatega.BackseategaClient;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.concurrent.atomic.AtomicReference;


@Environment(EnvType.CLIENT)
public class DataRenderer {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @SuppressWarnings("deprecation")
    public void draw (){
        RenderSystem.pushMatrix();
        MatrixStack matrixStack = new MatrixStack();

        AtomicReference<Float> textY = new AtomicReference<>((float) 100);
        BackseategaClient.DATA_COLLECTORS.forEach(dataCollector -> {
            client.textRenderer.draw(matrixStack,dataCollector.getDisplayText(),0f, textY.get(),dataCollector.getDisplayColor().intValue);
            textY.updateAndGet(v -> v + 12);
        });
        RenderSystem.popMatrix();
    }
}
