package de.maxhenkel.advancedtools.proxy;

import de.maxhenkel.advancedtools.render.PickaxeModel;
import de.maxhenkel.advancedtools.render.base.UniversalModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
 
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		ModelLoaderRegistry.registerLoader(new UniversalModel(new PickaxeModel()));
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}

}
