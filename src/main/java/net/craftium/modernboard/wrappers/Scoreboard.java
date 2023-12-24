package net.craftium.modernboard.wrappers;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.managers.ScoreboardManager;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public class Scoreboard
{
    private final Sidebar api;
    private final WeakReference<Player> player;

    public Scoreboard(ModernBoard plugin, Player player, ScoreboardManager manager)
    {
        this.api = manager.getLibrary().createSidebar(Sidebar.MAX_LINES, player.locale());
        this.player = new WeakReference<>(player);

        api.addPlayer(player);
    }

    public Scoreboard line(int line, Component text)
    {
        api.line(line, text);
        return this;
    }

    public Scoreboard title(Component text)
    {
        api.title(text);
        return this;
    }
}
