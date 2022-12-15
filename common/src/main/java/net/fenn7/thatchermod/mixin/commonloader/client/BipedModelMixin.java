package net.fenn7.thatchermod.mixin.commonloader.client;

import net.fenn7.thatchermod.commonside.item.custom.grenade.GrenadeLauncherItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedModelMixin<T extends LivingEntity> {

    @Shadow
    @Final
    public ModelPart head;
    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftArm;

    @Inject(method = "positionRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
            ordinal = 2), cancellable = true)
    private void thatchersRevenge$injectRightArmMethod(T entity, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack rightStack = client.options.mainArm == Arm.RIGHT ? entity.getMainHandStack() : entity.getOffHandStack();

        if (rightStack.getItem() instanceof GrenadeLauncherItem) {
            rightArm.pitch = -1.4F + head.pitch;
            rightArm.yaw += this.head.yaw;
            info.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPart;pitch:F",
            ordinal = 2), cancellable = true)
    private void thatchersRevenge$injectLeftArmMethod(T entity, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack leftStack = client.options.mainArm == Arm.RIGHT ? entity.getOffHandStack() : entity.getMainHandStack();

        if (leftStack.getItem() instanceof GrenadeLauncherItem) {
            leftArm.pitch = -1.4F + head.pitch;
            leftArm.yaw += head.yaw;
            info.cancel();
        }
    }
}