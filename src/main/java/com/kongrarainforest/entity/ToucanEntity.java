package com.kongrarainforest.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ToucanEntity extends AnimalEntity {
    public ToucanEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createToucanAttributes() {
        return AnimalEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28)
            .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(2, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return com.kongrarainforest.KongraRainforestMod.TOUCAN.create(world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected boolean isFlappingWings() {
        return this.speed > 1.0e-5f;
    }
}