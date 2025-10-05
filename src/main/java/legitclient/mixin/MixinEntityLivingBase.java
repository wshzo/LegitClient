package legitclient.mixin;

import legitclient.LegitClient;
import legitclient.event.EventManager;
import legitclient.events.StrafeEvent;
import legitclient.management.RotationState;
import legitclient.module.modules.Jesus;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@SideOnly(Side.CLIENT)
@Mixin({EntityLivingBase.class})
public abstract class MixinEntityLivingBase extends MixinEntity {
    @ModifyVariable(
            method = {"jump"},
            at = @At("STORE"),
            ordinal = 0
    )
    private float jump(float float1) {
        return (Entity) ((Object) this) instanceof EntityPlayerSP && RotationState.isActived()
                ? RotationState.getSmoothedYaw() * (float) (Math.PI / 180.0)
                : float1;
    }

    @Redirect(
            method = {"moveEntityWithHeading"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityLivingBase;moveFlying(FFF)V"
            )
    )
    private void moveEntityWithHeading(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
        if ((Entity) ((Object) this) instanceof EntityPlayerSP) {
            StrafeEvent event = new StrafeEvent(float2, float3, float4);
            EventManager.call(event);
            float2 = event.getStrafe();
            float3 = event.getForward();
            float4 = event.getFriction();
            boolean actived = RotationState.isActived();
            float yaw = this.rotationYaw;
            if (actived) {
                this.rotationYaw = RotationState.getSmoothedYaw();
            }
            entityLivingBase.moveFlying(float2, float3, float4);
            if (actived) {
                this.rotationYaw = yaw;
            }
        } else {
            entityLivingBase.moveFlying(float2, float3, float4);
        }
    }

    @ModifyVariable(
            method = {"moveEntityWithHeading"},
            name = {"f3"},
            at = @At("STORE")
    )
    private float moveEntityWithHeading(float float1) {
        if ((EntityLivingBase) ((Object) this) instanceof EntityPlayerSP && float1 == (float) EnchantmentHelper.getDepthStriderModifier((EntityLivingBase) ((Object) this))) {
            if (LegitClient.moduleManager == null) {
                return float1;
            }
            Jesus jesus = (Jesus) LegitClient.moduleManager.modules.get(Jesus.class);
            if (jesus.isEnabled() && (!jesus.groundOnly.getValue() || this.onGround)) {
                return Math.max(float1, jesus.speed.getValue());
            }
        }
        return float1;
    }
}
