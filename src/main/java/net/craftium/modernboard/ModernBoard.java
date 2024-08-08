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
import net.craftium.modernboard.listeners.LuckPermsListener;
import net.craftium.modernboard.listeners.PlayerListener;
import net.craftium.modernboard.managers.SidebarManager;
import net.craftium.modernboard.utils.UpdateChecker;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.plugin.PluginManager;
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

        registerCommands();
        registerListeners();

        UpdateChecker.start(this);

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
        sidebarManager.removeAllBoards();
        settings.reload();
        userAnimations.reload();
        getServer().getOnlinePlayers().forEach(sidebarManager::addPlayer);
    }

    private void registerCommands()
    {
        commandManager.registerCommands(new OnOffCommand(this), new ReloadCommand(this), new ToggleCommand(this));
    }

    private void registerListeners()
    {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);

        // If LuckPerms is available we will use it to listen to permission changes
        if(pluginManager.isPluginEnabled("LuckPerms"))
        {
            LuckPerms luckPerms = LuckPermsProvider.get();
            LuckPermsListener listener = new LuckPermsListener(this);
            //noinspection resource
            luckPerms.getEventBus().subscribe(this, UserDataRecalculateEvent.class, listener::onUserDataRecalculate);
            getSLF4JLogger().info("LuckPerms has been detected. Listening to permission changes.");
        }
        else
            getSLF4JLogger().warn("LuckPerms has not been detected. Permission-based assignments will not be applied instantly.");
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
