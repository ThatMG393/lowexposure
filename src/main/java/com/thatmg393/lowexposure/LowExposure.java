package com.thatmg393.lowexposure;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import foundry.veil.platform.VeilEventPlatform;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(
    value=LowExposure.MOD_ID,
    dist={ Dist.CLIENT }
)
@EventBusSubscriber(
    modid = LowExposure.MOD_ID,
    bus = EventBusSubscriber.Bus.MOD,
    value = Dist.CLIENT
)   
public class LowExposure {
    public static final String MOD_ID = "lowexposure";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation LOW_EXPOSURE_PIPELINE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "lowexpo_pipeline");
    public static ResourceLocation LOW_EXPOSURE_SHADER = ResourceLocation.fromNamespaceAndPath(MOD_ID, "lowexpo_shader");

    public static ShaderConfig LOADED_CONFIG = ConfigApiJava.registerAndLoadConfig(
        ShaderConfig::new, RegisterType.CLIENT
    );
    
    public LowExposure() { }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Low Exposure : loaded!");
        LOGGER.info("Here I am! (null)");

        if (!ModList.get().isLoaded("veil")) {
            LOGGER.error("Veil is NOT installed!");
            LOGGER.error("Veil is required to see the post-processing effect!");

            return;
        }
            
        VeilEventPlatform.INSTANCE.onVeilRenderLevelStage((
            stage, levelRenderer, bufferSource,
            matrixStack, matrix4fc, matrix4fc2,
            n, deltaTracker, camera, frustum
        ) -> {
            if (LOADED_CONFIG.enabled) {
                if (VeilRenderSystem.renderer().getPostProcessingManager().isActive(LOW_EXPOSURE_PIPELINE)) return;
                VeilRenderSystem.renderer().getPostProcessingManager().add(LOW_EXPOSURE_PIPELINE);
            } else if (VeilRenderSystem.renderer().getPostProcessingManager().isActive(LOW_EXPOSURE_PIPELINE)) {
                VeilRenderSystem.renderer().getPostProcessingManager().remove(LOW_EXPOSURE_PIPELINE);
            }
        });

        VeilEventPlatform.INSTANCE.preVeilPostProcessing((
            name, pipeline, context
        ) -> {
            if (!LOW_EXPOSURE_PIPELINE.equals(name)) return;
            ShaderProgram program = context.getShader(LOW_EXPOSURE_SHADER);
            if (program == null) return;

            program.getUniform("uBrightness").setFloat(LOADED_CONFIG.brightness);
            program.getUniform("uContrast").setFloat(LOADED_CONFIG.contrast);
            program.getUniform("uSaturation").setFloat(LOADED_CONFIG.saturation);

            program.getUniform("uLuma").setVector(
                LOADED_CONFIG.luma.get(0),
                LOADED_CONFIG.luma.get(1),
                LOADED_CONFIG.luma.get(2)
            );
        });
            
        LOGGER.info("Successfully registered post-processing shader!");

        if (!ModList.get().isLoaded("thebrokenscript")) {
            LOGGER.info("This mod is best used with The Broken Script!");
        }
    }
}
