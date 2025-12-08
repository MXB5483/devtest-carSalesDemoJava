package com.broadcom.demo.vehicles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Controller
public class DashboardController {

    // Path used inside Docker/K8s for dynamic config
    private static final Path CONFIG_PATH = Path.of("/config/api-env.properties");

    @GetMapping("/dashboard")
    public String dashboard(Model model) throws IOException {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(CONFIG_PATH.toFile())) {
            props.load(fis);
        }

        Map<String, Map<String, String>> endpoints = new TreeMap<>();
        for (String key : props.stringPropertyNames()) {
            String[] parts = key.split("\\.");
            if (parts.length == 2) {
                String prefix = parts[0];
                String env = parts[1];
                endpoints
                    .computeIfAbsent(prefix, k -> new LinkedHashMap<>())
                    .put(env, props.getProperty(key));
            }
        }

        model.addAttribute("endpoints", endpoints);
        model.addAttribute("envList", List.of("DEV", "UAT", "PROD"));
        return "dashboard";
    }

    @PostMapping("/dashboard/update")
    public String update(
            @RequestParam String endpoint,
            @RequestParam String active,
            @RequestParam String dev,
            @RequestParam String uat,
            @RequestParam String prod
    ) throws IOException {

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH.toFile())) {
            props.load(fis);
        }

        props.setProperty(endpoint + ".DEV", dev);
        props.setProperty(endpoint + ".UAT", uat);
        props.setProperty(endpoint + ".PROD", prod);
        props.setProperty(endpoint + ".ACTIVE", active);

        try (FileOutputStream out = new FileOutputStream(CONFIG_PATH.toFile())) {
            props.store(out, null);
        }

        return "redirect:/dashboard";
    }
}
