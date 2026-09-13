package site.olsterstudios.vinyldiscs;

import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemDisplayContext;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

public final class VinylJukeboxRenderer implements BlockEntityRenderer<JukeboxBlockEntity, VinylJukeboxRenderState> {
    private final ItemModelManager itemModelManager;

    public VinylJukeboxRenderer(BlockEntityRendererFactory.Context context) {
        this.itemModelManager = context.itemModelManager();
    }

    @Override
    public VinylJukeboxRenderState createRenderState() {
        return new VinylJukeboxRenderState();
    }

    @Override
    public void updateRenderState(JukeboxBlockEntity blockEntity, VinylJukeboxRenderState state, float tickProgress, net.minecraft.util.math.Vec3d cameraPos, net.minecraft.client.render.command.ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        ItemStack stack = blockEntity.getTheItem();
        state.hasRecord = !stack.isEmpty();
        if (state.hasRecord) {
            itemModelManager.updateForNonLivingEntity(
                state.vinyl,
                stack,
                ItemDisplayContext.FIXED,
                blockEntity
            );
        } else {
            state.vinyl.clear();
        }
    }

    @Override
    public void render(VinylJukeboxRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (!state.hasRecord || state.vinyl.isEmpty()) {
            return;
        }

        matrices.push();
        matrices.translate(0.5D, 1.015D, 0.5D);
        matrices.scale(0.78F, 0.78F, 0.78F);
        state.vinyl.render(matrices, queue, 15728880, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }
}
