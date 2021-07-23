package com.elunar.bitsforbump;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import org.bukkit.OfflinePlayer;

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
                    OfflinePlayer offlinePlayer = bitsForBump.getServer().getOfflinePlayer(uuid);
                    textChannel.sendMessage("Thanks for attempting to bump, you have gained **1✦**.").queue();
                    bitsForBump.eco.depositPlayer(offlinePlayer, 1.0);
                    bitsForBump.dataManager.setYamlPlayerBumps(user.getId(), playerBumps + 1);
                } else {
                    event.getChannel().sendMessage("**Your account is not linked.**").queue();
                    event.getChannel().sendMessage("> Use the command `/discord link` in the minecraft server.").queue();
                }
            }
        }

        if (event.getMessage().getContentRaw().startsWith("/bumps")) {
            Integer playerBumps = bitsForBump.dataManager.getYamlPlayerBumps(user.getId());
            if (playerBumps == 1){
                textChannel.sendMessage("You have bumped the server **1** time.").queue();
            }else{
                textChannel.sendMessage("You have bumped the server **" + Integer.toString(playerBumps) + "** times.").queue();
            }



        }



    }


}
