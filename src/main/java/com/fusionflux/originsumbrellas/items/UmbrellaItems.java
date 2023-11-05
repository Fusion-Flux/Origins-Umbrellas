package com.fusionflux.originsumbrellas.items;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class UmbrellaItems {
    public static final UmbrellaDyeTest UMBRELLA = new UmbrellaDyeTest(new FabricItemSettings().maxCount(1).maxDamage(1200));


    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(OriginsUmbrellas.MOD_ID, "umbrella"), UMBRELLA);

    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayers() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), UmbrellaItems.UMBRELLA);
    }

}
