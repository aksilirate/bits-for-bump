package com.elunar.bitsforbump;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DataManager {
    public BitsForBump bitsForBump;

    public DataManager(BitsForBump bitsForBump) {
        this.bitsForBump = bitsForBump;
    }


    public void setYamlPlayerBumps(String discordID, Integer playerBumps) {
        File file = new File(bitsForBump.getDataFolder() + "/player-data", discordID + ".yml");
        YamlConfiguration yaml_file = YamlConfiguration.loadConfiguration(file);

        yaml_file.set("player_bumps", playerBumps);

        saveYamlFile(file, yaml_file);
    }




    @SuppressWarnings("unused")
    public Integer getYamlPlayerBumps(String discordID) {
        File file = new File(bitsForBump.getDataFolder() + "/player-data", discordID + ".yml");
        YamlConfiguration yaml_file = YamlConfiguration.loadConfiguration(file);

        if (!yaml_file.contains("player_bumps")) {
            setYamlPlayerBumps(discordID, 0);
            return 0;
        } else {
            return (Integer) yaml_file.get("player_bumps");

        }

    }


    public void saveYamlFile(File file, YamlConfiguration yaml_file) {
        try {
            yaml_file.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
