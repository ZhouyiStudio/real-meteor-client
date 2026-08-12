package worst.events.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import worst.events.api.events.callables.EventCancellable;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandledScreenScrollEvent extends EventCancellable {
    @Nullable Slot hoveredSlot;
    double vertical;
}
