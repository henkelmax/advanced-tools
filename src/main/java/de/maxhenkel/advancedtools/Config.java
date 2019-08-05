package de.maxhenkel.advancedtools;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static ForgeConfigSpec.BooleanValue TOOL_ITEMS_NEVER_DESPAWN;
    public static ForgeConfigSpec.BooleanValue TOOL_ITEMS_INDESTRUCTIBLE;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPairServer.getRight();
        SERVER = specPairServer.getLeft();
    }

    public static class ServerConfig {


        public ServerConfig(ForgeConfigSpec.Builder builder) {
            TOOL_ITEMS_NEVER_DESPAWN = builder
                    .comment("If tool items should never despawn")
                    .define("tool_items_never_despawn", false);
            TOOL_ITEMS_INDESTRUCTIBLE = builder
                    .comment("If tool items should be indestructible")
                    .define("tool_items_indestructible", false);
        }
    }

}

