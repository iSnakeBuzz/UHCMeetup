package com.isnakebuzz.meetup.Database.Core;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Callback;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL_v2 {

    private Main plugin;

    private ComboPooledDataSource dataSource;

    public MySQL_v2(Main plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        dataSource = new ComboPooledDataSource();
        ConfigurationSection databaseSection = plugin.getConfig("Extra/Database").getConfigurationSection("MySQL");
        String driverClass = databaseSection.getString("driver-class");
        String jdbcUrl = databaseSection.getString("jdbc-url");
        String host = databaseSection.getString("hostname");
        String port = databaseSection.getString("port");
        String database = databaseSection.getString("database");
        String user = databaseSection.getString("username");
        String password = databaseSection.getString("password");
        int checkoutTimeout = databaseSection.getInt("checkout-timeout");

        try {
            dataSource.setDriverClass(driverClass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }

        dataSource.setJdbcUrl(jdbcUrl.replace("<host>", host).replace("<port>", port).replace("<database>", database));
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setCheckoutTimeout(checkoutTimeout);
    }

    public void prepareStatement(String sql, Callback<ResultSet> callback) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                connection = dataSource.getConnection();
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();

                callback.done(resultSet);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
    }

    public void disconnect() {
        dataSource.close();
    }


}
