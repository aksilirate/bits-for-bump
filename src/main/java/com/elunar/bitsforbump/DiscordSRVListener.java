package com.elunar.bitsforbump;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import java.util.UUID;

public class DiscordSRVListener {

    private final BitsForBump bitsForBump;

    public DiscordSRVListener(BitsForBump bitsForBump) {
        this.bitsForBump = bitsForBump;

    }

    @Subscribe(priority = ListenerPriority.MONITOR)
    public void discordMessageReceived(DiscordGuildMessageReceivedEvent event) {
        User user = event.getMember().getUser();
        UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(user.getId());

        TextChannel textChannel = event.getChannel();




        if (textChannel.getId().equals("852519391488901141")) {
            if (event.getMessage().getContentRaw().equals("!d bump")) {
                if (uuid != null) {
                    Integer playerBumps = bitsForBump.dataManager.getYamlPlayerBumps(user.getId());
                    String playerName = bitsForBump.getServer().getOfflinePlayer(uuid).getName();
                    textChannel.sendMessage("Thanks for attempting to bump, you have gained ***1✦***.").queue();
                    bitsForBump.eco.depositPlayer(playerName, 1.0);
                    bitsForBump.dataManager.setYamlPlayerBumps(user.getId(), playerBumps + 1);
                } else {
                    textChannel.sendMessage("account not linked.").queue();
                }
            }
        }

        if (event.getMessage().getContentRaw().startsWith("/bumps")) {
            Integer playerBumps = bitsForBump.dataManager.getYamlPlayerBumps(user.getId());
            textChannel.sendMessage("You have bumped ***" + Integer.toString(playerBumps) + "*** times.").queue();


        }



    }


}
