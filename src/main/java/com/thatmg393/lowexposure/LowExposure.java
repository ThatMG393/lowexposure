package com.thatmg393.lowexposure;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(LowExposure.MOD_ID)
@EventBusSubscriber(
    modid = LowExposure.MOD_ID,
    bus = EventBusSubscriber.Bus.MOD,
    value = Dist.CLIENT
)   
public class LowExposure {
    public static final String MOD_ID = "lowexposure";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public LowExposure(IEventBus modEventBus, ModContainer modContainer) {
        if (!ModList.get().isLoaded("veil")) {
            LOGGER.error("Veil is NOT installed!");
            LOGGER.error("Veil is required to see the post-processinf effect!");
        }

        LOGGER.info("Here I am! (null)");

        if (!ModList.get().isLoaded("thebrokenscript")) {
            LOGGER.info("Best used with The Broken Script!");
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
