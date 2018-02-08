package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, acceptedMinecraftVersions=Main.MC_VERSION)
public class Main {
	
    public static final String MODID = "advancedtools";
    public static final String VERSION = "1.0.3";
    public static final String MC_VERSION = "[1.12.2]";

	@Instance
    private static Main instance;

	@SidedProxy(clientSide="de.maxhenkel.advancedtools.proxy.ClientProxy", serverSide="de.maxhenkel.advancedtools.proxy.CommonProxy")
    public static CommonProxy proxy;
    
	public Main() {
		instance=this;
		new Registry();
	}
	
    @EventHandler
    public void preinit(FMLPreInitializationEvent event){
		proxy.preinit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
    	 proxy.init(event);
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event){
		proxy.postinit(event);
    }
    
	public static Main instance() {
		return instance;
	}
	
}
