package com.kongrarainforest.util;

import com.kongrarainforest.KongraRainforestMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class KongraArmorUtil {
    public static int countKongraPieces(PlayerEntity player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem() == KongraRainforestMod.KONGRA_HELMET
                || stack.getItem() == KongraRainforestMod.KONGRA_CHESTPLATE
                || stack.getItem() == KongraRainforestMod.KONGRA_LEGGINGS
                || stack.getItem() == KongraRainforestMod.KONGRA_BOOTS) {
                count++;
            }
        }
        return count;
    }

    public static boolean hasFullKongraArmor(PlayerEntity player) {
        return countKongraPieces(player) >= 4;
    }
}