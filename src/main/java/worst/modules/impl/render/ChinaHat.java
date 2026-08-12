package worst.modules.impl.render;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.ColorSetting;
import worst.util.ColorUtil;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChinaHat extends ModuleStructure {

    private static ChinaHat instance;

    public static ChinaHat getInstance() {
        return instance;
    }

    public final ColorSetting color1 = new ColorSetting("Color 1", "First gradient color")
            .value(ColorUtil.getColor(96, 96, 97, 255));

    public final ColorSetting color2 = new ColorSetting("Color 2", "Second gradient color")
            .value(ColorUtil.getColor(162, 162, 163, 255));

    public ChinaHat() {
        super("ChinaHat", "China Hat", ModuleCategory.RENDER);
        instance = this;
        settings(color1, color2);
    }
}