package org.chemthunder.acropolis.core.index.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.block.entity.AbstractEndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

/**
 * @author Chemthunder
 */
public interface AcropolisRenderLayers {
    Function<Identifier, RenderLayer> NOISE = Util.memoize(tex ->
            RenderLayer.of("noise", RenderSetup.builder(AcropolisRenderPipelines.NOISE)
                    .texture("Sampler0", tex)
                    .build())
    );

    RenderLayer NOISE_NONFUNC = RenderLayer.of(
            "noise_nonfunc",
            RenderSetup.builder(AcropolisRenderPipelines.NOISE)
                    .texture("Sampler0", AbstractEndPortalBlockEntityRenderer.SKY_TEXTURE)
                    .build()
    );

    static void clinit() {}

    static RenderLayer noiseTextured(Identifier texture) {
        return NOISE.apply(texture);
    }

    static RenderLayer noise() {
        return NOISE_NONFUNC;
    }
}
