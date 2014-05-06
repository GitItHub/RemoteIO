package dmillerw.remoteio.client.gui;

import dmillerw.remoteio.block.tile.TileRemoteInterface;
import dmillerw.remoteio.inventory.ContainerRemoteInterface;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class GuiRemoteInterface extends GuiContainer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");

	private final TileRemoteInterface tile;

	public GuiRemoteInterface(InventoryPlayer inventoryPlayer, TileRemoteInterface tile) {
		super(new ContainerRemoteInterface(inventoryPlayer, tile));

		this.tile = tile;
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
