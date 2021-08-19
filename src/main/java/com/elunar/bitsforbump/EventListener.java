package com.elunar.bitsforbump;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EventListener implements Listener {

    BitsForBump bitsForBump;

    public EventListener(BitsForBump bitsForBump) {
        this.bitsForBump = bitsForBump;
    }


    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onServiceRegister(ServiceRegisterEvent event) {

        RegisteredServiceProvider<Economy> eco_rsp = bitsForBump.getServer().getServicesManager().getRegistration(Economy.class);
        if (eco_rsp != null) {
            bitsForBump.economy = eco_rsp.getProvider();
        }
    }


}
