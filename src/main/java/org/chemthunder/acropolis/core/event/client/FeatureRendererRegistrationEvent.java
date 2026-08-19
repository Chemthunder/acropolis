package org.chemthunder.acropolis.core.event.client;

import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.chemthunder.acropolis.core.client.render.WingsFeatureRenderer;

/**
 * @author Chemthunder
 */
@SuppressWarnings("ALL")
public class FeatureRendererRegistrationEvent implements LivingEntityFeatureRendererRegistrationCallback {
    public void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?, ?> livingEntityRenderer, RegistrationHelper registrationHelper, EntityRendererFactory.Context context) {
        if (entityType == EntityType.PLAYER) {
            registrationHelper.register(new WingsFeatureRenderer<>((FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>) livingEntityRenderer, context));
        }
    }
}