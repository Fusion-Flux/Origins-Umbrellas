package com.fusionflux.originsumbrellas.client;

import com.fusionflux.originsumbrellas.items.UmbrellaItems;
import net.fabricmc.api.ClientModInitializer;

public class OriginsUmbrellasClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        UmbrellaItems.registerRenderLayers();
    }
}
