package site.olsterstudios.vinyldiscs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.util.Identifier;

public final class VinylDiscsClient implements ClientModInitializer {
    private static final Identifier HIGH_RESOLUTION_PACK = Identifier.of("vinyl-discs", "high_resolution");
    private static String highResolutionPackId;

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(BlockEntityType.JUKEBOX, VinylJukeboxRenderer::new);

        VinylDiscsConfig.load();

        FabricLoader.getInstance().getModContainer("vinyl-discs").ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        HIGH_RESOLUTION_PACK,
                        container,
                        ResourcePackActivationType.NORMAL
                )
        );

        applyTextureResolution();
    }

    public static void applyTextureResolution() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return;
        }

        ResourcePackManager manager = client.getResourcePackManager();
        highResolutionPackId = findHighResolutionPackId(manager);
        if (highResolutionPackId == null) {
            return;
        }

        boolean enabled = manager.getEnabledIds().contains(highResolutionPackId);
        boolean shouldEnable = VinylDiscsConfig.isHighResolution();

        if (enabled == shouldEnable) {
            return;
        }

        if (shouldEnable) {
            manager.enable(highResolutionPackId);
        } else {
            manager.disable(highResolutionPackId);
        }

        client.reloadResources();
    }

    private static String findHighResolutionPackId(ResourcePackManager manager) {
        for (ResourcePackProfile profile : manager.getProfiles()) {
            if (profile.getId().endsWith(HIGH_RESOLUTION_PACK.getPath())) {
                return profile.getId();
            }
        }
        return null;
    }
}
