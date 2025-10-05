package legitclient.module.modules;

import legitclient.LegitClient;
import legitclient.event.EventTarget;
import legitclient.event.types.Priority;
import legitclient.events.LivingUpdateEvent;
import legitclient.events.StrafeEvent;
import legitclient.mixin.IAccessorEntity;
import legitclient.module.Module;
import legitclient.util.MoveUtil;
import legitclient.property.properties.FloatProperty;
import legitclient.property.properties.PercentProperty;
import net.minecraft.client.Minecraft;

public class Speed extends Module {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public final FloatProperty multiplier = new FloatProperty("multiplier", 1.0F, 0.0F, 10.0F);
    public final FloatProperty friction = new FloatProperty("friction", 1.0F, 0.0F, 10.0F);
    public final PercentProperty strafe = new PercentProperty("strafe", 0);

    private boolean canBoost() {
        Scaffold scaffold = (Scaffold) LegitClient.moduleManager.modules.get(Scaffold.class);
        return !scaffold.isEnabled() && MoveUtil.isForwardPressed()
                && mc.thePlayer.getFoodStats().getFoodLevel() > 6
                && !mc.thePlayer.isSneaking()
                && !mc.thePlayer.isInWater()
                && !mc.thePlayer.isInLava()
                && !((IAccessorEntity) mc.thePlayer).getIsInWeb();
    }

    public Speed() {
        super("Speed", false);
    }

    @EventTarget(Priority.LOW)
    public void onStrafe(StrafeEvent event) {
        if (this.isEnabled() && this.canBoost()) {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.motionY = 0.42F;
                MoveUtil.setSpeed(
                        MoveUtil.getJumpMotion() * (double) this.multiplier.getValue().floatValue(),
                        MoveUtil.getMoveYaw()
                );
            } else {
                if (this.friction.getValue() != 1.0F) {
                    event.setFriction(event.getFriction() * this.friction.getValue());
                }
                if (this.strafe.getValue() > 0) {
                    double speed = MoveUtil.getSpeed();
                    MoveUtil.setSpeed(speed * (double) ((float) (100 - this.strafe.getValue()) / 100.0F), MoveUtil.getDirectionYaw());
                    MoveUtil.addSpeed(
                            speed * (double) ((float) this.strafe.getValue().intValue() / 100.0F), MoveUtil.getMoveYaw()
                    );
                    MoveUtil.setSpeed(speed);
                }
            }
        }
    }

    @EventTarget(Priority.LOW)
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (this.isEnabled() && this.canBoost()) {
            mc.thePlayer.movementInput.jump = false;
        }
    }
}
