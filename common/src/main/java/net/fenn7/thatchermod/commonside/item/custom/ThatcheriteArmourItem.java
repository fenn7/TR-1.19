package net.fenn7.thatchermod.commonside.item.custom;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fenn7.thatchermod.commonside.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThatcheriteArmourItem extends ArmorItem implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ThatcheriteArmourItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @ExpectPlatform
    public static Item create(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        throw new AssertionError();
    }

    public static boolean hasFullSet(PlayerEntity player) {
        return hasHelmet(player) && hasChest(player) && hasLegs(player) && hasBoots(player);
    }

    public static boolean hasHelmet(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        return !helmet.isEmpty() && helmet.getItem().equals(ModItems.THATCHERITE_HELMET.get());
    }

    public static boolean hasChest(PlayerEntity player) {
        ItemStack chest = player.getInventory().getArmorStack(2);
        return !chest.isEmpty() && chest.getItem().equals(ModItems.THATCHERITE_CHESTPLATE.get());
    }

    public static boolean hasLegs(PlayerEntity player) {
        ItemStack legs = player.getInventory().getArmorStack(1);
        return !legs.isEmpty() && legs.getItem().equals(ModItems.THATCHERITE_GREAVES.get());
    }

    public static boolean hasBoots(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        return !boots.isEmpty() && boots.getItem().equals(ModItems.THATCHERITE_BOOTS.get());
    }

    /* animation stuff */
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        // This is all the extradata this event carries. The livingentity is the entity
        // that's wearing the armor. The itemstack and equipmentslottype are self
        // explanatory.
        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);

        // Always loop the animation but later on in this method we'll decide whether or
        // not to actually play it
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));

        // If the living entity is an armorstand just play the animation nonstop
        if (livingEntity instanceof ArmorStandEntity) {
            return PlayState.CONTINUE;
        }

        // The entity is a player, so we want to only play if the player is wearing the
        // full set of armor
        else if (livingEntity instanceof PlayerEntity player) {

            // Get all the equipment, aka the armor, currently held item, and offhand item
            List<Item> equipmentList = new ArrayList<>();
            player.getItemsEquipped().forEach((x) -> equipmentList.add(x.getItem()));

            // elements 2 to 6 are the armor so we take the sublist. Armorlist now only
            // contains the 4 armor slots
            Set<Item> armorItems = new HashSet<>(equipmentList.subList(2, 6));

            // Make sure the player is wearing all the armor. If they are, continue playing
            // the animation, otherwise stop
            boolean isWearingAll = armorItems.containsAll(Set.of(
                    ModItems.THATCHERITE_BOOTS.get(),
                    ModItems.THATCHERITE_GREAVES.get(),
                    ModItems.THATCHERITE_CHESTPLATE.get(),
                    ModItems.THATCHERITE_HELMET.get()
            ));
            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
