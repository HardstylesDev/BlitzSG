package me.hardstyles.blitz.rank.ranks;

import me.hardstyles.blitz.rank.Rank;
import org.bukkit.ChatColor;

public class Admin extends Rank {
    public Admin(){
    super("Admin",ChatColor.RED + "[ADMIN] ", ChatColor.RED + "", 5,8);
    }
}
