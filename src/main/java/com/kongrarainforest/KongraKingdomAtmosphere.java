package com.kongrarainforest;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeParticleConfig;

// Layers real "Kongra Kingdom" atmosphere onto vanilla jungle biomes. A brand
// new placed Overworld biome isn't safely reachable on 1.20.1 — Fabric API's
// OverworldBiomes helper (the thing that makes this easy for the Nether/End)
// doesn't exist for this Minecraft version; doing it "for real" means hand
// -reconstructing vanilla's entire Overworld biome placement list. This is
// the supported, low-risk alternative: distinct colors, ambient particles,
// and denser wildlife on the jungle biomes players already explore, so a
// Kongra Kingdom jungle actually feels different to walk into.
public class KongraKingdomAtmosphere {
    public static void register() {
        BiomeModifications.create(new Identifier(KongraRainforestMod.MOD_ID, "kongra_kingdom_atmosphere"))
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE), context -> {
                // Deeper, richer jungle-canopy palette than vanilla's default.
                context.getEffects().setWaterColor(0x1E7A4A);
                context.getEffects().setWaterFogColor(0x0C3D25);
                context.getEffects().setFogColor(0x2E5E38);
                context.getEffects().setFoliageColor(0x2E7D32);
                context.getEffects().setGrassColor(0x4CAF50);

                // Faint drifting spores through the canopy — vanilla's own
                // Lush Caves ambient particle, reused here for a "living
                // jungle" feel without needing a custom particle type.
                context.getEffects().setParticleConfig(new BiomeParticleConfig(ParticleTypes.SPORE_BLOSSOM_AIR, 0.008F));

                // Slightly more wildlife than a vanilla jungle (10% -> 14%
                // base creature spawn chance) so the ecosystem feels alive.
                context.getSpawnSettings().setCreatureSpawnProbability(0.14F);
            });
    }
}
