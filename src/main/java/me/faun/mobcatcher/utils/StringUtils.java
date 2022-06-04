package me.faun.mobcatcher.utils;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.properties.Property;
import me.faun.mobcatcher.MobCatcher;
import me.faun.mobcatcher.configs.ConfigManager;
import me.faun.mobcatcher.configs.Configs;
import me.faun.mobcatcher.configs.Messages;
import me.mattstudios.msg.adventure.AdventureMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StringUtils {
    /**
     *  Returns a Component of the parsed message. This will also replace all instances of %prefix%.
     *
     *  @param text     The text to parse.
     *  @return         The result of the parsed text.
     */
    public static @NotNull Component messageParse(@NotNull String text) {
        final AdventureMessage message = AdventureMessage.create();

        return message.parse(text.replace("%prefix%", getStringFromMessages(Messages.PREFIX)));
    }

    /**
     * Returns a String version of a Component that supports mf-msg's hex format.
     *
     * @param text      The Component to convert into String.
     * @return          The converted Component
     */
    public static @NotNull String componentToString(@NotNull Component text) {
        StringBuilder sb = new StringBuilder();
        if (text.children().isEmpty()) {
            sb.append(((TextComponent) text.compact()).content());
        }

        for (Component component : text.children()) {
            if (component.compact().style().color() != null) {
                sb.append("&").append((component.compact()).style().color().asHexString())
                        .append(((TextComponent) component.compact()).content());
            } else {
                sb.append(((TextComponent) component.compact()).content());
            }
        }
        return sb.toString();
    }

    /**
     *  This will send a Component to a player
     *
     *  @param player   The player that will receive the message.
     *  @param message  The message that will be sent to the player.
     */
    public static void sendComponent(@NotNull Player player, @NotNull Property<String> message) {
        player.sendMessage(messageParse(getStringFromMessages(message)));
    }

    /**
     *  This will send a Component to a CommandSender
     *s
     *  @param sender   The CommandSender that will receive the message.
     *  @param message  The message that will be sent to the command sender.
     */
    public static void sendComponent(@NotNull CommandSender sender, Property<String> message) {
        sender.sendMessage(messageParse(getStringFromMessages(message)));
    }

    /**
     *  This will send a Component to a player
     *
     *  @param player   The player that will receive the message.
     *  @param messages  The message that will be sent to the player.
     */
    public static void sendComponent(Player player, String @NotNull ... messages) {
        for (String message : messages) {
            player.sendMessage(messageParse(message));
        }
    }

    /**
     *  This will send a Component to a player
     *
     *  @param sender   The CommandSender that will receive the message.
     *  @param messages  The message that will be sent to the command sender.
     */
    public static void sendComponent(CommandSender sender, String @NotNull ... messages) {
        for (String message : messages) {
            sender.sendMessage(messageParse(message));
        }
    }

    /**
     * This will return the property's value as a string.
     *
     * @param property the property that needs to be returned.
     * @return the property's value as a string.
     */
    public static String getStringFromMessages(Property<String> property) {
        ConfigManager configManager = new ConfigManager(MobCatcher.getPlugin(MobCatcher.class));
        SettingsManager settingsManager = configManager.getConfig(Configs.MESSAGES);
        return settingsManager.getProperty(property);
    }
}
