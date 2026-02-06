package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {




	@Shadow public int age;

	@Shadow public abstract World getWorld();

	@Shadow public abstract BlockPos getBlockPos();

	@Unique
	protected boolean ignoreUmbrella;

	@ModifyReturnValue(method = "isBeingRainedOn", at = @At("RETURN"))
	private boolean checkHoldingUmbrella(boolean original) {
		if(ignoreUmbrella)
			return original;
		return original && !OriginsUmbrellas.isHoldingUmbrella((Entity)(Object)this);
	}


}
