package com.fusionflux.originsumbrellas.mixin;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.power.factory.condition.EntityConditions;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiFunction;

@Mixin(EntityConditions.class)
public class EntityConditionsMixin {


    @Unique
    private static BiFunction<SerializableData.Instance, Entity, Boolean> wrapFunction(Identifier id, BiFunction<SerializableData.Instance, Entity, Boolean> original) {
        if(id.getPath().equals("exposed_to_sky") ){
            return (data, entity) -> {
                return original.apply(data, entity) && !OriginsUmbrellas.isHoldingUmbrella(entity);
            };
        }
        return original;
    }


    @WrapOperation(method = "register()V", at = @At(value = "NEW", target = "io/github/apace100/apoli/power/factory/condition/ConditionFactory"), remap = false)
    private static ConditionFactory<Entity> wrapCreate(Identifier id, SerializableData data, BiFunction<SerializableData.Instance, Entity, Boolean> function, Operation<ConditionFactory<Entity>> original) {
       return original.call(id,data,wrapFunction(id,function));
    }

}
