package com.kongrarainforest.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoods {
    public static final FoodComponent CANOPY_FRUIT = new FoodComponent.Builder()
        .hunger(5).saturationModifier(0.8f).alwaysEdible()
        .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f)
        .build();
}