package net.craftium.modernboard.listeners;

import net.craftium.modernboard.ModernBoard;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.entity.Player;

public class LuckPermsListener
{
    private final ModernBoard plugin;

    public LuckPermsListener(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    public void onUserDataRecalculate(UserDataRecalculateEvent event)
    {
        Player player = plugin.getServer().getPlayer(event.getUser().getUniqueId());
        if(player == null)
            return; // How???

        plugin.getSidebarManager().removePlayer(player);
        plugin.getSidebarManager().addPlayer(player);
    }
}
