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
import net.craftium.modernboard.managers.SidebarManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ModernBoard extends JavaPlugin
{
    private AnimationRegistry animationRegistry;
    private AnimationParser animationParser;

    private Messages messages;
    private Settings settings;
    private UserAnimations userAnimations;

    private CommandManager commandManager;
    private SidebarManager sidebarManager;

    @Override
    public void onEnable()
    {
        this.animationRegistry = new AnimationRegistry();
        this.animationParser = new AnimationParser(animationRegistry);

        this.messages = new Messages(this);
        this.settings = new Settings(this);
        this.userAnimations = new UserAnimations(this);

        this.commandManager = new CommandManager(this);
        this.sidebarManager = new SidebarManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        registerCommands();

        // if for some reason there are players online when the plugin is enabled, assign them a sidebar
        getServer().getOnlinePlayers().forEach(sidebarManager::addPlayer);
    }

    @Override
    public void onDisable()
    {
        if(sidebarManager != null)
            sidebarManager.close();
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

    public SidebarManager getSidebarManager()
    {
        return sidebarManager;
    }
}
