package com.broadcom.demo.vehicles.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Properties;

@Service
public class DynamicPropertiesService {

    private static final Path CONFIG_PATH = Path.of("/config/api-env.properties");

    public Properties load() {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_PATH.toFile())) {
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public String get(String key) {
        return load().getProperty(key);
    }

    public void set(String key, String value) {
        Properties props = load();
        props.setProperty(key, value);
        try (FileOutputStream out = new FileOutputStream(CONFIG_PATH.toFile())) {
            props.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
