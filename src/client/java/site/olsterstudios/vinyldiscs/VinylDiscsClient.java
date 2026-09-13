package site.olsterstudios.vinyldiscs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererFactories;
import net.minecraft.block.entity.BlockEntityType;

public final class VinylDiscsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(BlockEntityType.JUKEBOX, VinylJukeboxRenderer::new);
    }
}
