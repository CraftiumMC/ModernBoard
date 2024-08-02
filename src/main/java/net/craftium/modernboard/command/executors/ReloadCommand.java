package net.craftium.modernboard.command.executors;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand
{
    private final ModernBoard plugin;

    public ReloadCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @CommandMethod("modernboard|mb reload")
    @CommandPermission("modernboard.command.reload")
    public void reload(CommandSender sender)
    {
        try
        {
            plugin.reloadPlugin();
            sender.sendMessage(Component.text("ModernBoard reloaded successfully!",
                    NamedTextColor.GREEN));
        }
        catch(Exception e)
        {
            sender.sendMessage(Component.text("Failed to reload config files. " +
                    "Check the console for more details.", NamedTextColor.RED));
            plugin.getSLF4JLogger().error("Failed to reload config files:", e);
        }
    }
}
