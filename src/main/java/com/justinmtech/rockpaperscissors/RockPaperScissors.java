package com.justinmtech.rockpaperscissors;

import com.justinmtech.rockpaperscissors.game.Invites;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RockPaperScissors extends JavaPlugin {
    private HashMap<Player, Player> invites = new HashMap<>();

    public HashMap<Player, Player> getActiveGames() {
        return activeGames;
    }

    public void setActiveGames(HashMap<Player, Player> activeGames) {
        this.activeGames = activeGames;
    }

    private HashMap<Player, Player> activeGames = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public HashMap<Player, Player> getInvites() {
        return invites;
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
