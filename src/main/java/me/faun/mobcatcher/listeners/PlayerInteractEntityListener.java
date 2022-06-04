package me.faun.mobcatcher.listeners;

import de.tr7zw.nbtapi.NBTEntity;
import me.faun.mobcatcher.MobCatcher;
import me.faun.mobcatcher.items.MobCatcherItem;
import me.faun.mobcatcher.utils.ItemStackUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerInteractEntityListener implements Listener {
    private final MobCatcher plugin;

    public PlayerInteractEntityListener(MobCatcher plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getEquipment().getItemInMainHand();
        if (!item.hasItemMeta()) {
            System.out.println("first return");
            return;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        if (!pdc.has(plugin.getKey(), plugin.getBeanDataType())) {
            return;
        }

        MobCatcherItem mobCatcherItem = pdc.get(plugin.getKey(), plugin.getBeanDataType());
        assert mobCatcherItem != null;
        if (mobCatcherItem.getEntityType() != EntityType.UNKNOWN || !mobCatcherItem.getWhitelistedEntities().contains(event.getRightClicked().getType())) {
            return;
        }

        Entity entity = event.getRightClicked();
        mobCatcherItem.setEntityType(entity.getType());
        mobCatcherItem.setNbtEntity(new NBTEntity(entity).toString());

        pdc.set(plugin.getKey(), plugin.getBeanDataType(), mobCatcherItem);
        item.setItemMeta(meta);
        ItemStackUtils.glow(item);
        entity.remove();
    }
}
