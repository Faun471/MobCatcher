package me.faun.mobcatcher;

import com.jeff_media.morepersistentdatatypes.datatypes.serializable.ConfigurationSerializableDataType;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import me.faun.mobcatcher.commands.Command;
import me.faun.mobcatcher.commands.CommandManager;
import me.faun.mobcatcher.commands.MobCatcherCommand;
import me.faun.mobcatcher.configs.*;
import me.faun.mobcatcher.items.MobCatcherItem;
import me.faun.mobcatcher.listeners.PlayerInteractEntityListener;
import me.faun.mobcatcher.listeners.PlayerInteractListener;
import me.faun.mobcatcher.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.stream.Collectors;

public final class MobCatcher extends JavaPlugin {
    private ConfigManager configManager;
    private final NamespacedKey key = new NamespacedKey(this, "mob-catcher");
    private final PersistentDataType<byte[], MobCatcherItem> beanDataType = new ConfigurationSerializableDataType<>(MobCatcherItem.class);

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.reloadConfigs();

        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntityListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);

        initMessages(BukkitCommandManager.create(this));
    }

    private void initMessages(BukkitCommandManager<CommandSender> bukkitCommandManager) {
        bukkitCommandManager.registerSuggestion(SuggestionKey.of("#help"), (sender, context) -> CommandManager.getCommands().values().stream()
                .filter(command -> CommandManager.hasPermission(sender, command))
                .map((Command::name))
                .collect(Collectors.toList()));

        bukkitCommandManager.registerSuggestion(SuggestionKey.of("catchers"), (sender, context) -> new ArrayList<>(configManager.getConfig(Configs.CONFIG).getProperty(Config.CATCHERS).keySet()));

        bukkitCommandManager.registerMessage(MessageKey.UNKNOWN_COMMAND, (sender, context) -> StringUtils.sendComponent(sender, Messages.UNKNOWN_COMMAND));
        bukkitCommandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> StringUtils.sendComponent(sender, Messages.NO_PERMISSION));
        bukkitCommandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, context) -> {
            if (context.getArgumentType() == Player.class) {
                StringUtils.sendComponent(sender, Messages.PLAYER_NOT_ONLINE);
            }
        });

        bukkitCommandManager.registerCommand(new MobCatcherCommand(configManager));
    }

    public NamespacedKey getKey() {
        return key;
    }


    public PersistentDataType<byte[], MobCatcherItem> getBeanDataType() {
        return beanDataType;
    }
}
