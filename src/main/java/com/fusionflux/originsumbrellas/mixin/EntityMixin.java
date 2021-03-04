package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.StreamSupport;

@Mixin(Entity.class)
public abstract class EntityMixin {


	@Shadow public World world;

	@Shadow public abstract BlockPos getBlockPos();

	@Shadow public abstract Box getBoundingBox();

	@Shadow public abstract Iterable<ItemStack> getItemsHand();

	@Inject(method = "isBeingRainedOn", at = @At("HEAD"), cancellable = true)
	private void isBeingRainedOn(CallbackInfoReturnable<Boolean> cir) {
		Iterable<ItemStack> hands = this.getItemsHand();
		for (ItemStack stack : hands) {
			if (stack.getItem() == UmbrellaItems.UMBRELLA)
				cir.setReturnValue(false);
		}
	}



}
