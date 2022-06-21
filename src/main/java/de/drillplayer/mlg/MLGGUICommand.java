package de.drillplayer.mlg;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sql.SQLGetter;

public class MLGGUICommand implements CommandExecutor, Listener {

    public SQLGetter data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getGameMode() != GameMode.CREATIVE) {
                if (data.getMLG(player.getUniqueId())) {
                    Location curr = player.getLocation();
                    Inventory gui = Bukkit.createInventory(player, 27, "MLG");
                    ItemStack glass = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
                    ItemMeta glassmeta = glass.getItemMeta();
                    glassmeta.setDisplayName("");
                    glass.setItemMeta(glassmeta);
                    for (int slot = 0; slot < 12; slot++) {
                        if (gui.getItem(slot) == null) {
                            gui.setItem(slot, glass);
                        }
                    }
                    ItemStack water = new ItemStack(Material.WATER_BUCKET, 1);
                    ItemMeta watermeta = water.getItemMeta();
                    watermeta.setDisplayName("§9Wasseimer-MLG");
                    water.setItemMeta(watermeta);

                    ItemStack web = new ItemStack(Material.COBWEB, 1);
                    ItemMeta webmeta = web.getItemMeta();
                    webmeta.setDisplayName("§9Cobweb-MLG");
                    web.setItemMeta(webmeta);


                    gui.setItem(12, water);
                    gui.setItem(13, glass);
                    gui.setItem(14, web);
                    gui.setItem(15, glass);
                    for (int slot = 15; slot < 27; slot++) {
                        if (gui.getItem(slot) == null) {
                            gui.setItem(slot, glass);
                        }
                    }
                    player.openInventory(gui);

                } else {
                    player.sendMessage("§4Du musst hierf\u00fcr im MLG-Modus sein! /mlg");
                }
            } else {
                player.sendMessage("§4Diesen Befehl darf man nicht im Creative Modus ausf\u00fchren!");
            }
        } else {
            sender.sendMessage("§4Diesen Befehl darf nur ein Spieler ausf\u00fchren!");
        }

        return true;
    }


}
