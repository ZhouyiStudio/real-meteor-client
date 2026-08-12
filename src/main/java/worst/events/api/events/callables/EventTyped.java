package worst.events.api.events.callables;

import worst.events.api.events.Event;
import worst.events.api.events.Typed;

public abstract class EventTyped implements Event, Typed {

    private final byte type;

    protected EventTyped(byte eventType) {
        type = eventType;
    }

    @Override
    public byte getType() {
        return type;
    }

}