package com.justinmtech.rockpaperscissors;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private static RockPaperScissors plugin;
    private Player player;
    private Player player2;
    private String[] args;
    private final static int minBet = 100;

    public CommandHandler(RockPaperScissors plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        player = (Player) sender;
        this.args = args;

        if (command.getName().equalsIgnoreCase("rps")) {

            if (args.length == 2) {
                matchRequestWithBet();
            }

            else if (args.length == 1 && args[0].equalsIgnoreCase("accept")) {
                acceptMatch();
            }

            else if (args.length == 1) {
                matchRequest();
            }

            else {
                showHelp();
            }
        }

        else {
            if (plugin.getGame(player) != null) {
                if (command.getName().equalsIgnoreCase("rock")) {
                    plugin.getGame(player).setPlayerInput(player, "rock");
                    player.sendMessage(ChatColor.RED + "You chose rock!");
                }

                if (command.getName().equalsIgnoreCase("paper")) {
                    plugin.getGame(player).setPlayerInput(player, "paper");
                    player.sendMessage(ChatColor.RED + "You chose paper!");
                }

                if (command.getName().equalsIgnoreCase("scissors")) {
                    plugin.getGame(player).setPlayerInput(player, "scissors");
                    player.sendMessage(ChatColor.RED + "You chose scissors!");
                }
                if (plugin.getGame(player).haveBothPlayersChosenMove()) {
                    plugin.getGame(player).determineWinner();
                    plugin.getGame(player).resetInputs();
                    if (!plugin.getGame(player).isOver()) {
                        plugin.getGame(player).displayInstructions();
                    } else {
                        plugin.removeGame(player);
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "You are not in an rps match!");
            }
        }
        return true;
    }

    private void showHelp() {
        player.sendMessage(ChatColor.AQUA + "RPS Help");
        player.sendMessage(ChatColor.GRAY + "- /rps");
        player.sendMessage(ChatColor.GRAY + "- /rps <player> - challenger a player");
        player.sendMessage(ChatColor.GRAY + "- /rps <player> [bet]");
        player.sendMessage(ChatColor.GRAY + "- /rps accept - accept last challenge");
    }

    private void acceptMatch() {
        double bet = 0;
        try {
            player2 = player;
            player = plugin.getInvites().get(player2);
            bet = plugin.getBets().get(player);
            System.out.println("Accept match bet value: " + bet);
            sendMessageToBoth(ChatColor.GREEN + "Game starting!");
            createGameInstance(bet);
            plugin.removeInvite(player);

        } catch (NullPointerException e) {
            System.out.println("Accept match error: " + e.getMessage());
            player2.sendMessage(ChatColor.RED + "No one has challenged you yet!");
        }
    }

    private void matchRequest() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                player.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                player2.sendMessage(ChatColor.GOLD + player.getName() + " challenged you to an RPS match [BET: none]! Type /rps accept to accept.");
                plugin.addInvite(player2, player);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                player.sendMessage("Player not found!");
            }
        }

        private void matchRequestWithBet() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                if (Integer.parseInt(args[1]) >= minBet) {
                    double bet = Integer.parseInt(args[1]);
                    if (checkSufficientBalance(bet)) {
                        player.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                        player2.sendMessage(ChatColor.GOLD + player.getName() + " challenged you to an RPS match [BET: $" + bet + "]! Type /rps accept to accept.");
                        plugin.addInvite(player2, player);
                        plugin.addBet(player, bet);
                    } else {
                        player.sendMessage(ChatColor.RED + "You or the player challenged do not have enough for this bet!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "The minimum bet is " + minBet + "!");
                }
            } catch (Exception e) {
                player.sendMessage("Target player not found");
            }
        }

        private boolean checkSufficientBalance(double bet) {
            double balance1 = plugin.getEcon().getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()));
            double balance2 = plugin.getEcon().getBalance(Bukkit.getOfflinePlayer(player.getUniqueId()));
            if (balance1 < bet || balance2 < bet) {
                return false;
            } else {
                return true;
            }
        }

        private void createGameInstance(double bet) {
            plugin.addGame(new GameInstance(plugin, player, player2, bet));
        }

        private void sendMessageToBoth(String message) {
            player.sendMessage(message);
            player2.sendMessage(message);
        }
}
