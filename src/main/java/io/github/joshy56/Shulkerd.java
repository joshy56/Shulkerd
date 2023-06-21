package io.github.joshy56;

import io.github.joshy56.listeners.InventoryCloseListener;
import io.github.joshy56.listeners.PlayerInteractListener;
import io.github.joshy56.listeners.PlayerQuitListener;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author joshy56
 * @since 19/6/2023
 */
public class Shulkerd extends JavaPlugin {
    private final Map<UUID, ItemStack> boxes;

    public Shulkerd(){
        boxes = new HashMap<>();
    }

    @Override
    public void onEnable() {
        setListeners();
    }

    @Override
    public void onDisable() {
        PlayerInteractEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    private void setListeners() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(this), this);
    }

    public Map<UUID, ItemStack> getBoxes() {
        return boxes;
    }
}
