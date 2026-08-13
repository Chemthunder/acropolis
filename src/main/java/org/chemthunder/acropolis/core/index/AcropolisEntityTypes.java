package org.chemthunder.acropolis.core.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.entity.SpellshotEntity;

/**
 * @author Chemthunder
 */
public interface AcropolisEntityTypes {
    EntityTypeRegistrant plugin = new EntityTypeRegistrant(Acropolis.MOD_ID);

    EntityType<SpellshotEntity> SPELL_SHOT = plugin.register("spell_shot", EntityType.Builder.create(
            SpellshotEntity::new,
            SpawnGroup.MISC
    ).dimensions(1.0F, 1.0F));

    static void init() {}

    static void clinit() {
        EntityRendererFactories.register(SPELL_SHOT, EmptyEntityRenderer::new);
    }
}
