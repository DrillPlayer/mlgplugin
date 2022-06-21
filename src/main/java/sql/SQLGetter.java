package sql;

import de.drillplayer.mlg.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Main plugin;

    public SQLGetter(Main plugin) {
        this.plugin = plugin;
    }

    public static void createStatsTable() {
        PreparedStatement ps;
        try {
            ps = SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS stats (NAME VARCHAR(100), UUID VARCHAR(100), S INT (100), F INT (100), PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createMLG(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!existsPoints(uuid)) {
                PreparedStatement ps = SQL.getConnection().prepareStatement("INSERT IGNORE INTO mlg (NAME,UUID) VALUES (?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createMLGTable() {
        PreparedStatement ps;
        try {
            ps = SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mlg " + "(NAME VARCHAR(100), UUID VARCHAR(100), MLG BOOLEAN, PRIMARY KEY (NAME))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createStatsPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            if (!existsPoints(uuid)) {
                PreparedStatement ps = SQL.getConnection().prepareStatement("INSERT IGNORE INTO stats (NAME,UUID) VALUES (?,?)");
                ps.setString(1, player.getName());
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static boolean existsPoints(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT * FROM stats WHERE UUID =?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public static void updateMLG(UUID uuid, boolean mlg) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE mlg SET MLG=? WHERE UUID=?");
            ps.setBoolean(1, mlg);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addS(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE stats SET S=? WHERE UUID=?");
            ps.setInt(1, getS(uuid) + 1);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addF(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("UPDATE stats SET F=? WHERE UUID=?");
            ps.setInt(1, getF(uuid) + 1);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int getS(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT S FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int s = 0;
            if (rs.next()) {
                s = rs.getInt("S");
                return s;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static int getF(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT F FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int f = 0;

            if (rs.next()) {
                f = rs.getInt("F");
                return f;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static boolean getMLG(UUID uuid) {
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT MLG FROM mlg WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("MLG");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}