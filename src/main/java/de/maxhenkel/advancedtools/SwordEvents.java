package de.maxhenkel.advancedtools;

import de.maxhenkel.advancedtools.items.tools.AdvancedSword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SwordEvents {

    @SubscribeEvent
    public void sweep(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        Entity targetEntity = event.getTarget();
        if (player == null) {
            return;
        }

        if (!(player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AdvancedSword)) {
            return;
        }

        if (!targetEntity.canBeAttackedWithItem()) {
            return;
        }
        if (targetEntity.hitByEntity(player)) {
            return;
        }
        float damage = (float) player.getAttribute(Attributes.field_233823_f_).getValue();
        float f1;

        if (targetEntity instanceof LivingEntity) {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((LivingEntity) targetEntity).getCreatureAttribute());
        } else {
            f1 = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), CreatureAttribute.UNDEFINED);
        }

        float cooldown = player.getCooledAttackStrength(0.5F);
        damage = damage * (0.2F + cooldown * cooldown * 0.8F);
        f1 = f1 * cooldown;

        if (damage > 0.0F || f1 > 0.0F) {
            boolean flag = cooldown > 0.9F;
            boolean flag1 = false;
            int i = 0;
            i = i + EnchantmentHelper.getKnockbackModifier(player);

            if (player.isSprinting() && flag) {
                ++i;
                flag1 = true;
            }

            boolean flag2 = flag && player.fallDistance > 0.0F && !player.func_233570_aj_() && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Effects.BLINDNESS) /*&& !player.isRiding()*/ && targetEntity instanceof LivingEntity;
            flag2 = flag2 && !player.isSprinting();

            damage = damage + f1;
            boolean flag3 = false;
            double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);

            if (flag && !flag2 && !flag1 && player.func_233570_aj_() && d0 < (double) player.getAIMoveSpeed()) {
                flag3 = true;
            }

            if (flag3) {
                float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * damage;

                for (LivingEntity entitylivingbase : player.world.getEntitiesWithinAABB(LivingEntity.class, targetEntity.getBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
                    if (entitylivingbase != player && entitylivingbase != targetEntity && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < 9.0D) {
                        entitylivingbase.func_233627_a_(0.4F, (double) MathHelper.sin(entitylivingbase.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(entitylivingbase.rotationYaw * ((float) Math.PI / 180F))));
                        entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), f3);
                    }
                }

                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                player.spawnSweepParticles();
            }
        }
    }

}
