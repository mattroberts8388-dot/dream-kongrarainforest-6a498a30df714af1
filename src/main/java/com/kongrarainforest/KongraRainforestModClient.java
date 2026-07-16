package com.kongrarainforest;

import com.kongrarainforest.entity.JaguarEntity;
import com.kongrarainforest.entity.KongraEntity;
import com.kongrarainforest.entity.RainforestBeetleEntity;
import com.kongrarainforest.entity.ToucanEntity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.util.Identifier;

public class KongraRainforestModClient implements ClientModInitializer {
    // KONGRA - biped (gorilla body)
    private static final EntityModelLayer KONGRA_LAYER = new EntityModelLayer(new Identifier(KongraRainforestMod.MOD_ID, "kongra"), "main");
    private static final Identifier KONGRA_TEXTURE = new Identifier(KongraRainforestMod.MOD_ID, "textures/entity/kongra.png");

    // Jaguar - quadruped
    private static final EntityModelLayer JAGUAR_LAYER = new EntityModelLayer(new Identifier(KongraRainforestMod.MOD_ID, "jaguar"), "main");
    private static final Identifier JAGUAR_TEXTURE = new Identifier(KongraRainforestMod.MOD_ID, "textures/entity/jaguar.png");

    // Toucan - bird
    private static final EntityModelLayer TOUCAN_LAYER = new EntityModelLayer(new Identifier(KongraRainforestMod.MOD_ID, "toucan"), "main");
    private static final Identifier TOUCAN_TEXTURE = new Identifier(KongraRainforestMod.MOD_ID, "textures/entity/toucan.png");

    // Rainforest Beetle - spider
    private static final EntityModelLayer BEETLE_LAYER = new EntityModelLayer(new Identifier(KongraRainforestMod.MOD_ID, "rainforest_beetle"), "main");
    private static final Identifier BEETLE_TEXTURE = new Identifier(KongraRainforestMod.MOD_ID, "textures/entity/rainforest_beetle.png");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(KONGRA_LAYER, () -> TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 32));
        EntityRendererRegistry.register(KongraRainforestMod.KONGRA, ctx -> new MobEntityRenderer<KongraEntity, BipedEntityModel<KongraEntity>>(ctx, new BipedEntityModel<KongraEntity>(ctx.getPart(KONGRA_LAYER)) {}, 0.7f) {
            @Override public Identifier getTexture(KongraEntity entity) { return KONGRA_TEXTURE; }
        });

        EntityModelLayerRegistry.registerModelLayer(JAGUAR_LAYER, () -> TexturedModelData.of(QuadrupedEntityModel.getModelData(0, Dilation.NONE), 64, 32));
        EntityRendererRegistry.register(KongraRainforestMod.JAGUAR, ctx -> new MobEntityRenderer<JaguarEntity, QuadrupedEntityModel<JaguarEntity>>(ctx, new QuadrupedEntityModel<JaguarEntity>(ctx.getPart(JAGUAR_LAYER), true, 4.0f, 4.0f, 1.0f, 1.0f, 24) {}, 0.5f) {
            @Override public Identifier getTexture(JaguarEntity entity) { return JAGUAR_TEXTURE; }
        });

        EntityModelLayerRegistry.registerModelLayer(TOUCAN_LAYER, () -> ChickenEntityModel.getTexturedModelData());
        EntityRendererRegistry.register(KongraRainforestMod.TOUCAN, ctx -> new MobEntityRenderer<ToucanEntity, ChickenEntityModel<ToucanEntity>>(ctx, new ChickenEntityModel<ToucanEntity>(ctx.getPart(TOUCAN_LAYER)) {}, 0.3f) {
            @Override public Identifier getTexture(ToucanEntity entity) { return TOUCAN_TEXTURE; }
        });

        EntityModelLayerRegistry.registerModelLayer(BEETLE_LAYER, () -> SpiderEntityModel.getTexturedModelData());
        EntityRendererRegistry.register(KongraRainforestMod.RAINFOREST_BEETLE, ctx -> new MobEntityRenderer<RainforestBeetleEntity, SpiderEntityModel<RainforestBeetleEntity>>(ctx, new SpiderEntityModel<RainforestBeetleEntity>(ctx.getPart(BEETLE_LAYER)) {}, 0.4f) {
            @Override public Identifier getTexture(RainforestBeetleEntity entity) { return BEETLE_TEXTURE; }
        });
    }
}