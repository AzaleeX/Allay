package org.allaymc.api.plugin;

import java.nio.file.Path;

/**
 * @author daoge_cmd
 */
public interface PluginLoader {

    Path getPluginPath();

    PluginDescriptor loadDescriptor();

    PluginContainer loadPlugin();

    interface PluginLoaderFactory {

        boolean canLoad(Path pluginPath);

        PluginLoader create(Path pluginPath);
    }
}
