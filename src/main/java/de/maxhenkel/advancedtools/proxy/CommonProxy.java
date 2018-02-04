package de.maxhenkel.advancedtools.proxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
public class CommonProxy {

    public void preinit(FMLPreInitializationEvent event) {
        Configuration config = null;
        try {
            config = new Configuration(event.getSuggestedConfigurationFile());
            config.load();

            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postinit(FMLPostInitializationEvent event) {

    }

}
