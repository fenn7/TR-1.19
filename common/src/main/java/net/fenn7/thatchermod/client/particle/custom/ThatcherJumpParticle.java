package net.fenn7.thatchermod.client.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class ThatcherJumpParticle extends SpriteBillboardParticle {
    protected ThatcherJumpParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                                   SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 0.6F;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale *= 0.75F;
        this.maxAge = 200;
        this.setSpriteForAge(spriteSet);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public void tick() {
        super.tick();
        double radius = 2; // adjust this value to control the size of the circle
        double angle = 0.1; // adjust this value to control the speed of the particle

        double x = radius * Math.cos(angle);
        double y = radius * Math.sin(angle);

        // set the position of the particle to the calculated x and y values
        this.setPos(x, y, this.z);
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1 / (float) maxAge) * age + 1);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new ThatcherJumpParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}