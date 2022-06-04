package me.faun.mobcatcher.commands;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.*;
import dev.triumphteam.cmd.core.annotation.Command;
import me.faun.mobcatcher.configs.*;
import me.faun.mobcatcher.items.MobCatcherItem;
import me.faun.mobcatcher.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

@Command(value = "mobcatcher", alias = "mc")
public class MobCatcherCommand extends BaseCommand {
    private final ConfigManager configManager;

    public MobCatcherCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @SubCommand("reload")
    @Description("Reloads the plugin's config files.")
    @Permission("mobcatcher.reload")
    @Usage("/mobcatcher reload")
    public void reload(CommandSender sender) {
        long time = System.currentTimeMillis();
        configManager.reloadConfigs();
        StringUtils.sendComponent(sender, StringUtils.getStringFromMessages(Messages.RELOAD_SUCCESS)
                .replace("%time%", String.valueOf(System.currentTimeMillis() - time)));
    }


    @SubCommand("help")
    @Description("The plugin's help command.")
    @Usage("/mobcatcher help <command>")
    public void help(CommandSender sender, @Suggestion("#help") @Optional String arg) {
        HashMap<String, me.faun.mobcatcher.commands.Command> commands = CommandManager.getCommands();

        if (!commands.containsKey(arg)) {
            StringUtils.sendComponent(sender, StringUtils.getStringFromMessages(Messages.HELP_HEADER));
            commands.values().stream().filter((command -> CommandManager.hasPermission(sender, command)))
                    .forEach((command) -> StringUtils.sendComponent(sender, StringUtils.getStringFromMessages(Messages.HELP_COMMAND)
                            .replace("%command%", command.name())
                            .replace("%description%", command.description())
                            .replace("%usage%", command.usage())));
            StringUtils.sendComponent(sender, StringUtils.getStringFromMessages(Messages.HELP_FOOTER));
            return;
        }

        me.faun.mobcatcher.commands.Command command = commands.get(arg);
        if (CommandManager.hasPermission(sender, command)) {
            StringUtils.sendComponent(sender, StringUtils.getStringFromMessages(Messages.HELP_COMMAND)
                    .replace("%command%", command.name())
                    .replace("%description%", command.description())
                    .replace("%usage%", command.usage()));
        }
    }

    @SubCommand("give")
    @Description("Gives the player a mob catching item.")
    @Permission("mobcatcher.give")
    @Usage("/mobcatcher give [player] [item]")
    public void give(CommandSender sender, Player player, @Suggestion("catchers") String item) {
        PlayerInventory inventory = player.getInventory();
        if (inventory.firstEmpty() == -1) {
            StringUtils.sendComponent(sender, ChatColor.RED + player.getName() + "'s inventory is full.");
            return;
        }

        Map<String, MobCatcherItemBean> catchers = configManager.getConfig(Configs.CONFIG).getProperty(Config.CATCHERS);
        if (!catchers.containsKey(item)) {
            StringUtils.sendComponent(sender, Messages.ITEM_DOES_NOT_EXIST);
            return;
        }

        inventory.addItem(new MobCatcherItem(catchers.get(item)).toItemStack());
    }
}
