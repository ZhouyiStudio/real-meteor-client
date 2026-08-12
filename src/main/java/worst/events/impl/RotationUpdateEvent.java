package worst.events.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import worst.events.api.events.Event;

@Getter
@AllArgsConstructor
public class RotationUpdateEvent implements Event {
    byte type;
}
