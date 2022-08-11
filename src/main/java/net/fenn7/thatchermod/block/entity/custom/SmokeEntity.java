package net.fenn7.thatchermod.block.entity.custom;

import net.fenn7.thatchermod.block.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SmokeEntity extends ExplosiveProjectileEntity {
    public SmokeEntity(EntityType<? extends SmokeEntity> entityType, World world) {
        super(entityType, world);
    }
    public SmokeEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(ModEntities.SMOKE_ENTITY, owner, directionX, directionY, directionZ, world);
        this.setOwner(owner);
    }

    public void tick() {
        if (this.age >= 20) {
            this.discard();
        }
        Entity owner = this.getOwner();
        if (owner != null) {
            Vec3d offset = getRotationVector(0.0F, owner.getYaw()).multiply(0.5D);
            this.setPos(owner.prevX + offset.x, owner.prevY - offset.y - 1.0D, owner.prevZ + offset.z);
            this.setVelocity(owner.getVelocity());
        }
        if (this.age % 10 == 0) {
            this.getWorld().addParticle(ParticleTypes.FIREWORK, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_BLAZE_BURN, SoundCategory.PLAYERS,
                    1.5F, 0.75F);
        }
        super.tick();
    }

    protected ParticleEffect getParticleType() { return ParticleTypes.CAMPFIRE_SIGNAL_SMOKE; }
    public boolean collides() { return false; }
    public boolean hasNoGravity() { return true; }
}
