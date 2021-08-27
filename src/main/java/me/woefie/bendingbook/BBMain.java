package me.woefie.bendingbook;

import org.bukkit.plugin.java.JavaPlugin;

public class BBMain extends JavaPlugin {
    public static BBMain instance;

    public void onEnable() {

        instance = this;

        getCommand("Bendingbook").setTabCompleter(new BBTabCompleter());
        getCommand("bendingbook").setExecutor(new BBGetBook());
        this.saveDefaultConfig();
    }


    public void onDisable() {
        this.saveConfig();
    }


}
