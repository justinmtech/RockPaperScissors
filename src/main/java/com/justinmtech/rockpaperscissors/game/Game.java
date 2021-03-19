package com.justinmtech.rockpaperscissors.game;

import org.bukkit.entity.Player;

import java.util.EventListener;

public class Game {
    private int rounds;
    private boolean over;
    private boolean hasBet;
    private double bet;
    private Player challenger;
    private String challengerInput;
    private int challengerScore;
    private Player opponent;
    private String opponentInput;
    private int opponenntScore;

    public Game(Player challenger, Player opponent, double bet) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.challenger = challenger;
        this.opponent = opponent;
    }

    public Game(Player challenger, Player opponent) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.challenger = challenger;
        this.opponent = opponent;
    }

    public Game(Player challenger, double bet) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.challenger = challenger;
    }

    public Game(Player challenger) {
        rounds = 0;
        over = false;
        this.challenger = challenger;
    }

    public void startGame() {
        while (!over) {
            //listen

        }
    }

    private void determineWinner() {
        if (challengerInput == opponentInput) {
            challenger.sendMessage("Tie!");
            opponent.sendMessage("Tie!");
        } else if (challengerInput.equalsIgnoreCase("paper")) {
                if (opponentInput.equalsIgnoreCase("rock")) {
                    challengerScore++;
                } else if (opponentInput.equalsIgnoreCase("scissors")) {
                    opponenntScore++;
                }
        } else if (challengerInput.equalsIgnoreCase("rock")) {
            if (opponentInput.equalsIgnoreCase("paper")) {
                opponenntScore++;
            } else if (opponentInput.equalsIgnoreCase("scissors")) {
                challengerScore++;
            }
        } else if (challengerInput.equalsIgnoreCase("scissors")) {
            if (opponentInput.equalsIgnoreCase("rock")) {
                opponenntScore++;
            } else if (opponentInput.equalsIgnoreCase("paper")) {
                challengerScore++;
            }
        }
    }

    private void endGame() {

    }

    private void listenCommands() {
        //listen to commands

    }


    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isHasBet() {
        return hasBet;
    }

    public void setHasBet(boolean hasBet) {
        this.hasBet = hasBet;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
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