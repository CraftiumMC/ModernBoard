package net.craftium.modernboard.listeners;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.wrappers.Scoreboard;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        Scoreboard sb = plugin.getScoreboardManager().addPlayer(event.getPlayer());
        sb.title(Component.text("TECH TEST"))
                .line(0, Component.text("Line 1"))
                .line(1, Component.text("Line 2"))
                .line(2, Component.text("Line 3"))
                .line(3, Component.text("Line 4"));
    }
}
