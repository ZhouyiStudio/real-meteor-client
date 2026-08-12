package worst.modules.impl.misc;

import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.util.Instance;

public class NoAnnouncer extends ModuleStructure {

    public static NoAnnouncer getInstance() {
        return Instance.get(NoAnnouncer.class);
    }

    public NoAnnouncer() {
        super("NoAnnouncer", "Отключает диктора", ModuleCategory.MISC);
    }
}
