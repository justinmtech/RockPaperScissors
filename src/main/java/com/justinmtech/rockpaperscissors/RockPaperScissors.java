package com.justinmtech.rockpaperscissors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RockPaperScissors extends JavaPlugin {
    private HashMap<Player, Player> invites = new HashMap<>();
    private HashMap<Player, Player> activeGames = new HashMap<>();

    @Override
    public void onEnable() {
        //Plugin startup logic
        Commands commands = new Commands(this);
        this.getCommand("rps").setExecutor(commands);
        this.getCommand("rock").setExecutor(commands);
        this.getCommand("paper").setExecutor(commands);
        this.getCommand("scissors").setExecutor(commands);

        System.out.println("RockPaperScissors enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RockPaperScissors disabled!");
    }

    public HashMap<Player, Player> getInvites() {
        return invites;
    }

    public HashMap<Player, Player> getActiveGames() {
        return activeGames;
    }

    public void setActiveGames(HashMap<Player, Player> activeGames) {
        this.activeGames = activeGames;
    }

    public void setInvites(HashMap<Player, Player> invites) {
        this.invites = invites;
    }

    public void addInvite(Player player2, Player player1) {
        invites.put(player2, player1);
    }

    public void removeInvite(Player invited) {
        invites.remove(invited);
    }


}
