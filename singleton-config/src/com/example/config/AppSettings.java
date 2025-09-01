package com.example.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Thread-safe Singleton: private constructor, lazy initialization, 
 * reflection-safe, serialization-safe with readResolve.
 */
public class AppSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    private static volatile AppSettings instance;
    private final Properties props = new Properties();

    // Private constructor to prevent instantiation
    private AppSettings() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new IllegalStateException("Singleton instance already exists. Use getInstance() instead.");
        }
    }

    // Thread-safe lazy initialization using double-checked locking
    public static AppSettings getInstance() {
        if (instance == null) {
            synchronized (AppSettings.class) {
                if (instance == null) {
                    instance = new AppSettings();
                }
            }
        }
        return instance;
    }

    public void loadFromFile(Path file) {
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    // Preserve singleton on deserialization
    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }
}
