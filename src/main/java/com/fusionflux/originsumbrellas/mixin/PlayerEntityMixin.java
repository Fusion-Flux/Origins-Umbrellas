package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import io.github.apace100.apoli.mixin.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract Iterable<ItemStack> getItemsHand();

    @Shadow
    public abstract PlayerInventory getInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        boolean isBeingRainedOn = ((EntityAccessor) this).callIsBeingRainedOn();

        if (isBeingRainedOn && this.age % 10 == 0) {
            for (ItemStack stack : this.getItemsHand()) {
                if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() < stack.getMaxDamage() - 1) {
                    // Set damage instead of calling stack.damage, otherwise an animation is
                    // triggered for each damage tick.
                    stack.setDamage(stack.getDamage() + 1);
                }
            }
        } else if (!isBeingRainedOn && this.age % 20 == 0) {
            ItemStack offHand = this.getEquippedStack(EquipmentSlot.OFFHAND);
            boolean isHot = world.getBiome(this.getBlockPos()).value().isHot(this.getBlockPos());

            // Repair off-hand umbrella.
            this.repairStack(offHand, isHot, true);

            // Repair any umbrellas in the player's inventory.
            for (int i = 0; i < 36; i++) {
                this.repairStack(this.getInventory().getStack(i), isHot, this.getInventory().selectedSlot == i);
            }
        }
    }

    private void repairStack(ItemStack stack, boolean isHot, boolean isExposed) {
        if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.isDamaged()) {
            stack.setDamage(stack.getDamage() - 1);
            if (isHot) {
                // Umbrellas dry faster in hot climates.
                stack.setDamage(stack.getDamage() - 1);
            }
            if (isExposed) {
                // Umbrellas dry faster if they're exposed (not tucked away in inventory).
                stack.setDamage(stack.getDamage() - 1);
            }
        }
    }
}
