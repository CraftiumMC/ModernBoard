package net.craftium.modernboard.listeners;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.utils.UpdateChecker;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
    private final ModernBoard plugin;

    public PlayerListener(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        plugin.getSidebarManager().addPlayer(player);

        if(UpdateChecker.isUpdateAvailable() && player.hasPermission("modernboard.updatenotify"))
        {
            Component message = UpdateChecker.createNotification();
            player.sendMessage(plugin.getMessages().withPrefix(message));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.getSidebarManager().removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerWorldChange(PlayerChangedWorldEvent event)
    {
        determineSideboard(event);
    }

    private void determineSideboard(PlayerChangedWorldEvent event)
    {
        plugin.getSidebarManager().removePlayer(event.getPlayer());
        plugin.getSidebarManager().addPlayer(event.getPlayer());
    }
}
