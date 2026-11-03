package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.entity.render.MeteorEntityRenderer;
import org.chemthunder.acropolis.core.client.entity.render.PestilenceEntityRenderer;
import org.chemthunder.acropolis.core.client.entity.render.SwarmEntityRenderer;
import org.chemthunder.acropolis.core.entity.MeteorEntity;
import org.chemthunder.acropolis.core.entity.PestilenceEntity;
import org.chemthunder.acropolis.core.entity.SpellshotEntity;
import org.chemthunder.acropolis.core.entity.SwarmEntity;

/**
 * @author Chemthunder
 */
public interface AcropolisEntityTypes {
    EntityTypeRegistrant plugin = new EntityTypeRegistrant(Acropolis.MOD_ID);

    EntityType<SpellshotEntity> SPELL_SHOT = plugin.register("spell_shot", EntityType.Builder.create(
            SpellshotEntity::new,
            SpawnGroup.MISC
    ).dimensions(1.0F, 1.0F).disableSummon());

    EntityType<PestilenceEntity> PESTILENCE = plugin.register("pestilence", EntityType.Builder.<PestilenceEntity>create(
            PestilenceEntity::new,
            SpawnGroup.MISC
    ).dimensions(1.0F, 1.6F).disableSummon());

    EntityType<SwarmEntity> SWARM = plugin.register("swarm", EntityType.Builder.create(
            SwarmEntity::new,
            SpawnGroup.MISC
    ).dimensions(0.7F, 1.4F).disableSummon());

    EntityType<MeteorEntity> METEOR = plugin.register("meteor", EntityType.Builder.create(
            MeteorEntity::new,
            SpawnGroup.MISC
    ).dimensions(1, 1).makeFireImmune());

    static void init() {
        FabricDefaultAttributeRegistry.register(PESTILENCE, PestilenceEntity.createAttributes());
    }

    static void clinit() {
        EntityRendererFactories.register(SPELL_SHOT, EmptyEntityRenderer::new);
        EntityRendererFactories.register(PESTILENCE, PestilenceEntityRenderer::new);
        EntityRendererFactories.register(SWARM, SwarmEntityRenderer::new);
        EntityRendererFactories.register(METEOR, MeteorEntityRenderer::new);
    }
}
