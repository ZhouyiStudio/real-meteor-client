package worst.modules.impl.render;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import worst.events.api.EventHandler;
import worst.events.impl.TickEvent;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.SelectSetting;
import worst.util.Instance;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemPhysic extends ModuleStructure {
    public static ItemPhysic getInstance() {
        return Instance.get(ItemPhysic.class);
    }

    public SelectSetting mode = new SelectSetting("Физика", "").value("Обычная").selected("Обычная");

    public ItemPhysic() {
        super("ItemPhysic", "Item Physic", ModuleCategory.RENDER);
//        setup(mode);
    }

    @EventHandler
    public void onTick(TickEvent e) {
    }
}