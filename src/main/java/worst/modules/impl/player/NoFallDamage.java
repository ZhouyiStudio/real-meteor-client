package worst.modules.impl.player;

import antidaunleak.api.annotation.Native;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import worst.events.api.EventHandler;
import worst.events.impl.PacketEvent;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.SelectSetting;
import worst.util.move.MoveUtil;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoFallDamage extends ModuleStructure {

    SelectSetting mode = new SelectSetting("Режим", "Выберите тип")
            .value("SpookyTime")
            .selected("SpookyTime");

    public NoFallDamage() {
        super("NoFallDamage", "No Fall Damage", ModuleCategory.PLAYER);
        settings(mode);
    }

    @EventHandler
    @Native(type = Native.Type.VMProtectBeginUltra)
    public void onPacket(PacketEvent e) {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.fallDistance > 0 && MoveUtil.getDistanceToGround() > 4) {
            mc.player.setVelocity(0, 0, 0);
        }
    }
}