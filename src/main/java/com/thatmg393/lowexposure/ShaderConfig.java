package com.thatmg393.lowexposure;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import net.minecraft.resources.ResourceLocation;

public class ShaderConfig extends Config {
    public ShaderConfig() {
        super(
            ResourceLocation.fromNamespaceAndPath(
                LowExposure.MOD_ID,
                "lowexpo_config"
            )
        );
    }

    public float brightness = -0.05f;
    public float contrast = 1.15f;
    public float saturation = 0.50f;
    public ValidatedList<Float> luma = ValidatedList.ofFloat(
        0.299f, 0.587f, 0.114f
    );
}
