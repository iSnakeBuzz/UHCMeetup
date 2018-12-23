package com.isnakebuzz.meetup.Utils.Managers;

import com.isnakebuzz.meetup.Main;
import com.isnakebuzz.meetup.Utils.Connection;
import com.mongodb.Mongo;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DependManager {

    private Main plugin;

    public DependManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadDepends() {
        try {
            plugin.log("Depends", "Downloading dependencies");
            if (Connection.Database.database == Connection.Database.MONGODB) {
                this.downloadDependency(new URL("http://central.maven.org/maven2/org/mongodb/mongo-java-driver/3.9.1/mongo-java-driver-3.9.1.jar"), "mongo-java-driver.jar");
            } else if (Connection.Database.database == Connection.Database.MYSQL) {
                this.downloadDependency(new URL("http://central.maven.org/maven2/c3p0/c3p0/0.9.1.2/c3p0-0.9.1.2.jar"), "c3p");
                this.downloadDependency(new URL("http://central.maven.org/maven2/mysql/mysql-connector-java/8.0.13/mysql-connector-java-8.0.13.jar"), "mysql-connector-java");
                this.downloadDependency(new URL("http://central.maven.org/maven2/com/mchange/mchange-commons-java/0.2.15/mchange-commons-java-0.2.15.jar"), "mchange-commons-java");

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            plugin.log("Depends", "Error downloading dependencies");
        } finally {
            plugin.log("Depends", "All has been downloaded");
        }
    }

    private void downloadDependency(URL url, String name) {
        File libraries = new File(plugin.getDataFolder(), "Libraries");
        if (!libraries.exists()) libraries.mkdir();

        File fileName = new File(libraries, name + ".jar");

        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
                download(url, fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }

        loadJarFile(fileName);
    }

    private void download(final URL url, final File destinyFile) throws IOException {
        InputStream stream = url.openStream();
        ReadableByteChannel rbc = Channels.newChannel(stream);
        FileOutputStream fos = new FileOutputStream(destinyFile);
        fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
        fos.flush();
        fos.close();
    }

    private void loadJarFile(File jar) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Class<?> getClass = classLoader.getClass();
            Method method = getClass.getSuperclass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, jar.toURI().toURL());
        } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        } finally {
            plugin.log("Depends", "Has been downloaded " + jar.getName());
        }
    }

}
