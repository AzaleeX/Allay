package org.allaymc.exampleplugin;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.server.Server;
import org.allaymc.api.world.generator.WorldGenerator;

/**
 * Allay Project 2024/2/8
 *
 * @author daoge_cmd
 */
@Slf4j
@Getter
public final class ExamplePlugin extends Plugin {

    public static ExamplePlugin INSTANCE;

    private final EventListener eventListener = new EventListener();

    public ExamplePlugin() {
        INSTANCE = this;
    }

    @Override
    public void onLoad() {
        log.info("ExamplePlugin loaded!");
        Registries.WORLD_GENERATOR_FACTORIES.register("RANDOM_BLOCK", preset -> WorldGenerator.builder().name("RANDOM_BLOCK").noisers(new RandomBlockNoiser()).build());
    }

    @Override
    public void onEnable() {
        log.info("ExamplePlugin enabled!");
        log.info(getPluginI18n().tr("ep:example_plugin_i18n_test"));
        var server = Server.getInstance();
        server.getEventBus().registerListener(eventListener);
        Registries.COMMANDS.register(new ExampleCommand());
    }

    @Override
    public void onDisable() {
        log.info("ExamplePlugin disabled!");
        var server = Server.getInstance();
        server.getEventBus().unregisterListener(eventListener);
        Registries.COMMANDS.unregister("example-cmd");
    }
}
