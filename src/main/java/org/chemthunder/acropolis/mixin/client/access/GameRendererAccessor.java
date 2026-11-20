package org.chemthunder.acropolis.mixin.client.access;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author Chemthunder
 */
@Mixin(value = GameRenderer.class)
public interface GameRendererAccessor {
    @Accessor("postProcessorId")
    Identifier PostEffectId();

    @Accessor("postProcessorId")
    void PostEffectId(Identifier id);

    @Accessor("postProcessorEnabled")
    void EffectActive(boolean enabled);
}
