package com.boyenn.mc.backseatega.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Shadow private MinecraftClient client;

    @Redirect(method = "onTitle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;setOverlayMessage(Lnet/minecraft/text/Text;Z)V"))
    public void onTitleMixin(InGameHud instance, Text message, boolean tinted) {

        MinecraftClient inst = MinecraftClient.getInstance();
        ClientWorld world = inst.world;
        ClientPlayerEntity player = inst.player;
        Biome biome = world.getBiome(player.getBlockPos());


        instance.setOverlayMessage(Text.method_30163(biome.getTranslationKey()), tinted);
    }

}
