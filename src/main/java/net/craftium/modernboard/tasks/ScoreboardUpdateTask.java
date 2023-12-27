package net.craftium.modernboard.tasks;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.wrappers.Scoreboard;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardUpdateTask extends BukkitRunnable
{
    private final ModernBoard plugin;

    public ScoreboardUpdateTask(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        for(Scoreboard sb : plugin.getScoreboardManager().getBoards().values())
            sb.update();
    }
}
