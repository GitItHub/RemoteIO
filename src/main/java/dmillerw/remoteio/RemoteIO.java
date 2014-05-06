package dmillerw.remoteio;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.remoteio.block.HandlerBlock;
import dmillerw.remoteio.core.proxy.CommonProxy;
import dmillerw.remoteio.core.tracker.BlockTracker;
import dmillerw.remoteio.item.HandlerItem;
import dmillerw.remoteio.lib.ModInfo;
import dmillerw.remoteio.recipe.RecipeCopyLocation;

@Mod(modid= ModInfo.ID, name=ModInfo.NAME, version=ModInfo.VERSION)
public class RemoteIO {

	@Instance(ModInfo.ID)
	public static RemoteIO instance;
	
	@SidedProxy(serverSide=ModInfo.SERVER, clientSide=ModInfo.CLIENT)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		HandlerBlock.initialize();
		HandlerItem.initialize();

		GameRegistry.addRecipe(RecipeCopyLocation.INSTANCE);
		FMLCommonHandler.instance().bus().register(RecipeCopyLocation.INSTANCE);
		FMLCommonHandler.instance().bus().register(BlockTracker.INSTANCE);

		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
}