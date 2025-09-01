package com.example.config;

import java.nio.file.Path;

/** Singleton-aware wrapper that uses the single AppSettings instance. */
public class SettingsLoader {
    public AppSettings load(Path file) {
        AppSettings s = AppSettings.getInstance(); // use singleton instance
        s.loadFromFile(file);
        return s;
    }
}
