package com.boyenn.mc.backseatega.mixins;

import com.boyenn.mc.backseatega.BackseategaClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.RegistryTracker;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/toast/ToastManager;draw(Lnet/minecraft/client/util/math/MatrixStack;)V", shift = At.Shift.AFTER))

    private void drawData(CallbackInfo ci) {
        BackseategaClient.DATA_RENDERER.draw();
    }

    @Inject(at = @At("HEAD"), method = "joinWorld")
    public void onJoin(ClientWorld targetWorld, CallbackInfo ci) {
        if (targetWorld.getDimensionRegistryKey() == DimensionType.THE_NETHER_REGISTRY_KEY) {
            BackseategaClient.SPLIT_TRACKER.registerSplit("enter_nether");
            BackseategaClient.SPLIT_TRACKER.complete();
            return;
        }
    }

    // Record save
    @Inject(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;close()V", shift = At.Shift.BEFORE))
    public void onStop(CallbackInfo ci) {
        BackseategaClient.SPLIT_TRACKER.complete();
    }

    @Inject(at = @At("HEAD"), method = "method_29607(Ljava/lang/String;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/util/registry/RegistryTracker$Modifiable;Lnet/minecraft/world/gen/GeneratorOptions;)V")
    public void onCreate(String worldName, LevelInfo levelInfo, RegistryTracker.Modifiable registryTracker, GeneratorOptions generatorOptions, CallbackInfo ci) {
        BackseategaClient.SPLIT_TRACKER.start();
    }
}
