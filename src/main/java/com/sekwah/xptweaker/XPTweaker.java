package com.sekwah.xptweaker;

import com.google.gson.reflect.TypeToken;
import com.sekwah.xptweaker.data.EntityData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;

@Mod(modid = XPTweaker.modid, name = "XP Tweaker", version = XPTweaker.version)
public class XPTweaker {

    public static final String modid = "xptweaker";
    public static final Logger logger = LogManager.getLogger("XP Tweaker");

    public static final String version = "0.0.1";
    public static File configFolder;
    private DataStorage dataStorage;

    @Mod.EventHandler
    public void preLoad(FMLPreInitializationEvent event) {
        configFolder = new File(event.getModConfigurationDirectory(), "xptweaker");
        dataStorage = new DataStorage();
        dataStorage.copyDefaultFile("entityxp.json", false);

        MinecraftForge.EVENT_BUS.register(new EventHook(this));
    }

    public DataStorage getDataStorage() {
        return this.dataStorage;
    }
}
