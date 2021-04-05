package com.justinmtech.rockpaperscissors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private static RockPaperScissors plugin;
    private SendMessage sendMessage;
    private Player player;
    private Player player2;
    private String[] args;
    private final int minBet;

    public CommandHandler(RockPaperScissors plugin) {
        this.plugin = plugin;
        this.sendMessage = new SendMessage(plugin);
        this.minBet = plugin.getConfig().getInt("game-settings.minBet");
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
                    //SendMessage.choseRock(player);
                    sendMessage.choseRock(player);
                }

                if (command.getName().equalsIgnoreCase("paper")) {
                    plugin.getGame(player).setPlayerInput(player, "paper");
                    //SendMessage.chosePaper(player);
                    sendMessage.chosePaper(player);
                }

                if (command.getName().equalsIgnoreCase("scissors")) {
                    plugin.getGame(player).setPlayerInput(player, "scissors");
                    //SendMessage.choseScissors(player);
                    sendMessage.choseScissors(player);
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
                sendMessage.notInGame(player);
            }
        }
        return true;
    }

    private void showHelp() {
        sendMessage.help(player);
    }

    private void acceptMatch() {
        int bet = 0;
        try {
            player2 = player;
            player = plugin.getInvites().get(player2);
            bet = plugin.getBets().get(player);
            sendMessage.gameStarting(player, player2);
            createGameInstance(bet);
            plugin.removeInvite(player);

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            sendMessage.noInvites(player2);
        }
    }

    private void matchRequest() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                sendMessage.challenge(player, player2, 0);
                plugin.addInvite(player2, player);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                sendMessage.playerNotFound(player);
            }
        }

        private void matchRequestWithBet() {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                if (Integer.parseInt(args[1]) >= minBet) {
                    int bet = Integer.parseInt(args[1]);
                    if (checkSufficientBalance(bet)) {
                        sendMessage.challenge(player, player2, bet);
                        plugin.addInvite(player2, player);
                        plugin.addBet(player, bet);
                    } else {
                        sendMessage.insufficientFunds(player);
                    }
                } else {
                    sendMessage.minimumBet(player, minBet);
                }
            } catch (Exception e) {
                sendMessage.playerNotFound(player);
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

        private void createGameInstance(int bet) {
            plugin.addGame(new GameInstance(plugin, player, player2, bet));
        }
}
