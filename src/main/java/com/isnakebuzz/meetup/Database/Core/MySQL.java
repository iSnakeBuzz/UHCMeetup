package com.isnakebuzz.meetup.Database.Core;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Properties;

public class MySQL {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static int port;
    public static Connection con;

    static {
        MySQL.username = "";
        MySQL.password = "";
        MySQL.database = "";
        MySQL.host = "";
        MySQL.port = 3306;
    }

    public static void connect() {
        if (!isConnected()) {
            try {
                final Properties properties = new Properties();
                properties.setProperty("user", MySQL.username);
                properties.setProperty("password", MySQL.password);
                properties.setProperty("autoReconnect", "true");
                properties.setProperty("verifyServerCertificate", "false");
                properties.setProperty("useSSL", "false");
                properties.setProperty("requireSSL", "false");
                MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.host + ":" + MySQL.port + "/" + MySQL.database, properties);
                Bukkit.getConsoleSender().sendMessage("§aConexion a base de datos satisfecha.");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage("§cNo se pudo conectar a la base de datos");
                Bukkit.getConsoleSender().sendMessage("jdbc:mysql://" + MySQL.host + ":" + MySQL.port + "/" + MySQL.database);
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                MySQL.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return MySQL.con != null;
    }

    public static void update(final String qry) {
        if (isConnected()) {
            try {
                MySQL.con.createStatement().executeUpdate(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(final String qry) {
        if (isConnected()) {
            try {
                return MySQL.con.createStatement().executeQuery(qry);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getFirstString(final ResultSet rs, final int l, final String re, final int t) {
        try {
            while (rs.next()) {
                if (rs.getString(l).equalsIgnoreCase(re)) {
                    return rs.getString(t);
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public static int getFirstInt(final ResultSet rs, final int l, final String re, final int t) {
        try {
            while (rs.next()) {
                if (rs.getString(l).equalsIgnoreCase(re)) {
                    return rs.getInt(t);
                }
            }
        } catch (Exception ex) {
        }
        return 0;
    }

    public static Connection getConnection() {
        return MySQL.con;
    }

    public static void closeRessources(final ResultSet rs, final PreparedStatement st) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex2) {
            }
        }
    }

    public static void close(final PreparedStatement st, final ResultSet rs) {
        try {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
        }
    }

    public static void executeUpdate(final String statement) {
        try {
            final PreparedStatement st = MySQL.con.prepareStatement(statement);
            st.executeUpdate();
            close(st, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeUpdate(final PreparedStatement statement) {
        try {
            statement.executeUpdate();
            close(statement, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(final String statement) {
        try {
            final PreparedStatement st = MySQL.con.prepareStatement(statement);
            return st.executeQuery();
        } catch (Exception ex) {
            return null;
        }
    }

    public static ResultSet executeQuery(final PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (Exception ex) {
            return null;
        }
    }

    public static ResultSet query(final String query) throws SQLException {
        final Statement stmt = MySQL.con.createStatement();
        try {
            stmt.executeQuery(query);
            return stmt.getResultSet();
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
            return null;
        }
    }
}
