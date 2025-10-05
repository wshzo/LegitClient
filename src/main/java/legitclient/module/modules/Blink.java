package legitclient.module.modules;

import legitclient.LegitClient;
import legitclient.enums.BlinkModules;
import legitclient.event.EventTarget;
import legitclient.event.types.EventType;
import legitclient.event.types.Priority;
import legitclient.events.LoadWorldEvent;
import legitclient.events.TickEvent;
import legitclient.module.Module;
import legitclient.property.properties.IntProperty;
import legitclient.property.properties.ModeProperty;

public class Blink extends Module {
    public final ModeProperty mode = new ModeProperty("mode", 0, new String[]{"DEFAULT", "PULSE"});
    public final IntProperty ticks = new IntProperty("ticks", 20, 0, 1200);

    public Blink() {
        super("Blink", false);
    }

    @EventTarget(Priority.LOWEST)
    public void onTick(TickEvent event) {
        if (this.isEnabled() && event.getType() == EventType.POST) {
            if (!LegitClient.blinkManager.getBlinkingModule().equals(BlinkModules.BLINK)) {
                this.setEnabled(false);
            } else {
                if (this.ticks.getValue() > 0 && LegitClient.blinkManager.countMovement() > (long) this.ticks.getValue()) {
                    switch (this.mode.getValue()) {
                        case 0:
                            this.setEnabled(false);
                            break;
                        case 1:
                            LegitClient.blinkManager.setBlinkState(false, BlinkModules.BLINK);
                            LegitClient.blinkManager.setBlinkState(true, BlinkModules.BLINK);
                    }
                }
            }
        }
    }

    @EventTarget
    public void onWorldLoad(LoadWorldEvent event) {
        this.setEnabled(false);
    }

    @Override
    public void onEnabled() {
        LegitClient.blinkManager.setBlinkState(false, LegitClient.blinkManager.getBlinkingModule());
        LegitClient.blinkManager.setBlinkState(true, BlinkModules.BLINK);
    }

    @Override
    public void onDisabled() {
        LegitClient.blinkManager.setBlinkState(false, BlinkModules.BLINK);
    }
}
