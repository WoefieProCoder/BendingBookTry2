package me.woefie.bendingbook;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BBTabCompleter implements TabCompleter {

    List<String> tabs = new ArrayList<String>();
    List<String> helptabs = new ArrayList<String>();
    List<String> names = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (tabs.isEmpty()) {
            if (player.hasPermission("woefie.bendingbook.create")) {
                tabs.add("create");
            }
            if (player.hasPermission("woefie.bendingbook.give")) {
                tabs.add("give");
            }
            if (player.hasPermission("woefie.bendingbook.help")) {
                tabs.add("help");
                tabs.add("?");
            }
        }
        if (helptabs.isEmpty()) {
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                if (player.hasPermission("woefie.bendingbook.help")) {
                    helptabs.add("give");
                    helptabs.add("create");
                    helptabs.add("help");
                    helptabs.add("?");
                }
            }
        }

        if (names.isEmpty()) {
            if (args[0].equalsIgnoreCase("give")) {
                if (player.hasPermission("woefie.bendingbook.give")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        names.add(p.getName());
                    }
                }
            }
        }
        List<String> helpresults = new ArrayList<String>();
        List<String> results = new ArrayList<String>();
        List<String> nameresults = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : tabs) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    results.add(a);
            }
            return results;

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                for (String a : helptabs) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        helpresults.add(a);
                }
                return helpresults;
            } else if (args[0].equalsIgnoreCase("give")) {
                for (String b : names) {
                    if (b.toLowerCase().startsWith(args[1].toLowerCase()))
                        nameresults.add(b);
                }
                names.clear();
                return nameresults;
            }

        }
        return null;

    }

}
