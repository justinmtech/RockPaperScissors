package com.justinmtech.rockpaperscissors;

import com.justinmtech.rockpaperscissors.game.Invites;
import org.bukkit.plugin.java.JavaPlugin;

public final class RockPaperScissors extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new Invites();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
