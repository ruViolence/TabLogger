package ru.violence.tablogger;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TabListener implements Listener {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss", Locale.ENGLISH);
    private final FileLogger fileLogger;

    public TabListener(FileLogger fileLogger) {
        this.fileLogger = fileLogger;
    }

    @EventHandler
    public void onAsyncTabComplete(AsyncTabCompleteEvent event) {
        LocalDateTime now = LocalDateTime.now();
        this.fileLogger.log("[" + this.dateFormatter.format(now) + "] [" + this.timeFormatter.format(now) + "] " + event.getSender().getName() + ": " + event.getBuffer());
    }
}
