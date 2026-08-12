package worst.modules.impl.render;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.math.MathHelper;
import worst.events.api.EventHandler;
import worst.events.impl.CameraEvent;
import worst.events.impl.FovEvent;
import worst.events.impl.HotBarScrollEvent;
import worst.events.impl.KeyEvent;
import worst.modules.impl.combat.aura.MathAngle;
import worst.modules.impl.player.FreeLook;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.BindSetting;
import worst.modules.module.setting.implement.BooleanSetting;
import worst.modules.module.setting.implement.SliderSettings;
import worst.util.Instance;
import worst.util.math.MathUtils;
import worst.util.string.PlayerInteractionHelper;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CameraSettings extends ModuleStructure {

    float fov = 110;
    float smoothFov = 30;
    float lastChangedFov = 30;

    BooleanSetting clipSetting = new BooleanSetting("Проход камеры", "Камера проходит сквозь блоки").setValue(true);
    SliderSettings distanceSetting = new SliderSettings("Дистанция камеры", "Настройка расстояния камеры")
            .setValue(3.0F).range(2.0F, 5.0F);
    BindSetting zoomSetting = new BindSetting("Зум", "Клавиша для увеличения камеры");

    public CameraSettings() {
        super("CameraSettings", "Camera Settings", ModuleCategory.RENDER);
        settings(clipSetting, distanceSetting, zoomSetting);
    }

    @EventHandler
    public void onKey(KeyEvent e) {
        if (e.isKeyDown(zoomSetting.getKey())) {
            fov = Math.min(lastChangedFov, mc.options.getFov().getValue() - 20);
        }
        if (e.isKeyReleased(zoomSetting.getKey(), true)) {
            lastChangedFov = fov;
            fov = mc.options.getFov().getValue();
        }
    }

    @EventHandler
    public void onHotBarScroll(HotBarScrollEvent e) {
        if (PlayerInteractionHelper.isKey(zoomSetting)) {
            fov = (int) MathHelper.clamp(fov - e.getVertical() * 10, 10, mc.options.getFov().getValue());
            e.cancel();
        }
    }

    @EventHandler
    public void onFov(FovEvent e) {
        e.setFov((int) MathHelper.clamp((smoothFov = MathUtils.interpolateSmooth(1.6, smoothFov, fov)) + 1, 10, mc.options.getFov().getValue()));
        e.cancel();
    }

    @EventHandler
    public void onCamera(CameraEvent e) {
        e.setCameraClip(clipSetting.isValue());
        e.setDistance(distanceSetting.getValue());
        FreeLook freeLook = Instance.get(FreeLook.class);
        if (!freeLook.isState() || !PlayerInteractionHelper.isKey(freeLook.freeLookSetting)) {
            e.setAngle(MathAngle.cameraAngle());
        }
        e.cancel();
    }
}