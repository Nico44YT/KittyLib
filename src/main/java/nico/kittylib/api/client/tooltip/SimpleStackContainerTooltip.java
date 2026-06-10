package nico.kittylib.api.client.tooltip;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SimpleStackContainerTooltip implements TooltipComponent {
    public static final Identifier TEXTURE = Identifier.of("textures/gui/container/bundle.png");
    private static final int TEXTURE_SIZE = 128;
    private static final int WIDTH_PER_COLUMN = 18;
    private static final int HEIGHT_PER_ROW = 20;
    private final ItemStack stack;
    private final Identifier overlay;

    public SimpleStackContainerTooltip(SimpleStackContainerTooltipData data, @Nullable Identifier overlay) {
        this.stack = data.stack();

        this.overlay = overlay;
    }

    public int getHeight() {
        return this.getRows() * HEIGHT_PER_ROW + 4;
    }

    public int getWidth(TextRenderer textRenderer) {
        return this.getColumns() * WIDTH_PER_COLUMN + 2;
    }

    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        int columns = this.getColumns();
        int rows = this.getRows();
        int slotIndex = 0;

        for (int drawX = 0; drawX < rows; ++drawX) {
            for (int drawY = 0; drawY < columns; ++drawY) {
                int n = x + drawY * WIDTH_PER_COLUMN + 1;
                int o = y + drawX * HEIGHT_PER_ROW + 1;
                this.drawSlot(n, o, slotIndex++, context, textRenderer);
            }
        }

        this.drawOutline(x, y, columns, rows, context);
    }

    private void drawSlot(int x, int y, int index, DrawContext context, TextRenderer textRenderer) {
        if (stack == null || stack.isEmpty()) {
            this.draw(context, x, y, Sprite.SLOT);
            if(overlay != null) context.drawTexture(overlay, x + 1, y + 1, 0, 0, 16, 16, 16, 16);
        } else {
            this.draw(context, x, y, Sprite.SLOT);
            context.drawItem(stack, x + 1, y + 1, index);
            context.drawItemInSlot(textRenderer, stack, x + 1, y + 1);
        }
    }

    private void drawOutline(int x, int y, int columns, int rows, DrawContext context) {
        this.draw(context, x, y, Sprite.BORDER_CORNER_TOP);
        this.draw(context, x + columns * WIDTH_PER_COLUMN + 1, y, Sprite.BORDER_CORNER_TOP);

        int i;
        for (i = 0; i < columns; ++i) {
            this.draw(context, x + 1 + i * WIDTH_PER_COLUMN, y, Sprite.BORDER_HORIZONTAL_TOP);
            this.draw(context, x + 1 + i * WIDTH_PER_COLUMN, y + rows * HEIGHT_PER_ROW, Sprite.BORDER_HORIZONTAL_BOTTOM);
        }

        for (i = 0; i < rows; ++i) {
            this.draw(context, x, y + i * HEIGHT_PER_ROW + 1, Sprite.BORDER_VERTICAL);
            this.draw(context, x + columns * WIDTH_PER_COLUMN + 1, y + i * HEIGHT_PER_ROW + 1, Sprite.BORDER_VERTICAL);
        }

        this.draw(context, x, y + rows * HEIGHT_PER_ROW, Sprite.BORDER_CORNER_BOTTOM);
        this.draw(context, x + columns * WIDTH_PER_COLUMN + 1, y + rows * HEIGHT_PER_ROW, Sprite.BORDER_CORNER_BOTTOM);
    }

    private void draw(DrawContext context, int x, int y, Sprite sprite) {
        context.drawTexture(TEXTURE, x, y, 0, (float) sprite.u, (float) sprite.v, sprite.width, sprite.height, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    private int getColumns() {
        return 1;
    }

    private int getRows() {
        return 1;
    }

    @Environment(EnvType.CLIENT)
    private enum Sprite {
        SLOT(0, 0, 18, 20),
        BLOCKED_SLOT(0, 40, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int u;
        public final int v;
        public final int width;
        public final int height;

        Sprite(int u, int v, int width, int height) {
            this.u = u;
            this.v = v;
            this.width = width;
            this.height = height;
        }
    }
}
