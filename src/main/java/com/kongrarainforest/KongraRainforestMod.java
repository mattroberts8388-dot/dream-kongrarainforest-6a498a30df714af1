package com.kongrarainforest;

import com.kongrarainforest.entity.JaguarEntity;
import com.kongrarainforest.entity.KongraEntity;
import com.kongrarainforest.entity.RainforestBeetleEntity;
import com.kongrarainforest.entity.ToucanEntity;
import com.kongrarainforest.item.KongraArmorMaterial;
import com.kongrarainforest.item.ModFoods;
import com.kongrarainforest.rain.RainDamageHandler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KongraRainforestMod implements ModInitializer {
    public static final String MOD_ID = "kongrarainforest";

    // Items
    public static final Item RAINFOREST_GEM = new Item(new FabricItemSettings());
    public static final Item KONGRA_SCALE = new Item(new FabricItemSettings());
    public static final Item CANOPY_FRUIT = new Item(new FabricItemSettings().food(ModFoods.CANOPY_FRUIT));

    // Armor
    public static final Item KONGRA_HELMET = new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final Item KONGRA_CHESTPLATE = new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final Item KONGRA_LEGGINGS = new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final Item KONGRA_BOOTS = new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new FabricItemSettings());

    // Block
    public static final Block RAINFOREST_GEM_ORE = new Block(net.minecraft.block.AbstractBlock.Settings.copy(Blocks.STONE).strength(3.5f).requiresTool());
    public static final BlockItem RAINFOREST_GEM_ORE_ITEM = new BlockItem(RAINFOREST_GEM_ORE, new FabricItemSettings());

    // Entities
    public static final EntityType<KongraEntity> KONGRA = Registry.register(
        Registries.ENTITY_TYPE, new Identifier(MOD_ID, "kongra"),
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KongraEntity::new)
            .dimensions(EntityDimensions.fixed(1.4f, 2.6f)).build());

    public static final EntityType<JaguarEntity> JAGUAR = Registry.register(
        Registries.ENTITY_TYPE, new Identifier(MOD_ID, "jaguar"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, JaguarEntity::new)
            .dimensions(EntityDimensions.fixed(0.9f, 0.9f)).build());

    public static final EntityType<ToucanEntity> TOUCAN = Registry.register(
        Registries.ENTITY_TYPE, new Identifier(MOD_ID, "toucan"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ToucanEntity::new)
            .dimensions(EntityDimensions.fixed(0.5f, 0.7f)).build());

    public static final EntityType<RainforestBeetleEntity> RAINFOREST_BEETLE = Registry.register(
        Registries.ENTITY_TYPE, new Identifier(MOD_ID, "rainforest_beetle"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RainforestBeetleEntity::new)
            .dimensions(EntityDimensions.fixed(0.7f, 0.5f)).build());

    public static final RegistryKey<ItemGroup> KONGRA_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(MOD_ID, "main"));
    public static final ItemGroup KONGRA_GROUP = Registry.register(Registries.ITEM_GROUP, KONGRA_GROUP_KEY,
        FabricItemGroup.builder()
            .icon(() -> new net.minecraft.item.ItemStack(KONGRA_CHESTPLATE))
            .displayName(Text.translatable("itemGroup.kongrarainforest.main"))
            .entries((displayContext, entries) -> {
                entries.add(RAINFOREST_GEM);
                entries.add(KONGRA_SCALE);
                entries.add(CANOPY_FRUIT);
                entries.add(KONGRA_HELMET);
                entries.add(KONGRA_CHESTPLATE);
                entries.add(KONGRA_LEGGINGS);
                entries.add(KONGRA_BOOTS);
                entries.add(RAINFOREST_GEM_ORE_ITEM);
            })
            .build());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "rainforest_gem"), RAINFOREST_GEM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kongra_scale"), KONGRA_SCALE);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "canopy_fruit"), CANOPY_FRUIT);

        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kongra_helmet"), KONGRA_HELMET);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kongra_chestplate"), KONGRA_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kongra_leggings"), KONGRA_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "kongra_boots"), KONGRA_BOOTS);

        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "rainforest_gem_ore"), RAINFOREST_GEM_ORE);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "rainforest_gem_ore"), RAINFOREST_GEM_ORE_ITEM);

        // Entity attributes
        FabricDefaultAttributeRegistry.register(KONGRA, KongraEntity.createKongraAttributes());
        FabricDefaultAttributeRegistry.register(JAGUAR, JaguarEntity.createJaguarAttributes());
        FabricDefaultAttributeRegistry.register(TOUCAN, ToucanEntity.createToucanAttributes());
        FabricDefaultAttributeRegistry.register(RAINFOREST_BEETLE, RainforestBeetleEntity.createBeetleAttributes());

        // Spawns in jungle biomes (existing biomes only)
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.CREATURE, JAGUAR, 8, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.CREATURE, TOUCAN, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.CREATURE, RAINFOREST_BEETLE, 10, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
            SpawnGroup.MONSTER, KONGRA, 2, 1, 1);

        // Rain damage system
        RainDamageHandler.register();

        // Kongra Kingdom jungle atmosphere (colors, ambient particles, spawn rate)
        KongraKingdomAtmosphere.register();
    }
}
