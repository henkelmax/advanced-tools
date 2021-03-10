package de.maxhenkel.advancedtools.mixins;

import de.maxhenkel.advancedtools.items.tools.AbstractTool;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    abstract ItemStack getItem();

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void onItemUse(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = getItem();
        if (!(stack.getItem() instanceof AbstractTool)) {
            return;
        }

        if (!source.isFire()) {
            return;
        }

        AbstractTool tool = (AbstractTool) stack.getItem();
        if (tool.isFireResistant(stack)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "fireImmune", at = @At("HEAD"), cancellable = true)
    public void isImmuneToFire(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = getItem();
        if (!(stack.getItem() instanceof AbstractTool)) {
            return;
        }
        AbstractTool tool = (AbstractTool) stack.getItem();
        if (tool.isFireResistant(stack)) {
            cir.setReturnValue(true);
        }
    }

}
