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
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {


	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow public abstract Iterable<ItemStack> getItemsHand();

	@Shadow @Final public PlayerInventory inventory;

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tick(CallbackInfo ci) {
		if (((EntityAccessor) this).callIsBeingRainedOn() && this.age % 10 == 0) {
			ItemStack HeadSlot = this.getEquippedStack(EquipmentSlot.HEAD);
			for (ItemStack stack : this.getItemsHand()) {
				if (stack.getItem().equals(UmbrellaItems.UMBRELLA) || (HeadSlot.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() < stack.getMaxDamage() - 1)) {
					stack.damage(1, (LivingEntity) (Object) this, ((livingEntity) -> { }));
				}
			}
		}

		if (!((EntityAccessor) this).callIsBeingRainedOn() && this.age % 20 == 0) {
			ItemStack offHand = this.getEquippedStack(EquipmentSlot.OFFHAND);
			for(int current=0;current<36;current++) {
				ItemStack stack = this.inventory.getStack(current);
				if(current != this.inventory.selectedSlot)
					if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() <= stack.getMaxDamage() - 1&&!(this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
						stack.damage(-1, (LivingEntity) (Object) this, ((livingEntity) -> {
						}));
					}
				if(current != this.inventory.selectedSlot)
					if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() <= stack.getMaxDamage() - 1&&(this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
						stack.damage(-2, (LivingEntity) (Object) this, ((livingEntity) -> {
						}));
					}
				if(current == this.inventory.selectedSlot)
					if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() <= stack.getMaxDamage() - 1&&!(this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
						stack.damage(-2, (LivingEntity) (Object) this, ((livingEntity) -> {
						}));
					}
				if(current == this.inventory.selectedSlot)
					if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() <= stack.getMaxDamage() - 1&&(this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
						stack.damage(-3, (LivingEntity) (Object) this, ((livingEntity) -> {
						}));
					}
			}
			if (offHand.getItem().equals(UmbrellaItems.UMBRELLA) && offHand.getDamage() <= offHand.getMaxDamage() - 1 && !(this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
				offHand.damage(-2, (LivingEntity) (Object) this, ((livingEntity) -> {
				}));
			}
			if (offHand.getItem().equals(UmbrellaItems.UMBRELLA) && offHand.getDamage() <= offHand.getMaxDamage() - 1 && (this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.DESERT )||this.world.getBiome( this.getBlockPos() ).getCategory().equals( Biome.Category.NETHER ))) {
				offHand.damage(-3, (LivingEntity) (Object) this, ((livingEntity) -> {
				}));
			}
		}

	}


}
