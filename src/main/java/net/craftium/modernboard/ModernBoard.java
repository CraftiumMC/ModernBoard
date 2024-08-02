package net.craftium.modernboard;

import net.craftium.modernboard.animations.AnimationParser;
import net.craftium.modernboard.animations.AnimationRegistry;
import net.craftium.modernboard.command.CommandManager;
import net.craftium.modernboard.command.executors.OnOffCommand;
import net.craftium.modernboard.command.executors.ReloadCommand;
import net.craftium.modernboard.command.executors.ToggleCommand;
import net.craftium.modernboard.config.Messages;
import net.craftium.modernboard.config.Settings;
import net.craftium.modernboard.config.UserAnimations;
import net.craftium.modernboard.listeners.PlayerListener;
import net.craftium.modernboard.managers.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernBoard extends JavaPlugin
{
    private AnimationRegistry animationRegistry;
    private AnimationParser animationParser;

    private Messages messages;
    private Settings settings;
    private UserAnimations userAnimations;

    private CommandManager commandManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable()
    {
        this.animationRegistry = new AnimationRegistry();
        this.animationParser = new AnimationParser(animationRegistry);

        this.messages = new Messages(this);
        this.settings = new Settings(this);
        this.userAnimations = new UserAnimations(this);

        this.commandManager = new CommandManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        registerCommands();

        // if for some reason there are players online when the plugin is enabled, add them to the scoreboard
        getServer().getOnlinePlayers().forEach(scoreboardManager::addPlayer);
    }

    @Override
    public void onDisable()
    {
        if(scoreboardManager != null)
            scoreboardManager.close();
    }

    public void reloadPlugin()
    {
        settings.reload();
        userAnimations.reload();
    }

    private void registerCommands()
    {
        commandManager.registerCommands(new OnOffCommand(this), new ReloadCommand(this), new ToggleCommand(this));
    }

    public CommandManager getCommandManager()
    {
        return commandManager;
    }

    public Messages getMessages()
    {
        return messages;
    }

    public Settings getSettings()
    {
        return settings;
    }

    public AnimationParser getAnimationParser()
    {
        return animationParser;
    }

    public AnimationRegistry getAnimationRegistry()
    {
        return animationRegistry;
    }

    public ScoreboardManager getScoreboardManager()
    {
        return scoreboardManager;
    }
}
