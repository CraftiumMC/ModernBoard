package net.craftium.modernboard.managers;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.wrappers.Scoreboard;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import org.bukkit.entity.Player;

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

    public Scoreboard addPlayer(Player player)
    {
        Scoreboard scoreboard = new Scoreboard(plugin, player, this);
        boards.put(player, scoreboard);
        return scoreboard;
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
}
