package com.justinmtech.rockpaperscissors;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

//Listens for command inputs and executes them
public class CommandHandler implements CommandExecutor {
    private static RockPaperScissors plugin;
    private SendMessage sendMessage;
    //Challenger or command sender
    private Player player;
    //Opponent or player invited
    private Player player2;
    private String[] args;
    //Uses game-settings.minBet in config
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

            if (args.length == 2 && !args[0].equalsIgnoreCase("accept")) {
                matchRequestWithBet();
            }

            else if (args.length >= 1 && args[0].equalsIgnoreCase("accept")) {
                acceptMatch();
            }

            else if (args.length == 1 && args[0].equalsIgnoreCase("deny")) {
                denyLastRequest();
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
                    sendMessage.choseRock(player);
                }

                if (command.getName().equalsIgnoreCase("paper")) {
                    plugin.getGame(player).setPlayerInput(player, "paper");
                    sendMessage.chosePaper(player);
                }

                if (command.getName().equalsIgnoreCase("scissors")) {
                    plugin.getGame(player).setPlayerInput(player, "scissors");
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

    //Executed on /rps accept [player]
    private void acceptMatch() {
        //Define null bet integer
        int bet;
        try {
            //Set the opponent (player invited)
            player2 = player;

            //Set player from args
            Player inputPlayer = Bukkit.getServer().getPlayer(args[1]);

            //Get invite list of player2
            ArrayList invitesSentToPlayer2 = plugin.getInvites().get(player2);

            //Check if the command sender has any invites from the player entered in the command
            if (!plugin.checkIfInGame(player) && plugin.checkIfInvited(inputPlayer, player)) {

                //Get challenger based on the inputPlayer variable (rps /accept [player])
                Player challenger = (Player) invitesSentToPlayer2.stream().filter(p -> p.equals(inputPlayer)).findFirst().get();

                //Set the challenger (inviter)
                player = challenger;

                try {
                    //Get bet set by challenger
                    bet = plugin.getBets().get(player);
                } catch (NullPointerException e) {
                    //Set bet to 0 if no bet found
                    bet = 0;
                }
                //If /rps accept <player> equals inviter's username
                if (args[1].equalsIgnoreCase(player.getName())) {
                    //Remove the challenger's invite since it is being used
                    plugin.consumeInvite(player2, player);
                    //Start the game with the desired bet amount
                    createGameInstance(bet);
                }
            } else {

                if (plugin.checkIfInGame(player)) {
                    sendMessage.alreadyInGame(player);
                } else if (plugin.checkIfInvited(inputPlayer, player)) {
                    sendMessage.inviteExists(player);
                }

            }

        } catch (Exception e) {
            //If the command is /rps accept without the player name..
            if (args.length == 1) {
                //Tell player to include the name of inviter in /rps accept (ie: /rps accept John15)
                sendMessage.noPlayerEntered(player);
            } else {
                //Tell player they have no invites
                sendMessage.noInvites(player);
            }
            System.out.println("Accept Match Error: " + e.getCause());
        }
    }

    private void matchRequest() {

        if (!checkIfInvitedAlready()) {
            try {
                player2 = Bukkit.getServer().getPlayer(args[0]);
                sendMessage.challenge(player, player2, 0);
                plugin.addInvite(player2, player);
            } catch (NullPointerException e) {
                System.out.println(e.getCause());
                sendMessage.playerNotFound(player);
            }
        } else {
            sendMessage.inviteExists(player);
        }
    }

    private void denyLastRequest() {
        if (checkIfInvitedAlready()) {
            plugin.consumeInvite(player2, player);
        } else {
            sendMessage.noInvites(player);
        }
    }

    private boolean checkIfInvitedAlready() {
        boolean invited = false;
        player2 = Bukkit.getServer().getPlayer(args[0]);
        try {
            player2 = Bukkit.getServer().getPlayer(args[0]);
            ArrayList invitesSent = plugin.getInvites().get(player2);
            for (int i = 0; i < invitesSent.size(); i++) {
                if (invitesSent.get(i).equals(player)) {
                    invited = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Invite Check Error: " + e.getCause());
            }
            return invited;
    }

    private void matchRequestWithBet() {
        if (!checkIfInvitedAlready()) {
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
        } else {
            sendMessage.inviteExists(player);
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
