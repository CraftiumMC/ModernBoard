package net.craftium.modernboard.tasks;

import net.craftium.modernboard.entities.Scoreboard;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardUpdateTask extends BukkitRunnable
{
    private final Scoreboard scoreboard;

    public ScoreboardUpdateTask(Scoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
    }

    @Override
    public void run()
    {
        scoreboard.update();
    }
}
