package dmillerw.remoteio.item;

import dmillerw.remoteio.block.tile.TileRemoteInterface;
import dmillerw.remoteio.core.TabRemoteIO;
import dmillerw.remoteio.lib.DimensionalCoords;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemLocationChip extends Item {

	public static void setCoordinates(ItemStack stack, DimensionalCoords coords) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound nbt = stack.getTagCompound();
		NBTTagCompound tag = new NBTTagCompound();
		coords.writeToNBT(tag);
		nbt.setTag("position", tag);
		stack.setTagCompound(nbt);
	}

	public static DimensionalCoords getCoordinates(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			return null;
		}

		NBTTagCompound nbt = stack.getTagCompound();

		if (!nbt.hasKey("position")) {
			return null;
		}

		return DimensionalCoords.fromNBT(nbt.getCompoundTag("position"));
	}

	public ItemLocationChip() {
		super();

		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(TabRemoteIO.TAB);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
		DimensionalCoords coords = ItemLocationChip.getCoordinates(stack);

		if (coords != null) {
			list.add("Dimension: " + DimensionManager.getProvider(coords.dimensionID).getDimensionName());
			list.add("X: " + coords.x + " Y: " + coords.y + " Z: " + coords.z);
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(x, y, z);

			if (player.isSneaking()) {
				if (tile != null) {
					ItemLocationChip.setCoordinates(stack, DimensionalCoords.create(tile));
					player.addChatComponentMessage(new ChatComponentTranslation("chat.location_chip.set"));
				} else {
					player.addChatComponentMessage(new ChatComponentTranslation("chat.location_chip.fail"));
				}
			} else {
				if (tile != null) {
					if (tile instanceof TileRemoteInterface) {
						DimensionalCoords coords = ItemLocationChip.getCoordinates(stack);

						if (coords != null) {
							((TileRemoteInterface)tile).setRemotePosition(coords);
						}
					}
				}
			}
		}

		return !world.isRemote;
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return false;
	}

}