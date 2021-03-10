package de.maxhenkel.advancedtools.mixins;

import de.maxhenkel.advancedtools.items.tools.AdvancedSword;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooBlock.class)
public abstract class BambooBlockMixin {

    @Inject(method = "getDestroyProgress", at = @At("HEAD"), cancellable = true)
    public void getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (player.getMainHandItem().getItem() instanceof AdvancedSword) {
            cir.setReturnValue(1F);
        }
    }

}
