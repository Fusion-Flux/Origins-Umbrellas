package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import io.github.apace100.apoli.mixin.EntityAccessor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {


//     @Shadow
//     public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract Iterable<ItemStack> getItemsHand();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        // check is in overworld and is day and is sky visible
        boolean inDaylight = world.getDimensionKey().equals(DimensionTypes.OVERWORLD) && this.world.isDay() && this.world.isSkyVisible(this.getBlockPos());
        // check can biome precip and is raining and is sky visible

        if (inDaylight || ((EntityAccessor) this).callIsBeingRainedOn() && this.age % 100 == 0)
            for (ItemStack stack : this.getItemsHand()) // does not check headslot? if that is something u want to add
                if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() < stack.getMaxDamage() - 1) {
                    stack.setDamage(stack.getDamage()+1);
                    break; // if offhanding 2 dont break 2 umbrellas
                }


    }


}
