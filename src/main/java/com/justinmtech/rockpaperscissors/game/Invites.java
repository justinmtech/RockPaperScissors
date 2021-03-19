package com.justinmtech.rockpaperscissors.game;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Invites {
    //Inviter, invited
    private HashMap<Player, Player> pendingMatches;

    public void addPendingMatch(Player invited, Player inviter) {
        pendingMatches.put(invited, inviter);
    }

    public void removePendingMatch(Player invited) {
        pendingMatches.remove(invited);
    }


    public HashMap<Player, Player> getPendingMatches() {
        return pendingMatches;
    }

    public void setPendingMatches(HashMap<Player, Player> pendingMatches) {
        this.pendingMatches = pendingMatches;
    }





}

