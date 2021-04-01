package com.justinmtech.rockpaperscissors;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendMessage {

    public static void choseRock(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.RED + "You chose rock!");
    }

    public static void choseScissors(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.RED + "You chose scissors!");

    }

    public static void chosePaper(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.RED + "You chose paper!");

    }

    public static void notInGame(Player player) {
        player.sendMessage(ChatColor.RED + "You are not in an rps match!");

    }

    public static void gameStarting(Player player, Player player2) {
        toBoth(player, player2, ChatColor.GREEN + "Game starting!");
    }

    public static void noInvites(Player player) {
        player.sendMessage(ChatColor.RED + "No one has challenged you yet!");

    }

    public static void challenge(Player player, Player player2, double bet) {
        player.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
        player2.sendMessage(ChatColor.GOLD + player.getName() + " challenged you to an RPS match [BET: $" + bet + "]! Type /rps accept to accept.");

    }

    public static void insufficientFunds(Player player) {
        player.sendMessage(ChatColor.RED + "You or the player challenged do not have enough for this bet!");

    }

    public static void minimumBet(Player player, double minBet) {
        player.sendMessage(ChatColor.RED + "The minimum bet is " + minBet + "!");

    }

    public static void playerNotFound(Player player) {
        player.sendMessage("Player not found");

    }

    public static void gameInstructions(Player player, Player player2) {
        player.sendMessage(ChatColor.GOLD + "Type /rock, /paper or /scissors!");
        player2.sendMessage(ChatColor.GOLD + "Type /rock, /paper or /scissors!");
    }

    public static void matchTie(Player player, Player player2) {
        toBoth(player, player2,ChatColor.YELLOW + "Tie!");
    }

    public static void dividerLine(Player player) {
        player.sendMessage(ChatColor.GRAY + "-----------------------------");
    }

    public static void roundEnd(Player winner, Player loser) {
        dividerLine(winner);
        winner.sendMessage(ChatColor.GREEN + "Round win!");
        dividerLine(loser);
        loser.sendMessage(ChatColor.RED + "Round lose!");
    }

    public static void roundResults(Player player, Player player2, int player1Score, int player2Score) {
        player.sendMessage(ChatColor.AQUA + player.getName() + ": " + player1Score);
        player.sendMessage(ChatColor.AQUA + player2.getName() + ": " + player2Score);
        dividerLine(player);
        player2.sendMessage(ChatColor.AQUA + player2.getName() + ": " + player2Score);
        player2.sendMessage(ChatColor.AQUA + player.getName() + ": " + player1Score);
        dividerLine(player2);
    }

    public static void matchResults(Player winner, Player loser) {
        winner.sendMessage(ChatColor.GREEN + "You won!");
        dividerLine(winner);
        loser.sendMessage(ChatColor.RED + "You lost!");
        dividerLine(loser);

    }

    public static void gameCancelled(Player player, Player player2) {
        player.sendMessage(ChatColor.RED + "Game cancelled!");
        player2.sendMessage(ChatColor.RED + "Game cancelled!");
    }

    public static void betReturned(Player player) {
        player.sendMessage(ChatColor.RED + "Your bet was returned due to the game being interrupted.");

    }

    public static void help(Player player) {
        player.sendMessage(ChatColor.AQUA + "RPS Help");
        player.sendMessage(ChatColor.GRAY + "- /rps");
        player.sendMessage(ChatColor.GRAY + "- /rps <player> - challenger a player");
        player.sendMessage(ChatColor.GRAY + "- /rps <player> [bet]");
        player.sendMessage(ChatColor.GRAY + "- /rps accept - accept last challenge");
    }

    public static void toBoth(Player player, Player player2, String message) {
        player.sendMessage(message);
        player2.sendMessage(message);
    }

}
