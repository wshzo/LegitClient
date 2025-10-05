package legitclient.events;

import legitclient.event.events.Event;

public class KeyEvent implements Event {
    private final int keyCode;

    public KeyEvent(int key) {
        this.keyCode = key;
    }

    public int getKey() {
        return this.keyCode;
    }
}
