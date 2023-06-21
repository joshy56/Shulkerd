package io.github.joshy56.listeners;

import io.github.joshy56.Shulkerd;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Optional;

/**
 * @author joshy56
 * @since 19/6/2023
 */
public class PlayerInteractListener implements Listener {
    private final Shulkerd plugin;

    public PlayerInteractListener(Shulkerd plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        plugin.getLogger().info("Interact event received.");
        if (event.getAction() != Action.RIGHT_CLICK_AIR || !event.hasItem() || !event.isBlockInHand()) return;
        plugin.getLogger().info("Interact event, filter passed.");

        Player player = event.getPlayer();
        if (!player.hasPermission("shulkerd.use")) return;
        plugin.getLogger().info("Interact event, player has permission.");
        if (event.getItem().getAmount() != 1) return;
        plugin.getLogger().info("Interact event, item is only one.");

        Optional.ofNullable(event.getItem().getItemMeta())
                .filter(BlockStateMeta.class::isInstance)
                .map(BlockStateMeta.class::cast)
                .filter(BlockStateMeta::hasBlockState)
                .map(BlockStateMeta::getBlockState)
                .filter(ShulkerBox.class::isInstance)
                .map(ShulkerBox.class::cast)
                .map(ShulkerBox::getInventory)
                .map(player::openInventory)
                .ifPresent(shulkerBox -> {
                    player.playSound(player.getEyeLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1, 1);
                    plugin.getBoxes().put(player.getUniqueId(), event.getItem());
                });
        plugin.getLogger().info("Interact event, shulker box opened.");
    }
}
