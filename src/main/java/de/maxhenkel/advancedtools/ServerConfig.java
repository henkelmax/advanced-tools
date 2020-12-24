package de.maxhenkel.advancedtools;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig extends ConfigBase {

    public final ForgeConfigSpec.BooleanValue toolItemsNeverDespawn;
    public final ForgeConfigSpec.BooleanValue toolItemsIndestructible;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        toolItemsNeverDespawn = builder
                .comment("Makes dropped tool item entities never despawn")
                .define("tool_items_never_despawn", false);
        toolItemsIndestructible = builder
                .comment("Makes dropped tool item entities indestructible")
                .define("tool_items_indestructible", false);
    }

}
