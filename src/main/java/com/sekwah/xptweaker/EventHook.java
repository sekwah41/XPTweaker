package com.sekwah.xptweaker;

import com.google.gson.reflect.TypeToken;
import com.sekwah.xptweaker.data.EntityData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Type;
import java.util.HashMap;

public class EventHook {

    private final XPTweaker xpTweaker;

    private Minecraft mc = Minecraft.getMinecraft();

    private HashMap<String,EntityData> entityHashMap;

    public EventHook(XPTweaker xpTweaker) {
        this.xpTweaker = xpTweaker;
        Type type = new TypeToken<HashMap<String, EntityData>>() {
        }.getType();
        this.entityHashMap = xpTweaker.getDataStorage().loadJson(type, "entityxp.json");
    }

    @SubscribeEvent
    public void xpDrop(LivingExperienceDropEvent event) {
        String entityName = event.getEntityLiving().getClass().getName();
        if(mc.gameSettings.showDebugInfo) {
            XPTweaker.logger.debug("Killed: " + entityName);
        }
        if(entityHashMap.containsKey(entityName)) {
            EntityData entityData = entityHashMap.get(entityName);
            event.setDroppedExperience(entityData.getXp());
        }

    }
}
