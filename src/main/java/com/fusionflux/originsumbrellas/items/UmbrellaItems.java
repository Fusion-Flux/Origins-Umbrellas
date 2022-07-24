package com.fusionflux.originsumbrellas.items;

import com.fusionflux.originsumbrellas.OriginsUmbrellas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class UmbrellaItems {
    public static final DyeableUmbrellaItem UMBRELLA = new DyeableUmbrellaItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).maxDamage(1200));
    public static final DyeableUmbrellaItem NETHERITE_UMBRELLA = new DyeableUmbrellaItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).maxDamage(6000));

    public static final TagKey<Item> UMBRELLA_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(OriginsUmbrellas.MOD_ID, "umbrellas"));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new Identifier(OriginsUmbrellas.MOD_ID, "umbrella"), UMBRELLA);
        Registry.register(Registry.ITEM, new Identifier(OriginsUmbrellas.MOD_ID, "netherite_umbrella"), NETHERITE_UMBRELLA);

    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayers() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), UMBRELLA);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), NETHERITE_UMBRELLA);
    }


}
