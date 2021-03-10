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

        if (!(player.getItemInHand(Hand.MAIN_HAND).getItem() instanceof AdvancedSword)) {
            return;
        }

        if (!targetEntity.isAttackable()) {
            return;
        }
        if (targetEntity.skipAttackInteraction(player)) {
            return;
        }
        float damage = (float) player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        float f1;

        if (targetEntity instanceof LivingEntity) {
            f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), ((LivingEntity) targetEntity).getMobType());
        } else {
            f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), CreatureAttribute.UNDEFINED);
        }

        float cooldown = player.getAttackStrengthScale(0.5F);
        damage = damage * (0.2F + cooldown * cooldown * 0.8F);
        f1 = f1 * cooldown;

        if (damage > 0.0F || f1 > 0.0F) {
            boolean flag = cooldown > 0.9F;
            boolean flag1 = false;
            int i = 0;
            i = i + EnchantmentHelper.getKnockbackBonus(player);

            if (player.isSprinting() && flag) {
                ++i;
                flag1 = true;
            }

            boolean flag2 = flag && player.fallDistance > 0.0F && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(Effects.BLINDNESS) /*&& !player.isRiding()*/ && targetEntity instanceof LivingEntity;
            flag2 = flag2 && !player.isSprinting();

            damage = damage + f1;
            boolean flag3 = false;
            double d0 = (double) (player.walkDist - player.walkDistO);

            if (flag && !flag2 && !flag1 && player.isOnGround() && d0 < (double) player.getSpeed()) {
                flag3 = true;
            }

            if (flag3) {
                float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * damage;

                for (LivingEntity entitylivingbase : player.level.getEntitiesOfClass(LivingEntity.class, targetEntity.getBoundingBox().inflate(1.0D, 0.25D, 1.0D))) {
                    if (entitylivingbase != player && entitylivingbase != targetEntity && !player.isAlliedTo(entitylivingbase) && player.distanceToSqr(entitylivingbase) < 9.0D) {
                        entitylivingbase.knockback(0.4F, MathHelper.sin(entitylivingbase.yRot * ((float) Math.PI / 180F)), -MathHelper.cos(entitylivingbase.yRot * ((float) Math.PI / 180F)));
                        entitylivingbase.hurt(DamageSource.playerAttack(player), f3);
                    }
                }

                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                player.sweepAttack();
            }
        }
    }

}
