package com.fusionflux.originsumbrellas;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import net.fabricmc.api.ModInitializer;

public class OriginsUmbrellas implements ModInitializer {

	public static final String MOD_ID = "originsumbrellas";

	@Override
	public void onInitialize() {
		UmbrellaItems.registerItems();
	}
}
