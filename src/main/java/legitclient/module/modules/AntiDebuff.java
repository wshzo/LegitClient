package legitclient.module.modules;

import legitclient.module.Module;
import legitclient.property.properties.BooleanProperty;

public class AntiDebuff extends Module {
    public final BooleanProperty blindness = new BooleanProperty("blindness", true);
    public final BooleanProperty nausea = new BooleanProperty("nausea", true);

    public AntiDebuff() {
        super("AntiDebuff", false);
    }
}
