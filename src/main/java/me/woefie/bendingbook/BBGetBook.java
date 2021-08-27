package me.woefie.bendingbook;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BBGetBook implements CommandExecutor {
    public FileConfiguration config = BBMain.instance.getConfig();

    public ItemStack book;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] list) {

        if (string.equalsIgnoreCase("bendingbook")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (list.length > 0) {
                    if (list[0].equalsIgnoreCase("create")) {
                        if (player.hasPermission("woefie.bendingbook.create")) {
                            this.book = player.getInventory().getItemInMainHand();
                            if (book.getType() == Material.WRITTEN_BOOK) {
                                setBook(book);
                                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Book created");
                                return true;
                            } else {
                                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This item is not a book.");
                                return false;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to use this command");
                            return false;
                        }

                    }else if (list[0].equalsIgnoreCase("give")) {
                        if (player.hasPermission("woefie.bendingbook.give")) {
                            Player messager = (Player) sender;
                            if (!(list.length >= 2)) {
                                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You did not specify a player!");
                                return false;
                            } else {
                                Player receiver = Bukkit.getPlayer(list[1]);
                                if (receiver != null) {
                                    messager.sendMessage(ChatColor.GREEN +  "" + ChatColor.BOLD + "Book sent to " + receiver.getName());
                                    giveBook(receiver);

                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (p.hasPermission("woefie.bendingbook.getmessage")) {
                                            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + player.getName() + " Gave a bendingbook to " + receiver.getName());
                                        }
                                    }
                                    return true;
                                }else {
                                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Player is not online");
                                    return false;
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to use this command");
                        }

                    }    else if (list[0].equalsIgnoreCase("help") || list[0].equalsIgnoreCase("?")) {
                        if (player.hasPermission("woefie.bendingbook.help")) {
                            if (list.length >= 2) {
                                if (list[1].equalsIgnoreCase("create")) {
                                    player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Turn the book in your hand into the bendingbook." );
                                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook create");
                                    return true;
                                } else if (list[1].equalsIgnoreCase("give")) {
                                    player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Give an online player the bendingbook");
                                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook give {player}");
                                    return true;
                                } else if (list[1].equalsIgnoreCase("help") || list[1].equalsIgnoreCase("?")) {
                                    player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Shows all the usages and information about the commands.");
                                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook help {create/give/help/?}");
                                    return true;
                                } else {
                                    player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Wrong usage");
                                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook help {create/give/help/?}");
                                }
                            }else {
                                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "BendingBook V1.0 by Woefie.");
                                player.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook help {create/give/help/?}");
                                return false;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to use this command");
                            return false;
                        }
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "BendingBook V1.0 by Woefie.");
                        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook {create/give/help/?}");
                        return false;
                    }
                }else {
                    sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "BendingBook V1.0 by Woefie.");
                    sender.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Usage: /bendingbook {create/give/help/?}");
                    return false;
                }
            }return false;
        } return false;
    }

    public void setBook(ItemStack book) {
        if (alreadyHasABook(book)) {
            return;
        } else {
            config.set("book", book);

            BBMain.instance.saveConfig();
        }
    }

    public boolean alreadyHasABook(ItemStack book) {
        return false;
    }

    public boolean giveBook(Player player) {
        ItemStack bendingbook = config.getItemStack("book");
        if(bendingbook == null) return false;
        player.getInventory().addItem(bendingbook);
        return true;

    }
}
