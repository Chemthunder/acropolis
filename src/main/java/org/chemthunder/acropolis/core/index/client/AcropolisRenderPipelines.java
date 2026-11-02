package org.chemthunder.acropolis.core.index.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.VertexFormats;
import org.chemthunder.acropolis.core.Acropolis;

public interface AcropolisRenderPipelines {

    RenderPipeline NOISE = RenderPipeline.builder()
            .withLocation(Acropolis.id("pipeline/noise"))
            .withVertexShader(Acropolis.id("core/noise"))
            .withFragmentShader(Acropolis.id("core/noise"))
            .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
            .withUniform("Projection", UniformType.UNIFORM_BUFFER)
            .withUniform("Globals", UniformType.UNIFORM_BUFFER)
            .withVertexFormat(VertexFormats.POSITION, VertexFormat.DrawMode.TRIANGLE_FAN)
            .withDepthWrite(false)
            .withCull(false)
            .build();

    static void clinit() {}
}
