package me.faun.mobcatcher.configs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MobCatcherItemBean {
    private Material material;
    private String displayName;
    private int uses;
    private List<EntityType> whitelistedEntities;

    public MobCatcherItemBean() {
        this.material = Material.STICK;
        this.displayName = "Mob Catching Stick!";
        this.uses =  1;
        this.whitelistedEntities = Collections.singletonList(EntityType.WOLF);
    }

    public MobCatcherItemBean(Material material, String name, int uses, List<EntityType> mobs) {
        this.material = material;
        this.displayName = name;
        this.uses = uses;
        this.whitelistedEntities = mobs;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getUses() {
        return uses;
    }

    public List<EntityType> getWhitelistedEntities() {
        return whitelistedEntities;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    public void setWhitelistedEntities(List<String> whitelistedEntities) {
        this.whitelistedEntities = whitelistedEntities.stream().toList().contains("*") ? Arrays.stream(EntityType.values()).toList() : whitelistedEntities.stream()
                .filter(s -> Arrays.stream(EntityType.values()).map(EntityType::toString).toList().contains(s))
                .map(EntityType::valueOf)
                .toList();
    }
}
