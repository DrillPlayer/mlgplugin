package de.drillplayer.mlg;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sql.SQLGetter;

public class MLGCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getGameMode() != GameMode.CREATIVE) {
                SQLGetter.createMLG(player);
                SQLGetter.createStatsPlayer(player);
                SQLGetter.updateMLG(player.getUniqueId(), !SQLGetter.getMLG(player.getUniqueId()));
                if (SQLGetter.getMLG(player.getUniqueId())) {
                    sender.sendMessage("§aDu bist nun im MLG Modus!");
                } else {
                    sender.sendMessage("§cDu bist nicht mehr im MLG Modus!");
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
