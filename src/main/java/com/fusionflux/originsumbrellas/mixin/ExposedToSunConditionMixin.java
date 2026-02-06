package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.apace100.apoli.power.factory.condition.EntityConditions;
import io.github.apace100.apoli.power.factory.condition.entity.ExposedToSunCondition;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExposedToSunCondition.class)
public class ExposedToSunConditionMixin {

    @ModifyReturnValue(method = "condition", at = @At("RETURN"))
    private static boolean checkUmbrella(boolean original, SerializableData.Instance data, Entity entity){
        return original && !OriginsUmbrellas.isHoldingUmbrella(entity);
    }

}
