package org.chemthunder.acropolis.core.cca.skyfall;

import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.minecraft.world.attribute.timeline.EasingType;
import org.chemthunder.acropolis.core.Acropolis;
import org.chemthunder.acropolis.core.cca.skyfall.obj.AbstractionInstance;
import org.chemthunder.acropolis.core.cca.skyfall.obj.SightInstance;
import org.chemthunder.acropolis.core.cca.skyfall.obj.WeaponCutscene;
import org.chemthunder.acropolis.core.index.data.ADamageTypes;
import org.chemthunder.acropolis.core.networking.s2c.FlashPayload;
import org.chemthunder.acropolis.core.utilities.Skyfall;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.List;
import java.util.UUID;

/**
 * @author Chemthunder
 */
@SuppressWarnings("SpellCheckingInspection")
public class SkyfallWorldComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<SkyfallWorldComponent> KEY = ComponentRegistryV3.INSTANCE.getOrCreate(
            Acropolis.id("skyfall"),
            SkyfallWorldComponent.class
    );
    private final World world;

    public static final float MAX_SUNSCALE = 14;

    public static final int TIME_UNTIL_SUN = (6 * 20);
    public static final int TIME_UNTIL_FLASH = (2 * 20);

    private @Nullable Vec3d pos = null;
    private @Nullable Vec3d boxTarget = null;

    private @Nullable UUID boxTargetUUID = null;

    public boolean active = false;
    public boolean sunVisible = false;
    public boolean externalVisible = false;
    public boolean tickingUntilFlash = false;
    private boolean boxActive = false;

    private float sunscale = 0.0F;

    private int timeUntilSun = TIME_UNTIL_SUN;
    private int timeUntilFlash = TIME_UNTIL_FLASH;

    private float boxLerp = 0.0F;
    private float boxLerpUp = 0.0F;

    private boolean innovatorActive = false;
    private float innovatorSize = 0.0F;
    private int innovatorDialogueIndex = 0;

    private boolean innovatorSpeechOver = false;
    private int innovatorFinalizeDelay = 80;
    private int playerId;

    public SightInstance sight;
    public WeaponCutscene weaponCutscene;
    public AbstractionInstance abs;

    public SkyfallWorldComponent(World world) {
        this.world = world;

        this.sight = new SightInstance(world);
        this.weaponCutscene = new WeaponCutscene(world);
        this.abs = new AbstractionInstance(world);
    }

    public void tick() {
        sight.tick();
        weaponCutscene.tick();

        if (active) {
            if (timeUntilSun > 0) {
                timeUntilSun--;
                if (timeUntilSun == 0) {
                    sunVisible = true;
                    sync();
                }
            }

            if (sunVisible) {
                if (sunscale < MAX_SUNSCALE) {
                    sunscale += 0.025F;
                }
            }

            if (!tickingUntilFlash) {
                if (sunscale >= MAX_SUNSCALE) {
                    tickingUntilFlash = true;
                    sync();
                }
            }

            if (tickingUntilFlash) {
                if (timeUntilFlash > 0) {
                    timeUntilFlash--;
                    if (timeUntilFlash == 0) { // final flash
                        NetworkingUtils.sendForAllPlayers(world, new FlashPayload(0xFFa503fc, EasingType.OUT_EXPO));
                        externalVisible = true;
                        sync();

                        if (world instanceof ServerWorld serverWorld) {
                            serverWorld.setTimeOfDay(18000);
                        }
                    }
                }
            }
        }

        if (innovatorActive) {
            if (innovatorSize < 1.0F) {
                innovatorSize += 0.01F;
                sync();
            }
        } else {
            if (innovatorSize > 0.0F) {
                innovatorSize -= 0.05F;
                sync();
            }
        }

        if (innovatorSpeechOver) {
            if (innovatorFinalizeDelay > 0) {
                innovatorFinalizeDelay--;
                if (innovatorFinalizeDelay <= 0) {
                    innovatorActive = false;
                    sync();
                }
            }
        }

        if ((boxActive && boxTarget != null)) { // permakill box
            if (boxLerp > 0.0F) {
                boxLerp -= 0.01F;
                if (boxLerp <= 0) {
                    boxActive = false;
                    boxTarget = null;
                    NetworkingUtils.sendForAllPlayers(world, new FlashPayload(0xFFffffff, EasingType.LINEAR));
                    if (boxTargetUUID != null) {
                        PlayerEntity target = world.getPlayerByUuid(boxTargetUUID);

                        if (target != null) {
                            if (world instanceof ServerWorld serverWorld) {
                                target.damage(serverWorld, target.getDamageSources().create(ADamageTypes.REPURPOSE), Float.MAX_VALUE);
                            }
                        }
                    }
                    sync();
                }
            }

            if (boxLerpUp < 1.0F) {
                boxLerpUp += 0.01F;
                if (boxLerpUp >= 1.0F) {
                    sync();
                }
            }
        }
    }

    public void sync() {
        KEY.sync(world);
    }

    public void readData(ReadView readView) {
        pos = readView.read("Pos", Vec3d.CODEC).orElse(null);
        boxTarget = readView.read("BoxTarget", Vec3d.CODEC).orElse(null);
        boxTargetUUID = readView.read("BoxTargetUUID", Uuids.CODEC).orElse(null);

        active = readView.getBoolean("Active", false);
        sunVisible = readView.getBoolean("SunVisible", false);
        externalVisible = readView.getBoolean("ExternalVisible", false);
        tickingUntilFlash = readView.getBoolean("TickingUntilFlash", false);
        boxActive = readView.getBoolean("BoxActive", false);

        sunscale = readView.getFloat("Sunscale", 0.0F);
        boxLerp = readView.getFloat("BoxLerp", 0.0F);
        boxLerpUp = readView.getFloat("BoxLerpUp", 0.0F);

        timeUntilSun = readView.getInt("TimeUntilSun", TIME_UNTIL_SUN);
        timeUntilFlash = readView.getInt("TimeUntilFlash", TIME_UNTIL_FLASH);

        innovatorActive = readView.getBoolean("InnovatorActive", false);
        innovatorSize = readView.getFloat("InnovatorSize", 0.0F);
        innovatorDialogueIndex = readView.getInt("InnovatorDialogueIndex", 0);
        innovatorSpeechOver = readView.getBoolean("InnovatorSpeechOver", false);
        innovatorFinalizeDelay = readView.getInt("InnovatorFinalizeDelay", 80);

        playerId = readView.getInt("PlayerId", 0);

        sight = sight.read(readView);
        weaponCutscene = weaponCutscene.read(readView);
        abs = abs.read(readView);
    }

    public void writeData(WriteView writeView) {
        if (pos != null) writeView.put("Pos", Vec3d.CODEC, pos);
        if (boxTarget != null) writeView.put("BoxTarget", Vec3d.CODEC, boxTarget);
        if (boxTargetUUID != null) writeView.put("BoxTargetUUID", Uuids.CODEC, boxTargetUUID);

        writeView.putBoolean("Active", active);
        writeView.putBoolean("SunVisible", sunVisible);
        writeView.putBoolean("ExternalVisible", externalVisible);
        writeView.putBoolean("TickingUntilFlash", tickingUntilFlash);
        writeView.putBoolean("BoxActive", boxActive);

        writeView.putFloat("Sunscale", sunscale);
        writeView.putFloat("BoxLerp", boxLerp);
        writeView.putFloat("BoxLerpUp", boxLerpUp);

        writeView.putInt("TimeUntilSun", timeUntilSun);
        writeView.putInt("TimeUntilFlash", timeUntilFlash);

        writeView.putBoolean("InnovatorActive", innovatorActive);
        writeView.putFloat("InnovatorSize", innovatorSize);
        writeView.putInt("InnovatorDialogueIndex", innovatorDialogueIndex);
        writeView.putBoolean("InnovatorSpeechOver", innovatorSpeechOver);
        writeView.putInt("InnovatorFinalizeDelay", innovatorFinalizeDelay);

        writeView.putInt("PlayerId", playerId);

        sight.write(writeView);
        weaponCutscene.write(writeView);
        abs.write(writeView);
    }

    public void activate() {
        active = true;
        sunVisible = false;
        externalVisible = false;
        tickingUntilFlash = false;

        sunscale = 0.0F;

        timeUntilSun = TIME_UNTIL_SUN;
        sync();

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.setTimeOfDay(18000);
        }
    }

    public void close() {
        active = false;
        sunVisible = false;
        externalVisible = false;
        tickingUntilFlash = false;

        sunscale = 0.0F;

        timeUntilSun = TIME_UNTIL_SUN;
        timeUntilFlash = TIME_UNTIL_FLASH;
        sync();
    }

    public void createBox(Vec3d target, Entity entity) {
        boxTarget = target;
        boxLerp = 1.0F;
        boxLerpUp = 0.0F;
        boxActive = true;

        if (entity instanceof LivingEntity living) {
            boxTargetUUID = living.getUuid();
        }
        sync();
    }

    public void openIv() {
        this.innovatorSize = 0.0F;
        this.innovatorActive = true;
        this.innovatorDialogueIndex = 0;

        this.innovatorSpeechOver = false;
        this.innovatorFinalizeDelay = 80;

        sync();
    }

    public void closeIv() {
        this.innovatorActive = false;
        this.playerId = 0;

        sync();
    }

    public void stepIv() {
        if (this.innovatorDialogueIndex < Skyfall.getInnovatorSpeech().size() - 1) {
            this.innovatorDialogueIndex++;
            sync();
        } else {
            if (!this.innovatorSpeechOver) {
                this.innovatorSpeechOver = true;
                sync();
            }
        }

        List<Text> texts = Skyfall.getInnovatorSpeech();

        Text text = texts.get(this.innovatorDialogueIndex);

        if (this.innovatorDialogueIndex == 8) {
            if (this.getInnovatorTarget() instanceof ServerPlayerEntity serverPlayer) {
                serverPlayer.changeGameMode(GameMode.SPECTATOR);

                this.playerId = 0;
                this.sync();
            }
        }

        for (PlayerEntity player : world.getPlayers()) {
            if (!text.getString().isBlank()) {
                player.sendMessage(text, false);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    @Nullable
    public Vec3d getPos() {
        return pos;
    }

    public void setPos(@Nullable Vec3d pos) {
        this.pos = pos;
        sync();
    }

    public @Nullable Vec3d getBoxTarget() {
        return boxTarget;
    }

    public float getSunscale() {
        return sunscale;
    }

    public float getBoxLerp() {
        return boxLerp;
    }

    public float getBoxLerpUp() {
        return boxLerpUp;
    }

    public boolean isInnovatorActive() {
        return innovatorActive;
    }

    public float getInnovatorSize() {
        return innovatorSize;
    }

    @Nullable
    public PlayerEntity getInnovatorTarget() {
        Entity entity = this.world.getEntityById(this.playerId);

        if (entity instanceof PlayerEntity player) {
            return player;
        }
        return null;
    }

    public void setInnovatorTarget(Entity entity) {
        this.playerId = entity.getId();
        this.sync();
    }

    public boolean isPlayerHead(Entity entity) {
        return entity.getId() == this.playerId && !(entity instanceof ServerPlayerEntity serverPlayer && serverPlayer.isSpectator());
    }
}
