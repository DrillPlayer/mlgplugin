package de.drillplayer.mlg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sql.SQL;
import sql.SQLGetter;

import java.sql.SQLException;

public class Main extends JavaPlugin implements Listener {

    public static Main instance;
    public SQL SQL;

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new GUIHandler(), this);
        this.getCommand("mlggui").setExecutor(new MLGGUICommand());
        this.getCommand("mlg").setExecutor(new MLGCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
        getLogger().info("§8[§9MineTrox§8] §aMLG Konzept gestartet");
        this.SQL = new SQL();

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            Bukkit.getLogger().info("Datenbank ist nicht connected");
        } catch (SQLException throwables) {
            Bukkit.getLogger().info("Datenbank ist nicht connected");
        }

        if (SQL.isConnected()) {
            Bukkit.getLogger().info("Datenbank ist connected");
            SQLGetter.createStatsTable();
            SQLGetter.createMLGTable();
        }
    }

    public void onDisable() {
        SQL.disconnect();
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
            if (SQLGetter.getMLG(event.getEntity().getUniqueId())) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (event.getDamage() > 20) {
                        event.setCancelled(true);
                        event.getEntity().sendMessage("§8[§9MineTrox§8] §cDu hast den MLG leider nicht geschafft!");
                        SQLGetter.addF(event.getEntity().getUniqueId());
                        Player player = (Player) event.getEntity();
                        player.getInventory().clear();
                    }
                }
            }
    }

    @EventHandler
    public void onMLG(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (SQLGetter.getMLG(player.getUniqueId())) {
                if (player.getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                    SQLGetter.addS(player.getUniqueId());
                    player.sendMessage("§8[§9MineTrox§8] §aGlückwunsch! Du hast den MLG geschafft!");
                } else if (player.getInventory().getItemInMainHand().getType() == Material.COBWEB) {
                    SQLGetter.addS(player.getUniqueId());
                    player.sendMessage("§8[§9MineTrox§8] §aGlückwunsch! Du hast den MLG geschafft!");
                }
            }
        }
    }

    @EventHandler
    public void onBoat(VehicleEnterEvent event) {
        if (SQLGetter.getMLG(event.getEntered().getUniqueId())) {
            if (event.getVehicle().getType().equals(Material.BIRCH_BOAT)) {
                SQLGetter.addS(event.getEntered().getUniqueId());
                event.getEntered().sendMessage("§8[§9MineTrox§8] §aGlückwunsch! Du hast den MLG geschafft!");
            }
        }
    }


    public static Main getInstance() {
        return instance;
    }
}