package ru.violence.tablogger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TabLoggerPlugin extends JavaPlugin {
    private FileLogger fileLogger;

    @Override
    public void onEnable() {
        try {
            this.fileLogger = new FileLogger(this, "tab_complete.log");
            this.fileLogger.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Bukkit.getPluginManager().registerEvents(new TabListener(this.fileLogger), this);
    }

    @Override
    public void onDisable() {
        this.fileLogger.disable();
    }
}
