package sql;

import de.drillplayer.mlg.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {


    private String host = Main.getInstance().getConfig().getString("sql.host");
    private String port = Main.getInstance().getConfig().getString("sql.port");
    private String database = Main.getInstance().getConfig().getString("sql.database");
    private String username = Main.getInstance().getConfig().getString("sql.username");
    private String password = Main.getInstance().getConfig().getString("sql.password");

    private static Connection connection;

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
