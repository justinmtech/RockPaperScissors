package com.justinmtech.rockpaperscissors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private static RockPaperScissors plugin;
    private Player player1;
    private Player player2;
    private Player sender;
    private String[] args;
    private Command command;

    public Commands(RockPaperScissors plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.sender = (Player) sender;
        this.args = args;
        this.command = command;
        player1 = (Player) sender;

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
            if (command.getName().equalsIgnoreCase("rock")) {
                if (plugin.getGame(player1) != null) {
                    plugin.getGame(player1).setPlayerInput(player1, "rock");
                } else {
                    player1.sendMessage(ChatColor.RED + "You are not in a game!");
                }
            }

                if (command.getName().equalsIgnoreCase("paper")) {
                    if (plugin.getGame(player1) != null) {
                        plugin.getGame(player1).setPlayerInput(player1, "paper");

                    } else {
                        player1.sendMessage(ChatColor.RED + "You are not in a game!");
                    }
                }

                if (command.getName().equalsIgnoreCase("scissors")) {
                    if (plugin.getGame(player1) != null) {
                        plugin.getGame(player1).setPlayerInput(player1, "paper");
                    } else {
                        player1.sendMessage(ChatColor.RED + "You are not in a game!");
                    }
                }
            if (haveBothPlayersChosenMove()) {
                plugin.getGame(player1).determineWinner();
                plugin.resetInputs(player1);
                plugin.getGame(player1).startRound();
            } else {
                player1.sendMessage("Only one player has made their move!");
            }
        }
        return true;
    }

    private void showHelp() {
        player1.sendMessage(ChatColor.AQUA + "RPS Help");
        player1.sendMessage(ChatColor.GRAY + "- /rps");
        player1.sendMessage(ChatColor.GRAY + "- /rps <player> - challenger a player");
        player1.sendMessage(ChatColor.GRAY + "- /rps <player> [bet]");
        player1.sendMessage(ChatColor.GRAY + "- /rps accept - accept last challenge");
    }

    private void acceptMatch() {
        player2 = sender;
        System.out.println("Player 2 name: " + player2.getName());
        try {
            player1 = plugin.getInvites().get(player2);
            sendMessageToBoth(ChatColor.GREEN + "Game starting!");
            createGameInstance();
            plugin.removeInvite(player2);
        } catch (NullPointerException e) {
            System.out.println(e.getCause());
            this.sender.sendMessage(ChatColor.RED + "No one has challenged you yet!");
        }
    }

        private void matchRequest() {
            player1 = sender;
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                player1.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                player2.sendMessage(ChatColor.GOLD + player1.getName() + " challenged you to an RPS match [BET: none]! Type /rps accept to accept.");
                plugin.addInvite(player2, player1);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                this.sender.sendMessage("Player not found!");
            }
        }

        private void matchRequestWithBet() {
            player1 = sender;
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                int bet = Integer.parseInt(args[1]);
                player1.sendMessage(ChatColor.GOLD + "You challenged " + player2.getName() + " to an RPS match!");
                player2.sendMessage(ChatColor.GOLD + player1.getName() + " challenged you to an RPS match [BET: $" + bet + "]! Type /rps accept to accept.");
                plugin.addInvite(player2, player1);
            } catch (Exception e) {
                this.sender.sendMessage("Target player not found");
            }
        }

        private void createGameInstance() {
            plugin.addGame(new GameInstance(player1, player2));
        }

        private void sendMessageToBoth(String message) {
            player1.sendMessage(message);
            player2.sendMessage(message);
        }

    private boolean haveBothPlayersChosenMove() {
        if (plugin.getGame(player1).getPlayer1Input() != null && plugin.getGame(player2).getPlayer2Input() != null) {
            return true;
        } else
            return false;
    }

    private void rockPaperOrScissors() {
        if (command.getName().equalsIgnoreCase("rock")) {
            if (sender.getName().equals(player1.getName())) {
                plugin.getGame(player1).setPlayer1Input("rock");
            } else {
                plugin.getGame(player1).setPlayer2Input("rock");
            }

            if (command.getName().equalsIgnoreCase("paper")) {
                if (sender.getName().equals(player1.getName())) {
                    plugin.getGame(player1).setPlayer1Input("paper");

                } else {
                    plugin.getGame(player1).setPlayer2Input("paper");

                }
            }

            if (command.getName().equalsIgnoreCase("scissors")) {
                if (sender.getName().equals(player1.getName())) {
                    plugin.getGame(player1).setPlayer1Input("scissors");
                } else {
                    plugin.getGame(player1).setPlayer2Input("scissors");

                }
            }
        }
            if (haveBothPlayersChosenMove()) {
                plugin.getGame(player1).determineWinner();
                resetInputs();
                plugin.resetInputs(player1);
                plugin.getGame(player1).startRound();
            }
        }

    private void resetInputs() {
        plugin.resetInputs(player1);
    }
}
