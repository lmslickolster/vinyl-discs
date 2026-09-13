package site.olsterstudios.vinyldiscs;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class VinylDiscsConfigScreen extends Screen {
    private final Screen parent;
    private ButtonWidget resolutionButton;

    public VinylDiscsConfigScreen(Screen parent) {
        super(Text.translatable("screen.vinyl_discs.settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        resolutionButton = ButtonWidget.builder(getResolutionText(), button -> {
            VinylDiscsConfig.setHighResolution(!VinylDiscsConfig.isHighResolution());
            button.setMessage(getResolutionText());
        }).dimensions(this.width / 2 - 100, this.height / 2 - 10, 200, 20).build();

        addDrawableChild(resolutionButton);

        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> close())
                .dimensions(this.width / 2 - 100, this.height / 2 + 25, 200, 20)
                .build());
    }

    private Text getResolutionText() {
        return Text.translatable(
                "option.vinyl_discs.texture_resolution",
                VinylDiscsConfig.isHighResolution() ? "64x64" : "32x32"
        );
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context, mouseX, mouseY, deltaTicks);
        context.drawCenteredTextWithShadow(
                textRenderer,
                title,
                this.width / 2,
                this.height / 2 - 55,
                0xFFFFFF
        );
        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.vinyl_discs.resolution_description"),
                this.width / 2,
                this.height / 2 - 30,
                0xAAAAAA
        );
        super.render(context, mouseX, mouseY, deltaTicks);
    }
}
