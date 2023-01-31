package net.fenn7.thatchermod.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fenn7.thatchermod.commonside.ThatcherMod;
import net.fenn7.thatchermod.commonside.entity.mobs.FirstSpectreEntity;
import net.fenn7.thatchermod.commonside.entity.mobs.SecondSpectreEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class SecondSpectreRenderer extends GeoEntityRenderer<SecondSpectreEntity> {
    public SecondSpectreRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SecondSpectreModel());
    }

    @Override
    public Identifier getTextureLocation(SecondSpectreEntity instance) {
        return new Identifier(ThatcherMod.MOD_ID, "textures/entity/spectre/second_spectre.png");
    }

    @Override
    public Identifier getTexture(SecondSpectreEntity instance) {
        return new Identifier(ThatcherMod.MOD_ID, "textures/entity/spectre/second_spectre.png");
    }

    @Override
    public RenderLayer getRenderType(SecondSpectreEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }
}
