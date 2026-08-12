package worst.modules.impl.render;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import worst.events.api.EventHandler;
import worst.events.api.types.Priority;
import worst.events.impl.CameraEvent;
import worst.events.impl.CameraPositionEvent;
import worst.modules.impl.combat.aura.Angle;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.SliderSettings;
import worst.util.Instance;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmoothCamera extends ModuleStructure {

    public static SmoothCamera getInstance() {
        return Instance.get(SmoothCamera.class);
    }

    final SliderSettings speed = new SliderSettings("Smooth", "Скорость следования камеры")
            .range(0.01f, 0.3f).setValue(0.08f);

    float smoothYaw = 0;
    float smoothPitch = 0;
    Vec3d smoothPos = null;
    boolean initialized = false;

    public SmoothCamera() {
        super("SmoothCamera", "Smooth Camera", ModuleCategory.RENDER);
        settings(speed);
    }

    @Override
    public void activate() {
        initialized = false;
        smoothPos = null;
    }

    @Override
    public void deactivate() {
        initialized = false;
        smoothPos = null;
    }

    @EventHandler(Priority.LOW)
    public void onCamera(CameraEvent e) {
        Angle target = e.getAngle();

        if (!initialized) {
            smoothYaw = target.getYaw();
            smoothPitch = target.getPitch();
            initialized = true;
            return;
        }

        float dyaw = MathHelper.wrapDegrees(target.getYaw() - smoothYaw);
        smoothYaw += dyaw * speed.getValue();
        smoothPitch += (target.getPitch() - smoothPitch) * speed.getValue();

        e.setAngle(new Angle(smoothYaw, smoothPitch));
        e.cancel();
    }

    @EventHandler(Priority.LOW)
    public void onCameraPos(CameraPositionEvent e) {
        Vec3d target = e.getPos();

        if (smoothPos == null) {
            smoothPos = target;
            return;
        }

        smoothPos = new Vec3d(
                smoothPos.x + (target.x - smoothPos.x) * speed.getValue(),
                smoothPos.y + (target.y - smoothPos.y) * speed.getValue(),
                smoothPos.z + (target.z - smoothPos.z) * speed.getValue()
        );
        e.setPos(smoothPos);
    }
}
