package net.craftium.modernboard.managers;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.boards.SidebarSettings;
import net.craftium.modernboard.wrappers.Scoreboard;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ScoreboardManager
{
    private final Map<Player, Scoreboard> boards;
    private final ModernBoard plugin;
    private final ScoreboardLibrary api;

    public ScoreboardManager(ModernBoard plugin)
    {
        this.boards = new WeakHashMap<>();
        this.plugin = plugin;
        this.api = loadLibrary();
    }

    public Map<Player, Scoreboard> getBoards()
    {
        return Collections.unmodifiableMap(boards);
    }

    @Nullable
    public Scoreboard addPlayer(Player player)
    {
        if(api.closed())
        {
            plugin.getSLF4JLogger().warn("Tried to add scoreboard to closed manager!");
            return null;
        }

        SidebarSettings sidebar = determineSidebar(player);
        if(sidebar == null)
            return null;

        // If we already have one close it
        Scoreboard present = boards.get(player);
        if(present != null)
            present.close();

        Scoreboard scoreboard = new Scoreboard(plugin, player, sidebar);
        boards.put(player, scoreboard);
        return scoreboard;
    }

    public void removePlayer(Player player)
    {
        if(api.closed())
            return;

        Scoreboard scoreboard = boards.remove(player);
        if(scoreboard == null)
            return;

        scoreboard.close();
    }

    public void close()
    {
        if(api == null || api.closed())
            return;

        boards.values().forEach(Scoreboard::close);
        boards.clear();
        api.close();
    }

    public ScoreboardLibrary getLibrary()
    {
        return api;
    }

    private ScoreboardLibrary loadLibrary()
    {
        if(api != null)
            return api;

        try
        {
            return ScoreboardLibrary.loadScoreboardLibrary(plugin);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to load Scoreboard library:", e);
        }
    }

    @Nullable
    private SidebarSettings determineSidebar(Player player)
    {
        List<SidebarSettings> sidebars = new ArrayList<>(plugin.getSettings().sidebars);
        sidebars.sort(Comparator.comparingInt(SidebarSettings::priority));

        return sidebars.stream()
                .findFirst()
                .orElse(null);
    }
}
