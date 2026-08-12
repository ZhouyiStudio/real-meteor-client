package worst.modules.impl.player;

import antidaunleak.api.annotation.Native;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import worst.events.api.EventHandler;
import worst.events.impl.TickEvent;
import worst.modules.module.ModuleStructure;
import worst.modules.module.category.ModuleCategory;
import worst.modules.module.setting.implement.MultiSelectSetting;
import worst.modules.module.setting.implement.SelectSetting;
import worst.modules.module.setting.implement.SliderSettings;
import worst.util.inventory.InventoryUtils;
import worst.util.timer.StopWatch;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChestStealer extends ModuleStructure {
    StopWatch stopWatch = new StopWatch();

    SelectSetting modeSetting = new SelectSetting("Тип", "Выбирает тип стила")
            .value("FunTime", "WhiteList", "Default").selected("FunTime");

    SliderSettings delaySetting = new SliderSettings("Задержка", "Задержка между кликами по слоту")
            .setValue(100).range(0, 1000)
            .visible(() -> modeSetting.isSelected("WhiteList") || modeSetting.isSelected("Default"));

    MultiSelectSetting itemSettings = new MultiSelectSetting("Предметы", "Выберите предметы, которые вор будет подбирать")
            .value("Player Head", "Totem Of Undying", "Elytra", "Netherite Sword",
                    "Netherite Helmet", "Netherite ChestPlate", "Netherite Leggings",
                    "Netherite Boots", "Netherite Ingot", "Netherite Scrap")
            .visible(() -> modeSetting.isSelected("WhiteList"));

    public ChestStealer() {
        super("ChestStealer", "Chest Stealer", ModuleCategory.PLAYER);
        settings(modeSetting, delaySetting, itemSettings);
    }

    @EventHandler
    @Native(type = Native.Type.VMProtectBeginUltra)
    public void onTick(TickEvent e) {
        if (mc.player == null) return;

        switch (modeSetting.getSelected()) {
            case "FunTime" -> handleFunTimeMode();
            case "WhiteList", "Default" -> handleDefaultMode();
        }
    }

    @Native(type = Native.Type.VMProtectBeginUltra)
    private void handleFunTimeMode() {
        if (mc.currentScreen instanceof GenericContainerScreen sh
                && sh.getTitle().getString().toLowerCase().contains("мистический")
                && !mc.player.getItemCooldownManager().isCoolingDown(Items.GUNPOWDER.getDefaultStack())) {

            sh.getScreenHandler().slots.stream()
                    .filter(s -> s.hasStack()
                            && !s.inventory.equals(mc.player.getInventory())
                            && stopWatch.every(150))
                    .forEach(s -> InventoryUtils.click(s.id, 0, SlotActionType.QUICK_MOVE));
        }
    }

    @Native(type = Native.Type.VMProtectBeginUltra)
    private void handleDefaultMode() {
        if (mc.player.currentScreenHandler instanceof GenericContainerScreenHandler sh) {
            sh.slots.forEach(s -> {
                if (s.hasStack()
                        && !s.inventory.equals(mc.player.getInventory())
                        && (modeSetting.isSelected("Default") || whiteList(s.getStack().getItem()))
                        && stopWatch.every(delaySetting.getValue())) {
                    InventoryUtils.click(s.id, 0, SlotActionType.QUICK_MOVE);
                }
            });
        }
    }

    @Native(type = Native.Type.VMProtectBeginMutation)
    private boolean whiteList(Item item) {
        return itemSettings.getSelected().toString().toLowerCase()
                .contains(item.toString().toLowerCase().replace("_", ""));
    }
}