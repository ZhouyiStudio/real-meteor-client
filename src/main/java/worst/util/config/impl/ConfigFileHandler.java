package worst.util.config.impl;

import worst.util.config.impl.consolelogger.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * © 2026 Copyright Worst Client
 * All Rights Reserved ®
 */

public class ConfigFileHandler {

    private final ReentrantReadWriteLock lock;

    public ConfigFileHandler() {
        this.lock = new ReentrantReadWriteLock();
    }

    public void createDirectories() {
        try {
            Files.createDirectories(ConfigPath.getConfigDirectory());
        } catch (IOException e) {
            Logger.error("AutoConfiguration: Failed to create directories!");
        }
    }

    public boolean write(String content) {
        lock.writeLock().lock();
        try {
            Path configFile = ConfigPath.getConfigFile();
            Path tempFile = configFile.resolveSibling(configFile.getFileName() + ".tmp");

            Files.writeString(tempFile, content, StandardCharsets.UTF_8);
            Files.move(tempFile, configFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            return true;
        } catch (IOException e) {
            Logger.error("AutoConfiguration: Write failed! " + e.getMessage());
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String read() {
        lock.readLock().lock();
        try {
            Path configFile = ConfigPath.getConfigFile();

            if (!Files.exists(configFile)) {
                return null;
            }

            return Files.readString(configFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logger.error("AutoConfiguration: Read failed! " + e.getMessage());
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean exists() {
        return Files.exists(ConfigPath.getConfigFile());
    }
}