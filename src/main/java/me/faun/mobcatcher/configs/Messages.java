package me.faun.mobcatcher.configs;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import ch.jalu.configme.properties.StringProperty;

public class Messages implements SettingsHolder {
    @Override
    public void registerComments(CommentsConfiguration config) {
        config.setComment("", "\n",
                "█▄░▄█ ▄▀▄ █▀▄     ▄▀ ▄▀▄ ▀█▀ ▄▀ █░░ █▀▀ █▀▀▄",
                "█░█░█ █░█ █▀█     █░ █▀█ ░█░ █░ █▀▄ █▀▀ █▐█▀",
                "▀░░░▀ ░▀░ ▀▀░     ░▀ ▀░▀ ░▀░ ░▀ ▀░▀ ▀▀▀ ▀░▀▀ ",
                "\n",
                "This is MobCatcher's messages.yml. If you need some help",
                "please take a look at the currently non-existent wiki.");
    }

    public static final Property<String> PREFIX = new StringProperty("prefix", "<g:#BD4F6C:#BD4F6C>CatchMob ");

    public static final Property<String> PLAYER_NOT_ONLINE = new StringProperty("player-not-online", "%prefix% &fThat player is not online.");

    public static final Property<String> RELOAD_SUCCESS = new StringProperty("reload-success", "%prefix% &aSuccessfully reloaded the plugin in %time% ms.");

    public static final Property<String> NO_PERMISSION = new StringProperty("no-permission", "%prefix% &cYou do not have permission to run this command!");

    public static final Property<String> UNKNOWN_COMMAND = new StringProperty("unknown-command", "%prefix% &cUnknown command. Please do &a/mobcatcher help &cfor available commands.");

    public static final Property<String> ITEM_DOES_NOT_EXIST = new StringProperty("item-does-not-exist", "%prefix% &cThat item does not exist.");

    public static final Property<String> INVALID_ARGUMENT = new StringProperty("invalid-argument", "%prefix% &cInvalid arguments. Usage: %usage%.");

    public static final Property<String> HELP_HEADER = new StringProperty("help-header", "<g:#ec9f05:#ff4e00>=========== %prefix% commands list<g:#ff4e00:#ec9f05> ==========");
    public static final Property<String> HELP_COMMAND = new StringProperty("help-command", "[<g:#4884ee:#06bcfb>%usage%](hover: Click to suggest the &a%command%&f command.|suggest: /givepet %command%) &f%description%");
    public static final Property<String> HELP_FOOTER = new StringProperty("help-footer", "<g:#ec9f05:#ff4e00>====================<g:#ff4e00:#ec9f05>====================");

}
