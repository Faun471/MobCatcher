package me.faun.mobcatcher.commands;

public record Command (
        String name,
        String[] alias,
        String description,
        String permission,
        String usage
) {}
