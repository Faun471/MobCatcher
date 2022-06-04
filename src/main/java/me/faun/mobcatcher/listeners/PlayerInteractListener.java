package me.faun.mobcatcher.listeners;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTEntity;
import me.faun.mobcatcher.MobCatcher;
import me.faun.mobcatcher.items.MobCatcherItem;
import me.faun.mobcatcher.utils.StringUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Arrays;

public class PlayerInteractListener implements Listener {

    private final MobCatcher plugin;
    public PlayerInteractListener(MobCatcher plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || block == null) {
            return;
        }

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (!pdc.has(plugin.getKey(), plugin.getBeanDataType())) {
            return;
        }

        MobCatcherItem bean = pdc.get(plugin.getKey(), plugin.getBeanDataType());
        assert bean != null;
        System.out.println(bean);

        if (bean.getEntityType() == EntityType.UNKNOWN) {
            return;
        }

        NBTContainer container = new NBTContainer(bean.getNbtEntity());

        removeKeys(container, "Pos", "Paper.Origin", "FallFlying", "Motion");

        Location location = event.getInteractionPoint() == null ? event.getPlayer().getLocation() : event.getInteractionPoint();

        block.getWorld().spawnEntity(location, bean.getEntityType(), CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
            NBTEntity nbtEntity = new NBTEntity(entity);
            nbtEntity.mergeCompound(container);
        });

        pdc.set(plugin.getKey(), plugin.getBeanDataType(), bean.use());
        item.setItemMeta(meta);

        if (bean.getUses() == 0) {
            event.getPlayer().getInventory().remove(item);
            event.getPlayer().sendActionBar(StringUtils.messageParse("&cLmfao it broke xD"));
        }
    }

    private void removeKeys(NBTContainer container, String... keys) {
        Arrays.stream(keys).forEach(container::removeKey);
    }
}
