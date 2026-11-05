package org.chemthunder.acropolis.core.cca.entity;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.client.skyfall.SwoonEvents;
import org.chemthunder.acropolis.core.index.APowerTypes;
import org.chemthunder.acropolis.core.index.ARegistries;
import org.chemthunder.acropolis.core.index.ASoundEvents;
import org.chemthunder.acropolis.core.networking.s2c.ShoutPayload;
import org.chemthunder.acropolis.core.skyfall.PowerHolder;
import org.chemthunder.acropolis.core.skyfall.PowerType;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class AvatarComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<AvatarComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("avatar"),
            AvatarComponent.class
    );
    private final PlayerEntity player;

    private PowerHolder holder = PowerHolder.CHEM;
    private List<PowerType> powers = new ArrayList<>(ARegistries.POWER_TYPE.stream().toList());

    private int powerIndex = 0;

    private List<Integer> swoonTargets = new ArrayList<>();
    private int swoonTime = 0;

    public AvatarComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        if (!powers.isEmpty()) {
            player.sendMessage(Text.literal(powerIndex + ":" + powers.get(powerIndex).getName()), true);
        }

        if (player.age % 20 == 0) {
            powers = ARegistries.POWER_TYPE.stream().toList();
            sync();
        }

        if (swoonTime > 0) {
            player.setVelocity(player.getRotationVec(0).multiply(1));

            for (LivingEntity target : player.getEntityWorld().getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(3), EntityPredicates.EXCEPT_SPECTATOR)) {
                if (target != player) {
                    swoonTargets.add(target.getId());
                }
            }

            swoonTime--;

            if (swoonTime <= 0) {
                player.playSound(ASoundEvents.SWOON);

                if (player.getEntityWorld() instanceof ServerWorld serverWorld) {
                    for (int code : swoonTargets) {
                        Entity entity = serverWorld.getEntityById(code);

                        if (entity instanceof LivingEntity target) {
                            target.damage(serverWorld, target.getDamageSources().magic(), 9);
                        }
                    }
                }
                SwoonEvents.opacity = 1.0F;
                swoonTargets.clear();


                sync();
            }
        }
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readData(ReadView readView) {
        holder = readView.read("Holder", PowerHolder.CODEC).orElse(PowerHolder.CHEM);
        powers = readView.read("Powers", ARegistries.POWER_TYPE.getCodec().listOf()).orElse(new ArrayList<>());

        powerIndex = readView.getInt("PowerIndex", 0);

        swoonTargets = readView.read("SwoonTargets", Codec.INT.listOf()).orElse(new ArrayList<>());
        swoonTime = readView.getInt("SwoonTime", 0);
    }

    public void writeData(WriteView writeView) {
        writeView.put("Holder", PowerHolder.CODEC, holder);

        if (!powers.isEmpty()) {
            writeView.put("Powers", ARegistries.POWER_TYPE.getCodec().listOf(), powers);
        }

        writeView.putInt("PowerIndex", powerIndex);

        if (!swoonTargets.isEmpty()) {
            writeView.put("SwoonTargets", Codec.INT.listOf(), swoonTargets);
        }
        writeView.putInt("SwoonTime", swoonTime);
    }

    public void iterate(boolean bl) {
        if (bl) {
            if (powerIndex < powers.size() - 1) {
                powerIndex++;
                sync();
            }
        } else {
            if (powerIndex > 0) {
                powerIndex--;
                sync();
            }
        }
    }

    public PowerType getCurrent() {
        return powers.get(powerIndex);
    }

    public void deployCurrent() {
        PowerType cur = this.getCurrent();
        NetworkingUtils.sendForAllPlayers(
                player.getEntityWorld(),
                new ShoutPayload(MiscUtils.formatString(cur.getName()), cur.getHolder().color)
        );
        cur.use(player);
    }

    public int getPowerIndex() {
        return powerIndex;
    }

    public List<PowerType> getPowers() {
        return powers;
    }

    public void swoon() {
        swoonTime = 5;
        sync();
    }
}
