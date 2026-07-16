package com.kongrarainforest.entity;

import com.kongrarainforest.util.KongraArmorUtil;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class KongraEntity extends HostileEntity {
    public KongraEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 50;
    }

    public static DefaultAttributeContainer.Builder createKongraAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 14.0)
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
            .add(EntityAttributes.GENERIC_ARMOR, 12.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.9));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(net.minecraft.entity.Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof PlayerEntity player) {
            // Players without full KONGRA armor take a brutal extra hit — they "don't stand a chance".
            if (!KongraArmorUtil.hasFullKongraArmor(player)) {
                player.damage(this.getDamageSources().mobAttack(this), 8.0f);
                player.addVelocity(0, 0.4, 0);
            }
        }
        return hit;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        // Take reduced damage from players not wearing KONGRA armor.
        if (source.getAttacker() instanceof PlayerEntity player && !KongraArmorUtil.hasFullKongraArmor(player)) {
            amount = amount * 0.25f;
        }
        return super.damage(source, amount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 1.5f;
    }
}