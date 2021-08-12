package com.elunar.bitsforbump.tasks;

import com.elunar.bitsforbump.BitsForBump;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class UpdateLeaderboard extends BukkitRunnable {

    private final BitsForBump bitsForBump;
    Integer leaderboardSize;

    public UpdateLeaderboard(BitsForBump bitsForBump) {
        this.bitsForBump = bitsForBump;
        this.leaderboardSize = bitsForBump.config.getInt("leaderboard-size");
    }



    HashMap<String, Integer> playerBumpsMap = new HashMap<String, Integer>();


    public void run() {
        Guild guild = DiscordSRV.getPlugin().getMainGuild();
        List<Member> members = guild.getMembers();


        for (Member member : members) {

            Integer totalBumps = bitsForBump.dataManager.getYamlPlayerBumps(member.getId());

            playerBumpsMap.put(member.getId(), totalBumps);


        }


        Object[] value = playerBumpsMap.entrySet().toArray();
        Arrays.sort(value, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });


        Integer leaderboardCounter = leaderboardSize;

        String message = "**Bumps Leaderboard**\n";

        for (Object sortedBumps : value) {

            if (leaderboardCounter <= 0) {
                break;
            }

            int place = 10 - leaderboardSize;

            String discordUID = ((Map.Entry<String, Integer>) sortedBumps).getKey();
            String discordMention = "<@" + discordUID + ">";
            Integer bumps = ((Map.Entry<String, Integer>) sortedBumps).getValue();



            message = message + "> `" + place + ".'" + discordMention + "`: " + Integer.toString(bumps) + " bumps`\n";

            leaderboardCounter -= 1;


        }


        TextChannel playtimeChannel = guild.getTextChannelById(bitsForBump.config.getString("leaderboard-channel-id"));


        Integer historySize = playtimeChannel.getHistory().retrievePast(1).complete().size();


        if (historySize == 0) {
            playtimeChannel.sendMessage(message).queue();
        } else {

            String finalMessage = message;
            playtimeChannel.getHistory().retrievePast(1).queue(
                    messages -> messages.get(0).editMessage(finalMessage).queue()
            );

        }


    }




}
