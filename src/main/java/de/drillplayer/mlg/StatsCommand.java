package de.drillplayer.mlg;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sql.SQLGetter;

public class StatsCommand implements CommandExecutor {

    public SQLGetter data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SQLGetter.createStatsPlayer(player);
            player.sendMessage("§8-----------------------------------\n" +
                    "§8[§9MLG§8] §eYour MLG Stats:\n" +
                    "§8[§9MLG§8] \n" +
                    "§8[§9MLG§8] §aErfolgreiche MLG`s: " + SQLGetter.getS(player.getUniqueId()) + "\n" +
                    "§8[§9MLG§8] §cVerfehlte MLG`s:  " + SQLGetter.getF(player.getUniqueId()) + "\n" +
                    "§8-----------------------------------\n");
        } else {
            sender.sendMessage("§4Diesen Befehl darf nur ein Spieler ausf\u00fchren!");
        }
        return true;
    }
}
