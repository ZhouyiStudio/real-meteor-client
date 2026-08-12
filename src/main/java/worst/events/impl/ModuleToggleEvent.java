package worst.events.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import worst.events.api.events.Event;
import worst.modules.module.ModuleStructure;

@Getter
@AllArgsConstructor
public class ModuleToggleEvent implements Event {
    private final ModuleStructure module;
    private final boolean enabled;
}