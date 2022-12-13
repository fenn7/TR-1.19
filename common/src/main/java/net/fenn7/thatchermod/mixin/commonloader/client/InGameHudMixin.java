package net.fenn7.thatchermod.mixin.commonloader.client;

import net.fenn7.thatchermod.commonside.ThatcherMod;
import net.fenn7.thatchermod.commonside.effect.ModEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    private static final Identifier LAST_STAND_OVERLAY = new Identifier(ThatcherMod.MOD_ID, "textures/misc/last_stand.png");

    @Shadow
    protected abstract void renderOverlay(Identifier texture, float opacity);

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getLastFrameDuration()F", shift = At.Shift.AFTER))
    private void injectLastStandEffectRender(CallbackInfo ci) {
        ThatcherMod.LOGGER.warn("i am injektin");
        if (this.client.player.hasStatusEffect(ModEffects.LAST_STAND.get())) {
            float f = ThreadLocalRandom.current().nextFloat(0.525F, 0.575F);
            ThatcherMod.LOGGER.warn("" + f);
            this.renderOverlay(LAST_STAND_OVERLAY, f);
        }
    }
}
