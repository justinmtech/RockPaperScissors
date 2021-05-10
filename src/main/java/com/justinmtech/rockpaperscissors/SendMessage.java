package com.justinmtech.rockpaperscissors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendMessage {
    private static RockPaperScissors plugin;

    public SendMessage(RockPaperScissors plugin) {
        this.plugin = plugin;
    }

    public void choseRock(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.choseRock")));
    }

    public void choseScissors(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.choseScissors")));
    }

    public void chosePaper(Player player) {
        dividerLine(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.chosePaper")));
    }

    public void notInGame(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.notInGame")));
    }

    public void alreadyInGame(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.alreadyInGame")));
    }

    public void gameStarting(Player player, Player player2) {
        toBoth(player, player2, ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.gameStarting")));
    }

    public void noInvites(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.noInvites")));
    }

    public void challenge(Player player, Player player2, int bet) {
        //player.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.sendChallenge"))
                .replace("%p", player2.getName())
                .replace("%b", String.valueOf(bet)));

        //player2.sendMessage(ChatColor.GOLD + player.getName() + " challenged you to an RPS match [BET: $" + bet + "]! Type /rps accept to accept.");
        player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.receiveChallenge"))
                .replace("%p", player.getName())
                .replace("%b", String.valueOf(bet)));
    }

    public void insufficientFunds(Player player) {
        //player.sendMessage(ChatColor.RED + "You or the player challenged do not have enough for this bet!");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.insufficientFunds")));
    }

    public void minimumBet(Player player, int minBet) {
        //player.sendMessage(ChatColor.RED + "The minimum bet is " + minBet + "!");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.minimumBet"))
                .replace("%b", String.valueOf(minBet)));
    }

    public void playerNotFound(Player player) {
        //player.sendMessage("Player not found");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.playerNotFound")));
    }

    public void gameInstructions(Player player, Player player2) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.gameInstructions")));
        player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.gameInstructions")));
    }

    public void matchTie(Player player, Player player2) {
        toBoth(player, player2, plugin.getConfig().getString("messages.Tie"));
    }

    public void dividerLine(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.dividerLine")));
    }

    public void roundEnd(Player winner, Player loser) {
        dividerLine(winner);
        winner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundEndWin")));

        dividerLine(loser);
        loser.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundEndLose")));
    }

    public void roundResults(Player player, Player player2, int player1Score, int player2Score) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundResults"))
                .replace("%p", player.getName())
                .replace("%s", String.valueOf(player1Score)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundResults"))
                .replace("%p", player2.getName())
                .replace("%s", String.valueOf(player2Score)));
        dividerLine(player);

        player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundResults"))
                .replace("%p", player2.getName())
                .replace("%s", String.valueOf(player2Score)));
        player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.roundResults"))
                .replace("%p", player.getName())
                .replace("%s", String.valueOf(player1Score)));
        dividerLine(player2);
    }

    public void matchResults(Player winner, Player loser, int bet) {
        if (bet == 0) {
            winner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.matchResultsWin")));
            dividerLine(winner);
        }

        if (bet > 0) {
            winner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.matchResultsWinWithBet")).replace("%b", String.valueOf(bet)));
            dividerLine(winner);
        }

        if (bet == 0) {
            loser.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.matchResultsLose")));
            dividerLine(loser);
        }

        if (bet > 0) {
            loser.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.matchResultsLoseWithBet")).replace("%b", String.valueOf(bet)));
            dividerLine(loser);
        }
    }

    public void gameCancelled(Player player, Player player2) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.gameCancelled")));
        player2.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.gameCancelled")));
    }

    public void betReturned(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.betReturned")));
    }

    public void help(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.help.line-1")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.help.line-2")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.help.line-3")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.help.line-4")));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.help.line-5")));
    }

    public void matchBroadcast(Player winner, Player loser, int bet) {
        String betString = String.valueOf(bet);
        Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.matchBroadcast")
                .replace("%w", winner.getName())
                .replace("%l", loser.getName())
                .replace("%b", betString)));
    }

    public void toBoth(Player player, Player player2, String message) {
        player.sendMessage(message);
        player2.sendMessage(message);
    }

    public void inviteExists(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.inviteExists")));
    }

    public void noPlayerEntered(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.noPlayerEntered")));
    }
}
