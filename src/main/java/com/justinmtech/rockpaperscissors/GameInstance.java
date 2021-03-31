package com.justinmtech.rockpaperscissors;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

//The logic for an RPS match
public class GameInstance {
    //Is the match over?
    private boolean over;

    //Bet amount (0 if none)
    private double bet;

    //Player 1
    private Player player1;
    private String player1Input;
    private int player1Score;

    //Player 2
    private Player player2;
    private String player2Input;
    private int player2Score;

    //Main class reference
    private static RockPaperScissors plugin;

    //What score should the game end at?
    //One round = One win
    private static int scoreToWin = 3;

    //Construct a game instance with Main class, the two players and a bet
    public GameInstance(RockPaperScissors plugin, Player player1, Player player2, double bet) {
        this.plugin = plugin;
        over = false;
        this.bet = bet;
        this.player1 = player1;
        this.player2 = player2;
        if (collectBet()) {
            displayInstructions();
        }
    }

    //Show the players what to do after a game starts
    public void displayInstructions() {
        player1.sendMessage(ChatColor.GOLD + "Type /rock, /paper or /scissors!");
        player2.sendMessage(ChatColor.GOLD + "Type /rock, /paper or /scissors!");
    }

    //Check if the game is over yet
    public void isGameOver() {
        if (player1Score == scoreToWin || player2Score == scoreToWin) {
            over = true;
            gameOver();
        }
    }

    //Used for resetting inputs after each round
    public void resetInputs() {
        player1Input = null;
        player2Input = null;
    }

    //Determine round winner and increment scores
    public void determineWinner() {
        if (player1Input == player2Input) {
            player1.sendMessage(ChatColor.YELLOW + "Tie!");
            player2.sendMessage(ChatColor.YELLOW + "Tie!");
        } else if (player1Input.equalsIgnoreCase("paper")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player1Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player1.sendMessage(ChatColor.GREEN + "Round win!");
                player2.sendMessage(ChatColor.RED + "Round lose!");
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player2Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GREEN + "Round win!");
                player1.sendMessage(ChatColor.RED + "Round lose!");
            }
        } else if (player1Input.equalsIgnoreCase("rock")) {
            if (player2Input.equalsIgnoreCase("paper")) {
                player2Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GREEN + "Round win!");
                player1.sendMessage(ChatColor.RED + "Round lose!");
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player1Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player1.sendMessage(ChatColor.GREEN + "Round win!");
                player2.sendMessage(ChatColor.RED + "Round lose!");
            }
        } else if (player1Input.equalsIgnoreCase("scissors")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player2Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GREEN + "Round win!");
                player1.sendMessage(ChatColor.RED + "Round lose!");
            } else if (player2Input.equalsIgnoreCase("paper")) {
                player1Score++;
                player1.sendMessage(ChatColor.GRAY + "-----------------------------");
                player2.sendMessage(ChatColor.GRAY + "-----------------------------");
                player1.sendMessage(ChatColor.GREEN + "Round win!");
                player2.sendMessage(ChatColor.RED + "Round lose!");
            }
        }
        this.player1.sendMessage(ChatColor.AQUA + this.player1.getName() + ": " + player1Score);
        this.player1.sendMessage(ChatColor.AQUA + this.player2.getName() + ": " + player2Score);
        player1.sendMessage(ChatColor.GRAY + "-----------------------------");
        this.player2.sendMessage(ChatColor.AQUA + this.player2.getName() + ": " + player2Score);
        this.player2.sendMessage(ChatColor.AQUA + this.player1.getName() + ": " + player1Score);
        player2.sendMessage(ChatColor.GRAY + "-----------------------------");

        resetInputs();
        isGameOver();
    }

    //Game over sequence
    private void gameOver() {
        Player winner = null;
        boolean gameCancelled = false;
        if (player1Score == 3) {
            player1.sendMessage(ChatColor.GREEN + "You won!");
            player1.sendMessage(ChatColor.GRAY + "-----------------------------");
            player2.sendMessage(ChatColor.RED + "You lost!");
            player2.sendMessage(ChatColor.GRAY + "-----------------------------");
            winner = player1;
        } else if (player2Score == 3) {
            player1.sendMessage(ChatColor.RED + "You lost!");
            player1.sendMessage(ChatColor.GRAY + "-----------------------------");
            player2.sendMessage(ChatColor.GREEN + "You won!");
            player2.sendMessage(ChatColor.GRAY + "-----------------------------");
            winner = player2;
        } else {
            gameCancelled = true;
            player1.sendMessage(ChatColor.RED + "Game cancelled!");
            player2.sendMessage(ChatColor.RED + "Game cancelled!");
        }
        if (bet > 0 && !gameCancelled) {
            payBet(winner);
        }
    }

    //Pay the bet to the winner (original bet x2)
    private void payBet(Player winner) {
        if (bet > 0) {
            double payout = bet * 2;
            EconomyResponse r1 = plugin.getEcon().depositPlayer(winner, payout);
            winner.sendMessage(String.format("%s was added to your account for winning the match!", plugin.getEcon().format(r1.amount)));
        }
    }

    //Take the agreed on bet from both players at start of match
    private boolean collectBet() {
        boolean r1Success = false;
        boolean r2Success = false;
        if (bet > 0) {
            EconomyResponse r1 = plugin.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player1.getUniqueId()), bet);
            if (r1.transactionSuccess()) {
            player1.sendMessage(String.format("%s was taken from your account!", plugin.getEcon().format(r1.amount)));
            r1Success = true;
            }
            EconomyResponse r2 = plugin.getEcon().withdrawPlayer(Bukkit.getOfflinePlayer(player2.getUniqueId()), bet);
            if (r2.transactionSuccess()) {
            player2.sendMessage(String.format("%s was taken from your account!", plugin.getEcon().format(r2.amount)));
            r2Success = true;
            }
        } else {
            return false;
        }
        return (r1Success && r2Success);
    }

    //Return bet to the player that is not in the parameter
    public void returnBet(Player player) {
        if (player1.getName() != player.getName()) {
            player1.sendMessage(ChatColor.RED + "Your bet was returned due to the game being interrupted.");
            plugin.getEcon().depositPlayer(Bukkit.getOfflinePlayer(player1.getUniqueId()), bet);
        }

        if (player2.getName() != player.getName()) {
            player2.sendMessage(ChatColor.RED + "Your bet was returned due to the game being interrupted.");
            plugin.getEcon().depositPlayer(Bukkit.getOfflinePlayer(player2.getUniqueId()), bet);
        }
    }

    //Is the game over yet?
    public boolean isOver() {
        return over;
    }

    //Get the 1st player
    public Player getPlayer1() {
        return player1;
    }

    //Get the 2nd player
    public Player getPlayer2() {
        return player2;
    }

    //Set a player input based on parameters
    public void setPlayerInput(Player player, String input) {
        if (player.getName() == player1.getName()) {
            setPlayer1Input(input);
        } else {
            setPlayer2Input(input);
        }
    }

    //Set player 1's input specifically
    public void setPlayer1Input(String player1Input) {
        this.player1Input = player1Input;
    }

    //Set player 2's input specifically
    public void setPlayer2Input(String player2Input) {
        this.player2Input = player2Input;
    }

    //Have both players chosen rock, paper or scissors?
    public boolean haveBothPlayersChosenMove() {
        if (player1Input != null && player2Input != null) {
            return true;
        } else
            return false;
    }
}
