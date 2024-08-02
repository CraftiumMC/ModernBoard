package net.craftium.modernboard.command.executors;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.Flag;
import cloud.commandframework.annotations.ProxiedBy;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand
{
    private final ModernBoard plugin;

    public ToggleCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @CommandMethod("modernboard|mb toggle")
    @ProxiedBy("sidebar")
    @CommandPermission("modernboard.command.toggle")
    public void toggleSelf(Player player)
    {
        boolean active = plugin.getSidebarManager().hasActiveBoard(player);

        if(active)
        {
            plugin.getSidebarManager().removePlayer(player);
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
        }
        else
        {
            plugin.getSidebarManager().addPlayer(player);
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
        }
    }

    @CommandMethod("modernboard|mb toggle <player>")
    @ProxiedBy("sidebar")
    @CommandPermission("modernboard.command.toggle.other")
    public void toggleOther(CommandSender sender, @Argument("player") Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        boolean active = plugin.getSidebarManager().hasActiveBoard(player);

        if(active)
        {
            plugin.getSidebarManager().removePlayer(player);
            sender.sendMessage(plugin.getMessages().get("scoreboard-toggle-off-other",
                    Placeholder.component("target", player.name())));
            if(!silent)
                player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
        }
        else
        {
            plugin.getSidebarManager().addPlayer(player);
            sender.sendMessage(plugin.getMessages().get("scoreboard-toggle-on-other",
                    Placeholder.component("target", player.name())));
            if(!silent)
                player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
        }
    }
}
