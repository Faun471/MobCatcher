package me.faun.mobcatcher.commands;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotation.Description;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.faun.mobcatcher.MobCatcher;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CommandManager {
    private static final HashMap<String, Command> commands = loadCommands();

    private static @NotNull HashMap<String, Command> loadCommands() {
        HashMap<String, Command> commands = new HashMap<>();
        for (Method method : MobCatcher.class.getDeclaredMethods()) {
            if (method.getAnnotations().length == 0) {
                break;
            }

            String name = method.getName();
            String[] alias = new String[]{};
            String commandDescription = "";
            String commandPermission = "";
            String commandUsage = "/" + name;

            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof SubCommand command) {
                    name = command.value();
                    alias = command.alias();
                }

                if (annotation instanceof Description description) {
                    commandDescription = description.value();
                }

                if (annotation instanceof Permission permission) {
                    commandPermission = permission.value();
                }

                if (annotation instanceof Usage usage) {
                    commandUsage = usage.value();
                }
            }

            commands.put(name, new Command(name, alias, commandDescription, commandPermission, commandUsage));
        }
        return commands;
    }

    public static HashMap<String, Command> getCommands() {
        return commands;
    }

    public static boolean hasPermission(CommandSender sender, @NotNull Command command) {
        return command.permission().isEmpty() || command.permission().isBlank() || sender.hasPermission(command.permission());
    }
}
