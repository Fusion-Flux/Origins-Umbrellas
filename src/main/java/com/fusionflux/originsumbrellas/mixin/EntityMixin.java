package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@ModifyReturnValue(method = "isBeingRainedOn", at = @At("RETURN"))
	private boolean checkHoldingUmbrella(boolean original) {
		return original && !OriginsUmbrellas.isHoldingUmbrella((Entity)(Object)this);
	}



}
