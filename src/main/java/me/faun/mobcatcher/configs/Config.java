package me.faun.mobcatcher.configs;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.*;
import ch.jalu.configme.properties.types.BeanPropertyType;

public class Config extends PropertyInitializer implements SettingsHolder{
    @Override
    public void registerComments(CommentsConfiguration config) {
        config.setComment("", "\n",
                "█▄░▄█ ▄▀▄ █▀▄     ▄▀ ▄▀▄ ▀█▀ ▄▀ █░░ █▀▀ █▀▀▄",
                "█░█░█ █░█ █▀█     █░ █▀█ ░█░ █░ █▀▄ █▀▀ █▐█▀",
                "▀░░░▀ ░▀░ ▀▀░     ░▀ ▀░▀ ░▀░ ░▀ ▀░▀ ▀▀▀ ▀░▀▀ ",
                "\n" ,
                " This is GivePet's messages.yml, if you wish to know how to" ,
                " format the messages, please refer to this site: https://mf.mattstudios.me/message/mf-msg/syntax",
                "\n");
    }

    public static final MapProperty<MobCatcherItemBean> CATCHERS = PropertyInitializer.mapProperty(BeanPropertyType.of(MobCatcherItemBean.class))
            .path("mob-catchers")
            .defaultEntry("default", new MobCatcherItemBean())
            .build();
}
