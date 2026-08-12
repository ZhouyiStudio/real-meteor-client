package worst.modules.impl.combat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import worst.events.api.EventHandler;
import worst.events.impl.InteractEntityEvent;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.util.repository.friend.FriendUtils;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NoFriendDamage extends ModuleStructure {

    public NoFriendDamage() {
        super("NoFriendDamage", "No Friend Damage", ModuleCategory.COMBAT);
    }

    @EventHandler
    public void onAttack(InteractEntityEvent e) {
        e.setCancelled(FriendUtils.isFriend(e.getEntity()));
    }
}

