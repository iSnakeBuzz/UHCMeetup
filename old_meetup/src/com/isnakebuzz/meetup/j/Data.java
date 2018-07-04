package com.isnakebuzz.meetup.j;

import com.isnakebuzz.meetup.a.Main;
import org.bukkit.*;
import org.bukkit.plugin.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Data
{
    private Connection Con;
    private Statement St;
    private String Host;
    private String Database;
    private String Username;
    private String Password;
    private int Port;
    private String Name;
    private DatabaseType DataStorage;
    
    public void SetDatabase(final DatabaseType Name) {
        this.DataStorage = Name;
    }
    
    public boolean IsDatabase(final DatabaseType Name) {
        return this.DataStorage == Name;
    }
    
    public DatabaseType GetDatabase() {
        return this.DataStorage;
    }
    
    public String GetName() {
        return this.Name;
    }
    
    public Data(final String Name, final Main Instance) {
        this.Port = 3306;
        this.Name = Name;
        this.DataStorage = DatabaseType.SQLite;
        this.Disconnect();
    }
    
    public void Connect() {
        if (this.Con != null) {
            return;
        }
        switch (this.DataStorage) {
            case SQLite: {
                final File DataFile = new File(Main.plugin.getDataFolder(), "/" + this.Name + ".db");
                if (!DataFile.exists()) {
                    try {
                        DataFile.createNewFile();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                        Bukkit.getPluginManager().disablePlugin((Plugin)Main.plugin);
                    }
                }
                try {
                    Class.forName("org.sqlite.JDBC");
                    try {
                        this.Con = DriverManager.getConnection("jdbc:sqlite:" + DataFile);
                        this.St = this.Con.createStatement();
                    }
                    catch (SQLException ex2) {
                        ex2.printStackTrace();
                        Bukkit.getPluginManager().disablePlugin((Plugin)Main.plugin);
                    }
                }
                catch (ClassNotFoundException ex3) {
                    ex3.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin((Plugin)Main.plugin);
                }
            }
            break;
            case MySQL: {
                this.Host = Main.plugin.getConfig().getString("MySQL.Host");
                this.Database = Main.plugin.getConfig().getString("MySQL.Database");
                this.Username = Main.plugin.getConfig().getString("MySQL.Username");
                this.Password = Main.plugin.getConfig().getString("MySQL.Password");
                this.Port = Main.plugin.getConfig().getInt("MySQL.Port");
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    try {
                        this.Con = DriverManager.getConnection("jdbc:mysql://" + this.Host + ":" + this.Port + "/" + this.Database, this.Username, this.Password);
                        this.St = this.Con.createStatement();
                    }
                    catch (SQLException ex2) {
                        ex2.printStackTrace();
                        Bukkit.getPluginManager().disablePlugin((Plugin)Main.plugin);
                    }
                    return;
                } catch (ClassNotFoundException ex3) {
                    ex3.printStackTrace();
                    Bukkit.getPluginManager().disablePlugin((Plugin)Main.plugin);
                }
                break;
            }
        }
    }
    
    public void Restart() {
        this.Disconnect();
        this.Connect();
    }
    
    public void Disconnect() {
        try {
            if (this.IsConnected()) {
                this.Con.close();
            }
        }
        catch (SQLException ex) {
        }
    }
    
    public boolean IsConnected() {
        return this.Con != null;
    }
    
    public boolean IsDisconnected() {
        return this.Con == null;
    }
    
    public void ExecuteUpdate(final String Query) {
        try {
            this.St.executeUpdate(Query);
        }
        catch (SQLException ex) {
        }
    }
    
    public ResultSet ExecuteQuery(final String Query) {
        try {
            return this.St.executeQuery(Query);
        }
        catch (SQLException ex) {
            return null;
        }
    }
    
    public int GetValue(final UUID Uuid, final String Section) {
        int Value = 0;
        try {
            final ResultSet Rs = this.ExecuteQuery("SELECT * FROM " + this.Name + " WHERE UUID='" + Uuid + "'");
            if (Rs.next()) {
                Rs.getInt(Section);
            }
            Value = Integer.valueOf(Rs.getInt(Section));
        }
        catch (SQLException ex) {}
        return Value;
    }
    
    public void SetValue(final UUID Uuid, final String Section, final int Value) {
        this.ExecuteUpdate("UPDATE " + this.Name + " SET " + Section + "='" + Value + "' WHERE UUID='" + Uuid + "'");
    }
    
    public void AddValue(final UUID Uuid, final String Section, final int Value) {
        this.ExecuteUpdate("UPDATE " + this.Name + " SET " + Section + "='" + this.GetValue(Uuid, Section) + Value + "' WHERE UUID='" + Uuid + "'");
    }
    
    public enum DatabaseType
    {
        SQLite("SQLite", 0), 
        MySQL("MySQL", 1);
        
        private DatabaseType(final String s, final int n) {
        }
    }
}
