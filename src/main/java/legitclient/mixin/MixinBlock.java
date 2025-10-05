package legitclient.mixin;

import legitclient.LegitClient;
import legitclient.module.modules.Xray;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SideOnly(Side.CLIENT)
@Mixin({Block.class})
public abstract class MixinBlock {
    @Inject(
            method = {"shouldSideBeRendered"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable
    ) {
        if (LegitClient.moduleManager != null) {
            Xray xray = (Xray) LegitClient.moduleManager.modules.get(Xray.class);
            if (xray.isEnabled() && xray.mode.getValue() == 1 && xray.shouldRenderSide(Block.getIdFromBlock((Block) ((Object) this)))) {
                BlockPos block = new BlockPos(
                        blockPos.getX() - enumFacing.getDirectionVec().getX(),
                        blockPos.getY() - enumFacing.getDirectionVec().getY(),
                        blockPos.getZ() - enumFacing.getDirectionVec().getZ()
                );
                if (xray.checkBlock(block)) {
                    callbackInfoReturnable.setReturnValue(true);
                }
            }
        }
    }

    @Inject(
            method = {"getBlockLayer"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private void getBlockLayer(CallbackInfoReturnable<EnumWorldBlockLayer> callbackInfoReturnable) {
        if (LegitClient.moduleManager != null) {
            Xray xray = (Xray) LegitClient.moduleManager.modules.get(Xray.class);
            if (xray.isEnabled()) {
                int id = Block.getIdFromBlock((Block) ((Object) this));
                if (!xray.shouldRenderSide(id) || xray.mode.getValue() == 0 && !xray.isXrayBlock(id)) {
                    callbackInfoReturnable.setReturnValue(EnumWorldBlockLayer.TRANSLUCENT);
                }
            }
        }
    }
}
