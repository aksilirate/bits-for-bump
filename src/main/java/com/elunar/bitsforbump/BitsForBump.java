package com.elunar.bitsforbump;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BitsForBump extends JavaPlugin implements Listener {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
