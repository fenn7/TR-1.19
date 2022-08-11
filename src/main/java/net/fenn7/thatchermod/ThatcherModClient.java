package net.fenn7.thatchermod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fenn7.thatchermod.block.entity.ModEntities;
import net.fenn7.thatchermod.block.entity.client.*;
import net.fenn7.thatchermod.block.entity.client.armour.ThatcheriteArmourRenderer;
import net.fenn7.thatchermod.item.ModItems;
import net.fenn7.thatchermod.network.ModPackets;
import net.fenn7.thatchermod.particle.ModParticles;
import net.fenn7.thatchermod.particle.custom.DeficiencyIndicatorParticle;
import net.fenn7.thatchermod.particle.custom.ThatcherJumpParticle;
import net.fenn7.thatchermod.screen.ModScreenHandlers;
import net.fenn7.thatchermod.screen.ThatcherismAltarScreen;
import net.fenn7.thatchermod.event.KeyInputs;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ThatcherModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.THATCHER_JUMPSCARE, ThatcherJumpParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.DEFICIENCY_INDICATOR, DeficiencyIndicatorParticle.Factory::new);

        ScreenRegistry.register(ModScreenHandlers.THATCHERISM_ALTAR_SCREEN_HANDLER, ThatcherismAltarScreen::new);

        EntityRendererRegistry.register(ModEntities.THATCHER, ThatcherModelRenderer::new);
        EntityRendererRegistry.register(ModEntities.CURSED_METEOR, CursedMeteorRenderer::new);
        EntityRendererRegistry.register(ModEntities.CURSED_MISSILE, CursedMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.SMOKE_ENTITY, SmokeRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(new ThatcheriteArmourRenderer(), ModItems.THATCHERITE_BOOTS,
                ModItems.THATCHERITE_GREAVES, ModItems.THATCHERITE_CHESTPLATE, ModItems.THATCHERITE_HELMET);

        KeyInputs.register();
        ModPackets.registerS2CPackets();
        HudRenderCallback.EVENT.register(new LastStandOverlay());
    }
}
