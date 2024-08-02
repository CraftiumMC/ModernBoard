package net.craftium.modernboard.command.executors;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;

public class OnOffCommand
{
    private final ModernBoard plugin;

    public OnOffCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @Command("modernboard|mb on")
    @Permission("modernboard.command.on")
    public void turnOnSelf(Player player)
    {
        plugin.getScoreboardManager().addPlayer(player);
        player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
    }

    @Command("modernboard|mb on <player>")
    @Permission("modernboard.command.on.other")
    public void turnOnOther(CommandSourceStack source, @Argument Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        plugin.getScoreboardManager().addPlayer(player);
        source.getSender().sendMessage(plugin.getMessages().get("scoreboard-toggle-on-other",
                Placeholder.component("target", player.name())));
        if(!silent)
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
    }

    @Command("modernboard|mb off")
    @Permission("modernboard.command.off")
    public void turnOffSelf(Player player)
    {
        plugin.getScoreboardManager().removePlayer(player);
        player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
    }

    @Command("modernboard|mb off <player>")
    @Permission("modernboard.command.off.other")
    public void turnOffOther(CommandSourceStack source, @Argument Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        plugin.getScoreboardManager().removePlayer(player);
        source.getSender().sendMessage(plugin.getMessages().get("scoreboard-toggle-off-other",
                Placeholder.component("target", player.name())));
        if(!silent)
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
    }
}
