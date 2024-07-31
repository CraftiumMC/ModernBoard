package net.craftium.modernboard.listeners;

import net.craftium.modernboard.ModernBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
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
        plugin.getScoreboardManager().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.getScoreboardManager().removePlayer(event.getPlayer());
    }

    // TODO debug utility: remove before release
    @EventHandler
    public void test(BlockPlaceEvent ev)
    {
        if(ev.getBlockPlaced().getType() == Material.LAPIS_BLOCK)
            plugin.getScoreboardManager().removePlayer(ev.getPlayer());
        else if(ev.getBlockPlaced().getType() == Material.DIAMOND_BLOCK)
            plugin.getScoreboardManager().addPlayer(ev.getPlayer());
        else if(ev.getBlockPlaced().getType() == Material.EMERALD_BLOCK)
        {
            plugin.getScoreboardManager().removePlayer(ev.getPlayer());
            plugin.reloadPlugin();
            plugin.getScoreboardManager().addPlayer(ev.getPlayer());
        }
    }
}
