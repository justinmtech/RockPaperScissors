package com.justinmtech.rockpaperscissors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Game {
    private int rounds;
    private boolean over;
    private boolean hasBet;
    private double bet;
    private Player challenger;

    public String getChallengerInput() {
        return challengerInput;
    }

    public void setChallengerInput(String challengerInput) {
        this.challengerInput = challengerInput;
    }

    public String getOpponentInput() {
        return opponentInput;
    }

    public void setOpponentInput(String opponentInput) {
        this.opponentInput = opponentInput;
    }

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
        startGame();
    }

    public Game(Player challenger, Player opponent) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.challenger = challenger;
        this.opponent = opponent;
        startGame();
    }

    public Game(Player challenger, double bet) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.challenger = challenger;
        startGame();
    }

    public Game(Player challenger) {
        rounds = 0;
        over = false;
        this.challenger = challenger;
        startGame();
    }

    public void startGame() {
        //while (!over) {
            challenger.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
            opponent.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
            determineWinner();
            checkIfOver();
        //}
        gameOver();
    }

    public void checkIfOver() {
        if (challengerScore == 3 || opponenntScore == 3) {
            over = true;
        }
    }

    private void resetInputs() {
        challengerInput = null;
        opponentInput = null;
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
        challenger.sendMessage(challenger.getName() + ": " + challengerScore);
        opponent.sendMessage(opponent.getName() + ": " + opponenntScore);
        resetInputs();
    }

    private void gameOver() {
        if (challengerScore == 3) {
            challenger.sendMessage("You won!");
            opponent.sendMessage("You lost!");
        } else {
            challenger.sendMessage("You lost!");
            opponent.sendMessage("You won!");

        }
    }

    private void payBet(Player winner) {
        if (bet > 0) {
            double payout = bet * 2;
            winner.sendMessage("You won " + payout + "!");
            //TODO add vault dependency and give money to winner
        }
    }

    private void collectBet() {
        if (bet > 0) {
            //deduct bet amount from challenger
            //deduct bet amount fro opponent
        }
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
