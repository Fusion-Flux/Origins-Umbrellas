package com.fusionflux.originsumbrellas;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class OriginsUmbrellas implements ModInitializer {

	public static final String MOD_ID = "originsumbrellas";

	public static boolean isHoldingUmbrella(Entity player) {
		for (ItemStack stack : player.getHandItems()) {
			if (stack.getItem().equals(UmbrellaItems.UMBRELLA) && stack.getDamage() < stack.getMaxDamage() - 1 ) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void onInitialize() {
		UmbrellaItems.registerItems();
	}
}
