package com.justinmtech.rockpaperscissors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class RockPaperScissors extends JavaPlugin {
    private HashMap<Player, Player> invites = new HashMap<>();
    private ArrayList<GameInstance> gameInstances = new ArrayList<>();

    @Override
    public void onEnable() {
        //Plugin startup logic
        Commands commands = new Commands(this);
        this.getCommand("rps").setExecutor(commands);
        this.getCommand("rock").setExecutor(commands);
        this.getCommand("paper").setExecutor(commands);
        this.getCommand("scissors").setExecutor(commands);

        System.out.println("RockPaperScissors enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("RockPaperScissors disabled!");
    }

    public ArrayList<GameInstance> getGames() {
        return gameInstances;
    }

    public void setGames(ArrayList<GameInstance> gameInstances) {
        this.gameInstances = gameInstances;
    }

    public void addGame(GameInstance gameInstance) {
        gameInstances.add(gameInstance);
    }

    public void removeGame(Player player1) {

    }

    public void resetInputs(Player player1) {
        getGame(player1).setPlayer1Input(null);
        getGame(player1).setPlayer2Input(null);
    }

    public GameInstance getGame(Player player1) {
        GameInstance gameInstance = null;
        for (int i = 0; i < gameInstances.size(); i++) {
            if (gameInstances.get(i).getPlayer1().getName() == player1.getName() || gameInstances.get(i).getPlayer2().getName() == player1.getName()) {
                gameInstance = gameInstances.get(i);
            }
        }
        return gameInstance;
    }

    public HashMap<Player, Player> getInvites() {
        return invites;
    }

    public void setInvites(HashMap<Player, Player> invites) {
        this.invites = invites;
    }

    public void addInvite(Player player2, Player player1) {
        invites.put(player2, player1);
    }

    public void removeInvite(Player invited) {
        invites.remove(invited);
    }


}
