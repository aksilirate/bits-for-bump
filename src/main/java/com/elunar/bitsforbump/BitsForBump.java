package com.elunar.bitsforbump;

import github.scarsz.discordsrv.DiscordSRV;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class BitsForBump extends JavaPlugin {

    private DiscordSRVListener discordsrvListener = new DiscordSRVListener(this);

    public DataManager dataManager;

    public Economy eco;


    @Override
    public void onEnable() {

        dataManager = new DataManager(this);

        DiscordSRV.api.subscribe(discordsrvListener);

        if (!setupEconomy() ) {
            System.out.println("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else{
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null){
                eco = rsp.getProvider();
            }

        }


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
