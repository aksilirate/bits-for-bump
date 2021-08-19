package com.elunar.bitsforbump;

import com.elunar.bitsforbump.tasks.UpdateLeaderboard;
import github.scarsz.discordsrv.DiscordSRV;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public final class BitsForBump extends JavaPlugin {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    public FileConfiguration config = this.getConfig();

    public DataManager dataManager;

    public Economy economy;


    @Override
    public void onEnable() {

        dataManager = new DataManager(this);


        config.addDefault("leaderboard-size", 10);
        config.addDefault("leaderboard-channel-id", "0");
        config.options().copyDefaults(true);
        saveConfig();



        if (!setupEconomy() ) {
            System.out.println("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null){
            economy = rsp.getProvider();
        }


        getServer().getPluginManager().registerEvents(new EventListener(this), this);




        DiscordSRV.api.subscribe(discordsrvListener);

        BukkitTask updateLeaderboard = new UpdateLeaderboard(this).runTaskTimer(this, 20, 6000);

        if (!getDataFolder().exists()) {
            if (getDataFolder().mkdirs()) {
                getLogger().info("Data dir was created.");
            }
        }

    }


    private boolean setupEconomy() {
        return getServer().getPluginManager().getPlugin( "Vault") != null;

    }

    @Override
    public void onDisable() {
        DiscordSRV.api.unsubscribe(discordsrvListener);
    }


}
