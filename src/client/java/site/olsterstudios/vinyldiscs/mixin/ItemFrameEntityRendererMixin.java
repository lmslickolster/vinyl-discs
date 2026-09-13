package site.olsterstudios.vinyldiscs.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;

@Mixin(ItemFrameEntityRenderer.class)
public abstract class ItemFrameEntityRendererMixin {
    @Inject(method = "updateRenderState", at = @At("TAIL"))
    private void vinylDiscs$selectGlowStand(ItemFrameEntity frame, ItemFrameEntityRenderState state, float tickProgress, CallbackInfo ci) {
        if (!state.glow || state.itemRenderState.isEmpty()) {
            return;
        }

        ItemStack stack = frame.getHeldItemStack().copy();
        stack.set(
            DataComponentTypes.CUSTOM_MODEL_DATA,
            new CustomModelDataComponent(List.of(), List.of(true), List.of(), List.of())
        );

        ItemModelManager manager = MinecraftClient.getInstance().getItemModelManager();
        manager.updateForNonLivingEntity(state.itemRenderState, stack, ItemDisplayContext.FIXED, frame);
    }
}
