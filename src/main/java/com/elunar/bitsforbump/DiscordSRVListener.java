package com.elunar.bitsforbump;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class DiscordSRVListener {

    private final Plugin plugin;

    public DiscordSRVListener(Plugin plugin) {
        this.plugin = plugin;

    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void discordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        User user = event.getMember().getUser();
        UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(user.getId());

        TextChannel textChannel = event.getChannel();


        if (textChannel.getId().equals("852519391488901141")) {
            if (event.getMessage().getContentRaw().equals("!d bump")) {
                if (uuid != null) {
                    String playerName = plugin.getServer().getOfflinePlayer(uuid).getName();
                    textChannel.sendMessage("Thanks for attempting to bump! you have gained 1 bit.").queue();
                    BitsForBump.eco.depositPlayer(playerName, 1.0);

                } else {
                    textChannel.sendMessage("account not linked.").queue();
                }
            }
        }


    }


}
