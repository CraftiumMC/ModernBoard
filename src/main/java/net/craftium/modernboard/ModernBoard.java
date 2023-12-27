package net.craftium.modernboard;

import net.craftium.modernboard.config.Settings;
import net.craftium.modernboard.listeners.PlayerListener;
import net.craftium.modernboard.managers.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernBoard extends JavaPlugin
{
    private Settings settings;

    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable()
    {
        this.settings = new Settings(this);

        this.scoreboardManager = new ScoreboardManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable()
    {

    }

    public Settings getSettings()
    {
        return settings;
    }

    public ScoreboardManager getScoreboardManager()
    {
        return scoreboardManager;
    }
}
