package com.justinmtech.rockpaperscissors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameInstance {
    private int rounds;
    private boolean over;
    private boolean hasBet;
    private double bet;
    private Player player1;
    private String player1Input;
    private int player1Score;
    private Player player2;
    private String player2Input;
    private int player2Score;
    //private static RockPaperScissors plugin;

    public GameInstance(Player player1, Player player2, double bet) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.player1 = player1;
        this.player2 = player2;
        startGame();
    }

    public GameInstance(Player player1, Player player2) {
        rounds = 0;
        over = false;
        this.bet = bet;
        this.player1 = player1;
        this.player2 = player2;
        startGame();
    }

    public void startGame() {
        player1.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
        player2.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
    }

    public void startRound() {
        player1.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
        player2.sendMessage(ChatColor.GREEN + "Type /rock /paper or /scissors!");
    }

    public void isGameOver() {
        if (player1Score == 3 || player2Score == 3) {
            over = true;
            gameOver();
        }
    }

    private void resetInputs() {
        player1Input = null;
        player2Input = null;
    }

    public void determineWinner() {
        if (player1Input == player2Input) {
            player1.sendMessage(ChatColor.YELLOW + "Tie!");
            player2.sendMessage(ChatColor.YELLOW + "Tie!");
        } else if (player1Input.equalsIgnoreCase("paper")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player1Score++;
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player2Score++;
            }
        } else if (player1Input.equalsIgnoreCase("rock")) {
            if (player2Input.equalsIgnoreCase("paper")) {
                player2Score++;
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player1Score++;
            }
        } else if (player1Input.equalsIgnoreCase("scissors")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player2Score++;
            } else if (player2Input.equalsIgnoreCase("paper")) {
                player1Score++;
            }
        }
        this.player1.sendMessage(ChatColor.AQUA + this.player1.getName() + ": " + player1Score);
        this.player2.sendMessage(ChatColor.AQUA + this.player2.getName() + ": " + player2Score);
        resetInputs();
        isGameOver();
    }

    private void gameOver() {
        if (player1Score == 3) {
            player1.sendMessage(ChatColor.GREEN + "You won!");
            player2.sendMessage(ChatColor.RED + "You lost!");
        } else {
            player1.sendMessage(ChatColor.RED + "You lost!");
            player2.sendMessage(ChatColor.GREEN + "You won!");

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

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getPlayer1Input() {
        return player1Input;
    }

    public void setPlayerInput(Player player, String input) {
        if (player.getName() == player1.getName()) {
            setPlayer1Input(input);
        } else {
            setPlayer2Input(input);
        }
    }

    public void setPlayer1Input(String player1Input) {
        this.player1Input = player1Input;
    }

    public String getPlayer2Input() {
        return player2Input;
    }

    public void setPlayer2Input(String player2Input) {
        this.player2Input = player2Input;
    }

    private boolean haveBothPlayersChosenMove() {
        if (player1Input != null && player2Input != null) {
            return true;
        } else
            return false;
    }
}
