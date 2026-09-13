package site.olsterstudios.vinyldiscs;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public final class VinylJukeboxRenderState extends BlockEntityRenderState {
    public final ItemRenderState vinyl = new ItemRenderState();
    public boolean hasRecord;
}
