package net.craftium.modernboard.command.executors;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

public class ReloadCommand
{
    private final ModernBoard plugin;

    public ReloadCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @Command("modernboard|mb reload")
    @Permission("modernboard.command.reload")
    public void reload(CommandSourceStack source)
    {
        try
        {
            plugin.reloadPlugin();
            source.getSender().sendMessage(Component.text("ModernBoard reloaded successfully!",
                    NamedTextColor.GREEN));
        }
        catch(Exception e)
        {
            source.getSender().sendMessage(Component.text("Failed to reload config files. " +
                    "Check the console for more details.", NamedTextColor.RED));
            plugin.getSLF4JLogger().error("Failed to reload config files:", e);
        }
    }
}
