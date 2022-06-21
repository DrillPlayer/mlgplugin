package de.drillplayer.mlg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIHandler implements Listener {


    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("MLG")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType().equals(Material.WATER_BUCKET) || event.getCurrentItem().getType().equals(Material.COBWEB)) {
                Player player = (Player) event.getWhoClicked();
                Location location = player.getLocation();
                location.add(0, 150, 0);
                player.teleport(location);
                if (event.getCurrentItem().getType().equals(Material.WATER_BUCKET)) {
                    player.getInventory().clear();
                    player.getInventory().setItem(0, new ItemStack(Material.WATER_BUCKET));
                } else if (event.getCurrentItem().getType().equals(Material.COBWEB)) {
                    player.getInventory().clear();
                    player.getInventory().setItem(0, new ItemStack(Material.COBWEB));
                }
                player.closeInventory();

            }
        }
    }


}
