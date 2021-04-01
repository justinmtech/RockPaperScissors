package com.justinmtech.rockpaperscissors;

import org.bukkit.Bukkit;
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
                    SendMessage.choseRock(player);
                }

                if (command.getName().equalsIgnoreCase("paper")) {
                    plugin.getGame(player).setPlayerInput(player, "paper");
                    SendMessage.chosePaper(player);
                }

                if (command.getName().equalsIgnoreCase("scissors")) {
                    plugin.getGame(player).setPlayerInput(player, "scissors");
                    SendMessage.choseScissors(player);
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
                SendMessage.notInGame(player);
            }
        }
        return true;
    }

    private void showHelp() {
        SendMessage.help(player);
    }

    private void acceptMatch() {
        double bet = 0;
        try {
            player2 = player;
            player = plugin.getInvites().get(player2);
            bet = plugin.getBets().get(player);
            SendMessage.gameStarting(player, player2);
            createGameInstance(bet);
            plugin.removeInvite(player);

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            SendMessage.noInvites(player2);
        }
    }

    private void matchRequest() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                SendMessage.challenge(player, player2, 0);
                plugin.addInvite(player2, player);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                SendMessage.playerNotFound(player);
            }
        }

        private void matchRequestWithBet() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                if (Integer.parseInt(args[1]) >= minBet) {
                    double bet = Integer.parseInt(args[1]);
                    if (checkSufficientBalance(bet)) {
                        SendMessage.challenge(player, player2, bet);
                        plugin.addInvite(player2, player);
                        plugin.addBet(player, bet);
                    } else {
                        SendMessage.insufficientFunds(player);
                    }
                } else {
                    SendMessage.minimumBet(player, minBet);
                }
            } catch (Exception e) {
                SendMessage.playerNotFound(player);
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
}
