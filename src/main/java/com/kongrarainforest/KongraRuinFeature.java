package com.kongrarainforest;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

// A small, overgrown stepped-pyramid ruin -- KONGRA's lair. Placed sparsely
// in jungle biomes via BiomeModifications.addFeature() in KongraRainforestMod.
// Built entirely from full-cube blocks (no stairs/slabs) to avoid directional
// BlockState mistakes, and the interior den is left unlit on purpose so
// hostile mobs -- including KONGRA -- naturally spawn there under vanilla's
// light-level spawning rules, instead of needing fragile structure-bound
// spawn logic.
public class KongraRuinFeature extends Feature<DefaultFeatureConfig> {
    public KongraRuinFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        BlockPos base = origin.withY(
            world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.getX(), origin.getZ()) - 1);

        // Four solid, shrinking square tiers -- the stepped-pyramid silhouette.
        int[] radii = {4, 3, 2, 1};
        int y = 0;
        for (int tier = 0; tier < radii.length; tier++) {
            int r = radii[tier];
            Block block = (tier % 2 == 0) ? Blocks.MOSSY_COBBLESTONE : Blocks.CRACKED_STONE_BRICKS;
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    for (int dy = 0; dy < 2; dy++) {
                        setBlockState(world, base.add(dx, y + dy, dz), block.getDefaultState());
                    }
                }
            }
            y += 2;
        }

        // Chiseled corner pillars on the base tier.
        int baseR = radii[0];
        int[][] corners = {{-baseR, -baseR}, {-baseR, baseR}, {baseR, -baseR}, {baseR, baseR}};
        for (int[] c : corners) {
            for (int dy = 0; dy < 2; dy++) {
                setBlockState(world, base.add(c[0], dy, c[1]), Blocks.CHISELED_STONE_BRICKS.getDefaultState());
            }
        }

        // KONGRA's den -- a small hollow chamber carved into the base tier.
        // Deliberately unlit.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 1; dy++) {
                    setBlockState(world, base.add(dx, dy, dz), Blocks.CAVE_AIR.getDefaultState());
                }
            }
        }
        // Entrance passage on the south face.
        for (int dz = 2; dz <= baseR; dz++) {
            for (int dy = 0; dy <= 1; dy++) {
                setBlockState(world, base.add(0, dy, dz), Blocks.CAVE_AIR.getDefaultState());
            }
        }
        // Chamber floor, so it reads as a built room rather than a hole.
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                setBlockState(world, base.add(dx, -1, dz), Blocks.CRACKED_STONE_BRICKS.getDefaultState());
            }
        }

        // A handful of vines on the west/east faces of each tier for the overgrown look.
        for (int tier = 0; tier < radii.length; tier++) {
            int r = radii[tier];
            int vy = tier * 2 + 1;
            if (random.nextFloat() < 0.8f) {
                setBlockState(world, base.add(-r - 1, vy, 0), Blocks.VINE.getDefaultState().with(VineBlock.EAST, true));
            }
            if (random.nextFloat() < 0.8f) {
                setBlockState(world, base.add(r + 1, vy, 2), Blocks.VINE.getDefaultState().with(VineBlock.WEST, true));
            }
        }

        return true;
    }
}
