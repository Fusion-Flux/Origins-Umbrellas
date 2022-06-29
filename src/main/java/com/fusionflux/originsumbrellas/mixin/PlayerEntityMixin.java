package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import io.github.apace100.apoli.mixin.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

        if ((inDaylight || ((EntityAccessor) this).callIsBeingRainedOn()) && this.age % 500 == 0)
            for (ItemStack stack : this.getItemsHand())
                if (stack.getItem().equals(UmbrellaItems.UMBRELLA)) {
                    stack.setDamage(stack.getDamage() + 1);
                    break; // if offhanding 2 dont break 2 umbrellas
                }


    }


}
