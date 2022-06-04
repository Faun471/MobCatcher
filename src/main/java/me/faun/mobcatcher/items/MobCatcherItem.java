package me.faun.mobcatcher.items;

import me.faun.mobcatcher.MobCatcher;
import me.faun.mobcatcher.configs.MobCatcherItemBean;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MobCatcherItem implements ConfigurationSerializable {
    private final MobCatcher plugin = MobCatcher.getPlugin(MobCatcher.class);

    private final Material material;
    private final String displayName;
    private final List<EntityType> whitelistedEntities;

    private int uses;
    private EntityType entityType;
    private String nbtEntity;

    public MobCatcherItem(Map<String, Object> map) {
        this.material = (Material) map.get("material");
        this.displayName = (String) map.get("displayName");
        this.whitelistedEntities = (List<EntityType>) map.get("mobs");
        this.uses = (Integer) map.get("uses");
        this.entityType = (EntityType) map.get("entityType");
        this.nbtEntity = (String) map.get("nbtEntity");
    }

    public MobCatcherItem(MobCatcherItemBean bean) {
        this.material = bean.getMaterial();
        this.displayName = bean.getDisplayName();
        this.uses = bean.getUses();
        this.whitelistedEntities = bean.getWhitelistedEntities();
        this.entityType = EntityType.UNKNOWN;
        this.nbtEntity = "";
    }

    public MobCatcherItem(Material material, String displayName,List<EntityType> whitelistedEntities, int uses, EntityType entityType, String nbtEntity) {
        this.material = material;
        this.displayName = displayName;
        this.whitelistedEntities = whitelistedEntities;
        this.uses = uses;
        this.entityType = entityType;
        this.nbtEntity = nbtEntity;
    }

    public MobCatcherItem use() {
        if (this.uses > 0) this.uses--;
        this.entityType = EntityType.UNKNOWN;
        this.nbtEntity = "";
        return this;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemBuilder(material).name(displayName).build();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(plugin.getKey(), plugin.getBeanDataType(), this);
        item.setItemMeta(meta);
        return item;
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

    public EntityType getEntityType() {
        return entityType;
    }

    public String getNbtEntity() {
        return nbtEntity;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void setNbtEntity(String nbtEntity) {
        this.nbtEntity = nbtEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MobCatcherItem) obj;
        return Objects.equals(this.material, that.material) &&
                Objects.equals(this.displayName, that.displayName) &&
                this.uses == that.uses &&
                Objects.equals(this.whitelistedEntities, that.whitelistedEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, displayName, uses, whitelistedEntities);
    }

    @Override
    public String toString() {
        return "MobCatcherItem[" +
                "material=" + material + ", " +
                "displayName=" + displayName + ", " +
                "uses=" + uses + ", " +
                "whitelistedEntities=" + whitelistedEntities + ", " +
                "entity=" + entityType + ", " +
                "nbtEntity=" + nbtEntity + ']';
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return new HashMap<>() {{
            put("material", material);
            put("displayName", displayName);
            put("mobs", whitelistedEntities);
            put("uses", uses);
            put("entityType", entityType);
            put("nbtEntity", nbtEntity);
        }};
    }

    public static MobCatcherItem deserialize(Map<String, Object> map) {
        return new MobCatcherItem(map);
    }

    public static MobCatcherItem valueOf(Map<String, Object> map) {
        return new MobCatcherItem(map);
    }
}
