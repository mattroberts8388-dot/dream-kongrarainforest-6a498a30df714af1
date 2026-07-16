package com.kongrarainforest.rain;

import com.kongrarainforest.util.KongraArmorUtil;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class RainDamageHandler {
    // How long a player must be exposed to rain before damage starts (ticks). 15 seconds.
    private static final int EXPOSURE_THRESHOLD = 300;
    // Interval between damage applications once threshold is passed. 3 seconds.
    private static final int DAMAGE_INTERVAL = 60;

    // Per-player exposure counter keyed by uuid string hash via NBT-independent map.
    private static final java.util.Map<java.util.UUID, Integer> exposure = new java.util.HashMap<>();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                if (!world.isRaining()) {
                    continue;
                }
                for (PlayerEntity player : world.getPlayers()) {
                    if (player.isSpectator() || player.isCreative()) {
                        exposure.remove(player.getUuid());
                        continue;
                    }

                    BlockPos pos = player.getBlockPos();
                    boolean underOpenSky = world.isSkyVisible(pos);
                    boolean rainingHere = world.hasRain(pos);

                    if (underOpenSky && rainingHere && !player.isSubmergedInWater()) {
                        int current = exposure.getOrDefault(player.getUuid(), 0) + 1;

                        // KONGRA armor slows exposure buildup dramatically.
                        int pieces = KongraArmorUtil.countKongraPieces(player);
                        if (pieces >= 4) {
                            // Full armor: no rain damage at all.
                            exposure.put(player.getUuid(), 0);
                            continue;
                        }

                        exposure.put(player.getUuid(), current);

                        if (current >= EXPOSURE_THRESHOLD && current % DAMAGE_INTERVAL == 0) {
                            float damage = 1.0f + (pieces == 0 ? 1.0f : 0.0f);
                            DamageSource src = player.getDamageSources().magic();
                            player.damage(src, damage);
                        }
                    } else {
                        // Drying off gradually.
                        int current = exposure.getOrDefault(player.getUuid(), 0);
                        if (current > 0) {
                            exposure.put(player.getUuid(), Math.max(0, current - 2));
                        }
                    }
                }
            }
        });
    }
}