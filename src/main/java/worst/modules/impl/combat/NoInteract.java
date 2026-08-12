package worst.modules.impl.combat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.util.Instance;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoInteract extends ModuleStructure {
    public static NoInteract getInstance() {
        return Instance.get(NoInteract.class);
    }

    public NoInteract() {
        super("NoInteract", "No Interact", ModuleCategory.COMBAT);
    }
}
