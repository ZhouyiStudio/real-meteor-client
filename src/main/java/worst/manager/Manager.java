package worst.manager;

import lombok.Getter;
import worst.client.draggables.HudManager;
import worst.command.CommandManager;
import worst.events.api.EventManager;
import worst.modules.impl.combat.aura.attack.StrikerConstructor;
import worst.modules.module.ModuleRepository;
import worst.screens.clickgui.ClickGui;
import worst.util.config.ConfigSystem;
import worst.util.config.impl.bind.BindConfig;
import worst.util.config.impl.blockesp.BlockESPConfig;
import worst.util.config.impl.drag.DragConfig;
import worst.util.config.impl.friend.FriendConfig;
import worst.util.config.impl.prefix.PrefixConfig;
import worst.util.config.impl.proxy.ProxyConfig;
import worst.util.config.impl.staff.StaffConfig;
import worst.util.entity.fakeplayer.FakePlayerManager;
import worst.util.modules.ModuleProvider;
import worst.util.modules.ModuleSwitcher;
import worst.util.render.font.FontInitializer;
import worst.util.render.shader.RenderCore;
import worst.util.render.shader.Scissor;
import worst.util.repository.macro.MacroRepository;
import worst.util.repository.way.WayRepository;
import worst.util.tps.TPSCalculate;

/**
 * © 2026 Copyright Worst Client
 * All Rights Reserved ®
 */

@Getter
public class Manager {
    public StrikerConstructor attackPerpetrator = new StrikerConstructor();
    private EventManager eventManager;
    private RenderCore renderCore;
    private Scissor scissor;
    private ModuleProvider moduleProvider;
    private ModuleRepository moduleRepository;
    private ModuleSwitcher moduleSwitcher;
    private ClickGui clickgui;
    private ConfigSystem configSystem;
    private CommandManager commandManager;
    private TPSCalculate tpsCalculate;
    private HudManager hudManager = new HudManager();

    public void init() {
        MacroRepository.getInstance().init();
        WayRepository.getInstance().init();
        BlockESPConfig.getInstance().load();
        FriendConfig.getInstance().load();
        PrefixConfig.getInstance().load();
        StaffConfig.getInstance().load();
        ProxyConfig.getInstance().load();
        DragConfig.getInstance().load();
        BindConfig.getInstance();

        FontInitializer.register();

        tpsCalculate = new TPSCalculate();

        clickgui = new ClickGui();
        eventManager = new EventManager();
        renderCore = new RenderCore();
        scissor = new Scissor();
        hudManager = new HudManager();
        hudManager.initElements();
        moduleRepository = new ModuleRepository();
        moduleRepository.setup();
        moduleProvider = new ModuleProvider(moduleRepository.modules());
        moduleSwitcher = new ModuleSwitcher(moduleRepository.modules(), eventManager);
        configSystem = new ConfigSystem();
        configSystem.init();
        commandManager = new CommandManager();
        commandManager.init();

        FakePlayerManager.getInstance().init();
    }
}