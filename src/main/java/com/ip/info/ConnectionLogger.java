package com.ip.info;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.net.InetAddress;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConnectionLogger extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ConnectionLogger has been enabled!");
    }

    private String formatIpInfo(String jsonData) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonData);
            
            // Get nested objects
            JSONObject company = (JSONObject) json.get("company");
            JSONObject location = (JSONObject) json.get("location");
            JSONObject asn = (JSONObject) json.get("asn");
            
            return String.format(
                "§c§lIP Information:\n\n" +
                "§f§lGeneral Info:\n" +
                "§7IP: §f%s\n" +
                "§7Is VPN: §f%s\n" +
                "§7Is Proxy: §f%s\n" +
                "§7Is Mobile: §f%s\n" +
                "§7Is DataCenter: §f%s\n\n" +
                "§f§lLocation:\n" +
                "§7Country: §f%s\n" +
                "§7State: §f%s\n" +
                "§7City: §f%s\n" +
                "§7Timezone: §f%s\n\n" +
                "§f§lNetwork Info:\n" +
                "§7Company: §f%s\n" +
                "§7Network: §f%s\n" +
                "§7ASN: §f%s\n" +
                "§7Route: §f%s",
                json.get("ip"),
                json.get("is_vpn"),
                json.get("is_proxy"),
                json.get("is_mobile"),
                json.get("is_datacenter"),
                location.get("country"),
                location.get("state"),
                location.get("city"),
                location.get("timezone"),
                company.get("name"),
                company.get("network"),
                asn.get("asn"),
                asn.get("route")
            );
        } catch (Exception e) {
            getLogger().warning("Error parsing JSON: " + e.getMessage());
            return "Error parsing IP information";
        }
    }

    private String getIpInfo(String ip) {
        try {
            URL url = new URL("https://api.ipapi.is/?q=" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            return formatIpInfo(response.toString());
            
        } catch (Exception e) {
            getLogger().warning("Error fetching IP info: " + e.getMessage());
            return "Could not fetch IP details";
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        InetAddress address = event.getPlayer().getAddress().getAddress();
        String ip = address.getHostAddress();
        
        String ipInfo = getIpInfo(ip);
        event.getPlayer().kickPlayer(ipInfo);
        
        getLogger().info(String.format(
            "Player kicked - Name: %s, IP Info: %s",
            playerName,
            ipInfo
        ));
    }

    @Override
    public void onDisable() {
        getLogger().info("ConnectionLogger has been disabled!");
    }
}