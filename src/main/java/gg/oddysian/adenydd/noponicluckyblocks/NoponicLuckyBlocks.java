package gg.oddysian.adenydd.noponicluckyblocks;

import gg.oddysian.adenydd.noponicluckyblocks.commands.LuckyBlockCommand;
import gg.oddysian.adenydd.noponicluckyblocks.config.Config;
import gg.oddysian.adenydd.noponicluckyblocks.listener.LuckyBlockListener;
import gg.oddysian.adenydd.noponicluckyblocks.obj.LuckyBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = NoponicLuckyBlocks.MOD_ID,
        name = NoponicLuckyBlocks.MOD_NAME,
        version = NoponicLuckyBlocks.VERSION,
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class NoponicLuckyBlocks {

    public static final String MOD_ID = "noponicluckyblocks";
    public static final String MOD_NAME = "NoponicLuckyBlocks";
    public static final String VERSION = "1.0-SNAPSHOT";
    public static final String AUTHOR = "Adenydd";
    public static Logger log = LogManager.getLogger(MOD_NAME);
    public static File configDir;

    public static LuckyBlockListener listener;


    @Mod.Instance(MOD_ID)
    public static NoponicLuckyBlocks INSTANCE;


    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        log.info("Meh Meh! Tora booting up " + MOD_NAME + " by Miss DevPon " + AUTHOR + " " + VERSION + " - 2021" + " to make Server better than any ServerPon!");
        configDir = new File(event.getModConfigurationDirectory() + "/");
        configDir.mkdir();

        Config.getConfig().setup();
        Config.getConfig().load();

        listener = new LuckyBlockListener();

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        LuckyBlock.loadLuckyBlocks();
        event.registerServerCommand(new LuckyBlockCommand());
    }

}
