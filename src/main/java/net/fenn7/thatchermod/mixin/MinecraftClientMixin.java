package net.fenn7.thatchermod.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fenn7.thatchermod.ThatcherMod;
import net.fenn7.thatchermod.item.ModItems;
import net.fenn7.thatchermod.item.custom.grenade.GrenadeLauncherItem;
import net.fenn7.thatchermod.network.ModPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow public ClientPlayerEntity player;

    @Inject(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"), cancellable = true)
    private void injectedAttackMethod(CallbackInfoReturnable cir) {
        if (player.getMainHandStack().isOf(ModItems.GRENADE_LAUNCHER)) {
            ClientPlayNetworking.send(ModPackets.GRENADE_ID, PacketByteBufs.create());
            ThatcherMod.LOGGER.warn("awooooooooooooga");
            cir.cancel();
        }
        ThatcherMod.LOGGER.warn("call");
    }
}