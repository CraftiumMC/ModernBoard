package net.craftium.modernboard;

import net.craftium.modernboard.listeners.PlayerListener;
import net.craftium.modernboard.managers.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernBoard extends JavaPlugin
{
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable()
    {
        this.scoreboardManager = new ScoreboardManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable()
    {

    }

    public ScoreboardManager getScoreboardManager()
    {
        return scoreboardManager;
    }
}
