package me.faun.mobcatcher.configs;

import org.apache.commons.lang.WordUtils;

public enum Configs {
    CONFIG,
    MESSAGES;

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }
}
