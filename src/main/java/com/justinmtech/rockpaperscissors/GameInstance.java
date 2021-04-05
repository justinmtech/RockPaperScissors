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
    private int bet;

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
    private int scoreToWin;

    private SendMessage sendMessage;

    //Construct a game instance with Main class, the two players and a bet
    public GameInstance(RockPaperScissors plugin, Player player1, Player player2, int bet) {
        this.plugin = plugin;
        this.sendMessage = new SendMessage(plugin);
        over = false;
        this.bet = bet;
        this.player1 = player1;
        this.player2 = player2;
        scoreToWin = plugin.getConfig().getInt("game-settings.scoreToWin");
        if (collectBet()) {
            displayInstructions();
        }
    }

    //Show the players what to do after a game starts
    public void displayInstructions() {
        sendMessage.gameInstructions(player1, player2);
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
            sendMessage.matchTie(player1, player2);
        } else if (player1Input.equalsIgnoreCase("paper")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player1Score++;
                sendMessage.roundEnd(player1, player2);
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player2Score++;
                sendMessage.roundEnd(player2, player1);
            }
        } else if (player1Input.equalsIgnoreCase("rock")) {
            if (player2Input.equalsIgnoreCase("paper")) {
                player2Score++;
                sendMessage.roundEnd(player2, player1);
            } else if (player2Input.equalsIgnoreCase("scissors")) {
                player1Score++;
                sendMessage.roundEnd(player1, player2);
            }
        } else if (player1Input.equalsIgnoreCase("scissors")) {
            if (player2Input.equalsIgnoreCase("rock")) {
                player2Score++;
                sendMessage.roundEnd(player2, player1);
            } else if (player2Input.equalsIgnoreCase("paper")) {
                player1Score++;
                sendMessage.roundEnd(player1, player2);
            }
        }
        sendMessage.roundResults(player1, player2, player1Score, player2Score);

        //Reset player inputs
        resetInputs();

        //Check if game is over
        isGameOver();
    }

    //Game over sequence
    private void gameOver() {
        Player winner = null;
        Player loser = null;
        boolean gameCancelled = false;
        if (player1Score == 3) {
            sendMessage.matchResults(player1, player2);
            winner = player1;
            loser = player2;
        } else if (player2Score == 3) {
            sendMessage.matchResults(player2, player1);
            winner = player2;
            loser = player1;
        } else {
            gameCancelled = true;
            sendMessage.gameCancelled(player1, player2);
        }

        if (bet > 0 && !gameCancelled) {
            payBet(winner, loser);
        }

        if (bet > plugin.getConfig().getDouble("game-settings.broadcastMatchMinBet") && !gameCancelled) {
            sendMessage.matchBroadcast(player1, player2, bet);
        }
    }

    //Pay the bet to the winner (original bet x2)
    private void payBet(Player winner, Player loser) {
            int payout = bet * 2;
        if (bet > 0) {
            EconomyResponse r1 = plugin.getEcon().depositPlayer(winner, payout);

            winner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    String.format(plugin.getConfig().getString("messages.betWin"),
                            plugin.getEcon().format(r1.amount / 2))));

            loser.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.betLose")
                            .replace("%s", String.valueOf((int) bet))));
        }
    }

    //Take the agreed on bet from both players at start of match
    private boolean collectBet() {
        boolean r1Success = false;
        boolean r2Success = false;
        if (bet > 0) {
            EconomyResponse r1 = plugin.getEcon()
                    .withdrawPlayer(Bukkit.getOfflinePlayer(player1.getUniqueId()), bet);
            if (r1.transactionSuccess()) {
                r1Success = true;
            }
            EconomyResponse r2 = plugin.getEcon()
                    .withdrawPlayer(Bukkit.getOfflinePlayer(player2.getUniqueId()), bet);
            if (r2.transactionSuccess()) {
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
            sendMessage.betReturned(player1);
            plugin.getEcon().depositPlayer(Bukkit.getOfflinePlayer(player1.getUniqueId()), bet);
        }

        if (player2.getName() != player.getName()) {
            sendMessage.betReturned(player2);
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
