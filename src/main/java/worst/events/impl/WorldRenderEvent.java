package worst.events.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.util.math.MatrixStack;
import worst.events.api.events.Event;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class WorldRenderEvent implements Event {
    MatrixStack stack;
    float partialTicks;
}
