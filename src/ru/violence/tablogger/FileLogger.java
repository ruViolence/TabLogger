package ru.violence.tablogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FileLogger extends Thread {
    private boolean running = true;
    private final File file;
    private final BufferedWriter writer;
    private final Deque<String> queue = new ConcurrentLinkedDeque<>();

    public FileLogger(TabLoggerPlugin plugin, String fileName) throws IOException {
        plugin.getDataFolder().mkdirs();
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!this.file.exists()) {
            this.file.createNewFile();
        }
        this.writer = new BufferedWriter(new FileWriter(this.file, true));
    }

    public void log(String s) {
        this.queue.add(s);
    }

    public void disable() {
        this.running = false;
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                boolean hasUpdate = false;
                while (true) {
                    String poll = this.queue.pollFirst();
                    if (poll == null) break;

                    this.writer.write(poll);
                    this.writer.newLine();
                    hasUpdate = true;
                }

                if (hasUpdate) this.writer.flush();

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
