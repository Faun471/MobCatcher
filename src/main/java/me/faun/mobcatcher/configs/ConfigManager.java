package me.faun.mobcatcher.configs;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.properties.Property;
import me.faun.mobcatcher.MobCatcher;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class ConfigManager {
    private final MobCatcher plugin;
    private final HashMap<Configs, SettingsManager> configs = new HashMap<>();

    public ConfigManager(MobCatcher plugin) {
        this.plugin = plugin;
    }

    public void reloadConfigs() {
        configs.clear();
        loadConfig(Configs.MESSAGES);
        loadConfig(Configs.CONFIG);
    }

    public SettingsManager getConfig(Configs name) {
        if (!configs.containsKey(name)) {
            loadConfig(name);
        }

        return configs.get(name);
    }

    public void loadConfig(Configs name) {
        String fileName = name.name().toLowerCase() + ".yml";
        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        SettingsManager settingsManager = initSettings(name, file);
        configs.put(name, settingsManager);
    }

    public SettingsManager initSettings(Configs name, File config) {
        Class<? extends SettingsHolder> clazz = switch (name) {
            case MESSAGES -> Messages.class;
            case CONFIG -> Config.class;
        };

        Path configFile = Path.of(config.getPath());
        return SettingsManagerBuilder
                .withYamlFile(configFile)
                .configurationData(clazz)
                .useDefaultMigrationService()
                .create();
    }

    public Object getConfigValue(Configs config, Property<?> value) {
        SettingsManager settingsManager = getConfig(config);
        return settingsManager.getProperty(value);
    }
}

