package io.github.joshy56.listeners;

import io.github.joshy56.Shulkerd;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

/**
 * @author joshy56
 * @since 19/6/2023
 */
public class InventoryCloseListener implements Listener {
    private final Shulkerd plugin;

    public InventoryCloseListener(Shulkerd plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        plugin.getLogger().info("Inventory close received.");
        Inventory inventory = event.getInventory();
        plugin.getLogger().info("Inventory close, inventory type: " + inventory.getType().name() + ".");
        if(inventory.getType() != InventoryType.SHULKER_BOX) return;
        plugin.getLogger().info("Inventory close, is a shulker box.");
        if(inventory.getHolder() != null) return;
        plugin.getLogger().info("Inventory close, do not have holder.");

        Player player = (Player) event.getPlayer();
        player.playSound(player.getEyeLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, 1, 1);
        ItemStack shulkerBox = plugin.getBoxes().remove(player.getUniqueId());
        if (shulkerBox == null) return;
        plugin.getLogger().info("Invetory close, is a non block shulker box.");

        BlockStateMeta meta = (BlockStateMeta) shulkerBox.getItemMeta();

        ShulkerBox shulker = (ShulkerBox) meta.getBlockState();
        shulker.getInventory().setContents(inventory.getContents());
        shulker.update();

        meta.setBlockState(shulker);
        shulkerBox.setItemMeta(meta);
        player.updateInventory();
    }
}
