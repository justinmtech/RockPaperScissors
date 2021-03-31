package com.justinmtech.rockpaperscissors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

//Listens for player quits
//Used for cancelling games and returning bets if one players logs off during a game.
//The player that logs out does not get their bet back to discourage cheating.
public class PlayerQuitListener implements Listener {
    private static RockPaperScissors plugin;

    public PlayerQuitListener(RockPaperScissors plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        //Check if player that logged out is in an RPS match
        if (plugin.getGame(event.getPlayer()) != null) {
            //If the player is in a match, execute returnBet() with the player that logged out as a parameter.
            //The parameter is to prevent the logged out player from receiving their bet back.
            plugin.getGame(event.getPlayer()).returnBet(event.getPlayer());
        }
    }
}
