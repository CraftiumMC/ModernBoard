package net.craftium.modernboard.command.executors;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.craftium.modernboard.ModernBoard;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.ProxiedBy;

public class ToggleCommand
{
    private final ModernBoard plugin;

    public ToggleCommand(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @Command("modernboard|mb toggle")
    @ProxiedBy("sidebar|sb")
    @Permission("modernboard.command.toggle")
    public void toggleSelf(Player player)
    {
        boolean active = plugin.getScoreboardManager().hasActiveBoard(player);

        if(active)
        {
            plugin.getScoreboardManager().removePlayer(player);
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
        }
        else
        {
            plugin.getScoreboardManager().addPlayer(player);
            player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
        }
    }

    @Command("modernboard|mb toggle <player>")
    @ProxiedBy("sidebar|sb")
    @Permission("modernboard.command.toggle.other")
    public void toggleOther(CommandSourceStack source, @Argument Player player,
                            @Flag(value = "silent", aliases = "s") boolean silent)
    {
        boolean active = plugin.getScoreboardManager().hasActiveBoard(player);

        if(active)
        {
            plugin.getScoreboardManager().removePlayer(player);
            source.getSender().sendMessage(plugin.getMessages().get("scoreboard-toggle-off-other",
                    Placeholder.component("target", player.name())));
            if(!silent)
                player.sendMessage(plugin.getMessages().get("scoreboard-toggle-off"));
        }
        else
        {
            plugin.getScoreboardManager().addPlayer(player);
            source.getSender().sendMessage(plugin.getMessages().get("scoreboard-toggle-on-other",
                    Placeholder.component("target", player.name())));
            if(!silent)
                player.sendMessage(plugin.getMessages().get("scoreboard-toggle-on"));
        }
    }
}
