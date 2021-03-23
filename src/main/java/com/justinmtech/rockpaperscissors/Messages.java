package com.justinmtech.rockpaperscissors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

//Plugin messages
public class Messages {
    private Player opponent;
    private Player challenger;

    public Messages(Player challenger, Player opponent) {
        this.challenger = challenger;
        this.opponent = opponent;
    }


    public void gameRequest() {
        //Send alert request to opponent and challenger
        challenger.sendMessage("RPS match request sent to " + opponent.getName());
        opponent.sendMessage(challenger.getName() + " challenged you to an RPS match! Type /rps accept to accept the match");
    }

    public void gameStarted() {
        //Send alert to players
        sendBoth("The RPS match has begun.. Type /rock, /paper or /scissors!");
    }

    public void gameEnd() {
        challenger.sendMessage("");
    }

    private void sendBoth(String message) {
        challenger.sendMessage(message);
        opponent.sendMessage(message);
    }


    public Player getChallenger() {
        return challenger;
    }

    public void setChallenger(Player challenger) {
        this.challenger = challenger;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
}
