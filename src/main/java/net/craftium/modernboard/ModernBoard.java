package net.craftium.modernboard;

import net.craftium.modernboard.animations.AnimationParser;
import net.craftium.modernboard.config.AnimationRegistry;
import net.craftium.modernboard.config.Settings;
import net.craftium.modernboard.listeners.PlayerListener;
import net.craftium.modernboard.managers.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernBoard extends JavaPlugin
{
    private AnimationRegistry animationRegistry;
    private Settings settings;

    private AnimationParser animationParser;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable()
    {
        this.animationRegistry = new AnimationRegistry(this);
        this.settings = new Settings(this);

//        this.animationRegistry = new AnimationRegistry();
        this.animationParser = new AnimationParser(animationRegistry);
        this.scoreboardManager = new ScoreboardManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        // if for some reason there are players online when the plugin is enabled, add them to the scoreboard
        getServer().getOnlinePlayers().forEach(scoreboardManager::addPlayer);
    }

    @Override
    public void onDisable()
    {
        if(scoreboardManager != null)
            scoreboardManager.close();
    }

    public AnimationRegistry getAnimationRegistry()
    {
        return animationRegistry;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public AnimationParser getAnimationParser()
    {
        return animationParser;
    }

    public ScoreboardManager getScoreboardManager()
    {
        return scoreboardManager;
    }
}
