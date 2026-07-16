package com.kongrarainforest;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class KongraArmorItem extends ArmorItem {
    public KongraArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.kongrarainforest.kongra_armor").formatted(Formatting.GREEN));
        super.appendTooltip(stack, world, tooltip, context);
    }
}