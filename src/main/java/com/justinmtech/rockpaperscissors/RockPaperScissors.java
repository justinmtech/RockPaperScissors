package com.justinmtech.rockpaperscissors;

import com.justinmtech.rockpaperscissors.commands.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RockPaperScissors extends JavaPlugin {
    private HashMap<Player, Player> invites;
    private HashMap<Player, Player> activeGames;



    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("rps").setExecutor(new RPSCommand());
        this.getCommand("rps accept").setExecutor(new AcceptDuelCommand());
        this.getCommand("rock").setExecutor(new RockCommand());
        this.getCommand("paper").setExecutor(new PaperCommand());
        this.getCommand("scissors").setExecutor(new ScissorsCommand());
        invites = new HashMap<>();
        activeGames = new HashMap<>();

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

    public void addInvite(Player invited, Player inviter) {
        invites.put(invited, inviter);
    }

    public void removeInvite(Player invited) {
        invites.remove(invited);
    }


}
