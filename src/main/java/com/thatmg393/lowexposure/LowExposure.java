package com.thatmg393.lowexposure;

@Mod(LowExposure.MOD_ID)
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)   
public class LowExposure {
    public static final String MOD_ID = "lowexposure";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public LowExposure(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Here I am! (null)");
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
