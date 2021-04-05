package com.justinmtech.rockpaperscissors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;

//Main plugin class
//TODO Add configurable time limit for each rps round
public final class RockPaperScissors extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private Economy econ = null;
    //Used to store bets and the player that made them
    private HashMap<Player, Integer> bets = new HashMap();
    //Stores the invited player as well as the inviter
    private HashMap<Player, Player> invites = new HashMap<>();
    //Stores active GameInstance objects
    private ArrayList<GameInstance> gameInstances = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        CommandHandler commandHandler = new CommandHandler(this);
        this.getCommand("rps").setExecutor(commandHandler);
        this.getCommand("rock").setExecutor(commandHandler);
        this.getCommand("paper").setExecutor(commandHandler);
        this.getCommand("scissors").setExecutor(commandHandler);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        //Execute setupEconomy() and if it returns false, print error in console.
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        System.out.println("RockPaperScissors enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("RockPaperScissors disabled!");
    }

    //Set up Vault Economy dependency
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        System.out.println("Vault hooked successfully to RockPaperScissors!");
        return econ != null;
    }

    //Add a new game instance
    public void addGame(GameInstance gameInstance) {
        gameInstances.add(gameInstance);
    }

    //Remove a game instance with a player object
    public void removeGame(Player player) {
        for (int i = 0; i < gameInstances.size(); i++) {
            if (gameInstances.get(i).getPlayer1() == player || gameInstances.get(i).getPlayer2() == player) {
                gameInstances.remove(i);
            }
        }
    }

    //Fetch a game from player object
    public GameInstance getGame(Player player1) {
        GameInstance gameInstance = null;
        for (int i = 0; i < gameInstances.size(); i++) {
            if (gameInstances.get(i).getPlayer1().getName() == player1.getName() || gameInstances.get(i).getPlayer2().getName() == player1.getName()) {
                gameInstance = gameInstances.get(i);
            }
        }
        return gameInstance;
    }

    public Economy getEcon() {
        return econ;
    }

    public void setEcon(Economy econ) {
        this.econ = econ;
    }

    public HashMap<Player, Player> getInvites() {
        return invites;
    }

    public void addInvite(Player player2, Player player1) {
        invites.put(player2, player1);
    }

    public void removeInvite(Player invited) {
        invites.remove(invited);
    }

    public HashMap<Player, Integer> getBets() {
        return bets;
    }

    public void addBet(Player player, int bet) {
        bets.put(player, bet);
    }
}
