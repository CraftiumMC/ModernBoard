package net.craftium.modernboard.command.executors;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.Flag;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnOffCommand
{
    private final ModernBoard plugin;

    public OnOffCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @CommandMethod("modernboard|mb on")
    @CommandPermission("modernboard.command.on")
    public void turnOnSelf(Player player)
    {
        plugin.getSidebarManager().addPlayer(player);
        player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
    }

    @CommandMethod("modernboard|mb on <player>")
    @CommandPermission("modernboard.command.on.other")
    public void turnOnOther(CommandSender sender, @Argument("player") Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        plugin.getSidebarManager().addPlayer(player);
        sender.sendMessage(plugin.getMessages().get("scoreboard-toggle-on-other",
                Placeholder.component("target", player.name())));
        if(!silent)
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
    }

    @CommandMethod("modernboard|mb off")
    @CommandPermission("modernboard.command.off")
    public void turnOffSelf(Player player)
    {
        plugin.getSidebarManager().removePlayer(player);
        player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
    }

    @CommandMethod("modernboard|mb off <player>")
    @CommandPermission("modernboard.command.off.other")
    public void turnOffOther(CommandSender sender, @Argument("player") Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        plugin.getSidebarManager().removePlayer(player);
        sender.sendMessage(plugin.getMessages().get("scoreboard-toggle-off-other",
                Placeholder.component("target", player.name())));
        if(!silent)
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
    }
}
