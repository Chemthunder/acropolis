package org.chemthunder.acropolis.core.client.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PestilenceEntityRenderState extends LivingEntityRenderState {
    public PestilenceEntity entity;

    public int age;
    public float tickProgress;

    public final AnimationState HOVER = new AnimationState();
}
