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

// KONGRA's lair -- a large, overgrown stepped-pyramid ruin with a great hall
// inside big enough for the whole gang (Kongra, jaguar, toucan, beetle) to
// occupy at once. Placed sparsely in jungle biomes via
// BiomeModifications.addFeature() in KongraRainforestMod. Built entirely
// from full-cube blocks (no stairs/slabs) to avoid directional BlockState
// mistakes, and the hall is left unlit on purpose so hostile mobs --
// including KONGRA -- naturally spawn there under vanilla's light-level
// spawning rules, instead of needing fragile structure-bound spawn logic.
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

        // Five solid, shrinking square tiers, each 3 blocks tall -- a much
        // taller and wider silhouette than a small step pyramid.
        int[] radii = {9, 7, 5, 3, 1};
        int tierHeight = 3;
        int y = 0;
        for (int tier = 0; tier < radii.length; tier++) {
            int r = radii[tier];
            Block block = (tier % 2 == 0) ? Blocks.MOSSY_COBBLESTONE : Blocks.CRACKED_STONE_BRICKS;
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    for (int dy = 0; dy < tierHeight; dy++) {
                        setBlockState(world, base.add(dx, y + dy, dz), block.getDefaultState());
                    }
                }
            }
            y += tierHeight;
        }

        // Chiseled corner pillars on the base tier, full tier height.
        int baseR = radii[0];
        int[][] corners = {{-baseR, -baseR}, {-baseR, baseR}, {baseR, -baseR}, {baseR, baseR}};
        for (int[] c : corners) {
            for (int dy = 0; dy < tierHeight; dy++) {
                setBlockState(world, base.add(c[0], dy, c[1]), Blocks.CHISELED_STONE_BRICKS.getDefaultState());
            }
        }

        // KONGRA's great hall -- a real room, not a closet. 9x9 floor, 5
        // blocks of headroom, big enough for the whole gang to be inside
        // at once. Deliberately unlit.
        int hallR = 4;
        for (int dx = -hallR; dx <= hallR; dx++) {
            for (int dz = -hallR; dz <= hallR; dz++) {
                for (int dy = 0; dy <= 4; dy++) {
                    setBlockState(world, base.add(dx, dy, dz), Blocks.CAVE_AIR.getDefaultState());
                }
            }
        }
        // Hall floor, so it reads as a built room rather than a hole.
        for (int dx = -hallR; dx <= hallR; dx++) {
            for (int dz = -hallR; dz <= hallR; dz++) {
                setBlockState(world, base.add(dx, -1, dz), Blocks.CRACKED_STONE_BRICKS.getDefaultState());
            }
        }
        // Wide, tall entrance passage on the south face -- easily fits
        // KONGRA (1.4 wide, 2.6 tall) alongside a companion.
        for (int dz = hallR + 1; dz <= baseR; dz++) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = 0; dy <= 3; dy++) {
                    setBlockState(world, base.add(dx, dy, dz), Blocks.CAVE_AIR.getDefaultState());
                }
            }
        }

        // A cavern beneath the great hall, reached by a shaft down from the
        // hall floor -- gives the lair real depth instead of ending at one
        // room, without needing to know where any other ruin generated.
        carveCavern(world, random, base);

        // Vines on the tiers' east/west faces for the overgrown look --
        // scaled up to the bigger structure.
        for (int tier = 0; tier < radii.length; tier++) {
            int r = radii[tier];
            for (int i = 0; i < 3; i++) {
                int vy = tier * tierHeight + 1 + random.nextInt(Math.max(1, tierHeight - 1));
                int vz = -r + 2 + random.nextInt(Math.max(1, 2 * r - 4));
                if (random.nextFloat() < 0.8f) {
                    setBlockState(world, base.add(-r - 1, vy, vz), Blocks.VINE.getDefaultState().with(VineBlock.EAST, true));
                }
                if (random.nextFloat() < 0.8f) {
                    setBlockState(world, base.add(r + 1, vy, vz), Blocks.VINE.getDefaultState().with(VineBlock.WEST, true));
                }
            }
        }

        return true;
    }

    // Vertical shaft down from the hall floor into a cluster of overlapping
    // carved spheres along a gently wandering path -- a real cave chamber
    // rather than another box room -- with a built floor at the bottom so
    // it reads as KONGRA's den.
    private void carveCavern(StructureWorldAccess world, Random random, BlockPos base) {
        int shaftDepth = 12 + random.nextInt(6);
        // dy starts at 0 (not 1) so the shaft's top layer lands exactly on
        // the hall floor's y-level and punches an opening through it --
        // starting one below left the floor solid over a sealed shaft.
        for (int dy = 0; dy <= shaftDepth; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    setBlockState(world, base.add(dx, -1 - dy, dz), Blocks.CAVE_AIR.getDefaultState());
                }
            }
        }

        BlockPos.Mutable center = base.mutableCopy().move(0, -1 - shaftDepth, 0);
        int blobs = 5 + random.nextInt(3);
        for (int i = 0; i < blobs; i++) {
            int r = 4 + random.nextInt(4);
            carveSphere(world, center, r);
            center.move(random.nextInt(7) - 3, -(1 + random.nextInt(3)), random.nextInt(7) - 3);
        }

        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                setBlockState(world, center.add(dx, -1, dz), Blocks.MOSSY_COBBLESTONE.getDefaultState());
            }
        }
    }

    private void carveSphere(StructureWorldAccess world, BlockPos center, int r) {
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (dx * dx + dy * dy + dz * dz <= r * r) {
                        setBlockState(world, center.add(dx, dy, dz), Blocks.CAVE_AIR.getDefaultState());
                    }
                }
            }
        }
    }
}
