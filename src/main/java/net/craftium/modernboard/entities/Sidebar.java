package net.craftium.modernboard.entities;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.boards.SidebarSettings;
import net.craftium.modernboard.entities.impl.AnimatedComponentUpdater;
import net.craftium.modernboard.entities.impl.SidebarLineComponent;
import net.craftium.modernboard.entities.impl.SidebarTitleComponent;
import net.craftium.modernboard.entities.impl.StaticComponentUpdater;
import net.craftium.modernboard.tasks.SidebarUpdateTask;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import static net.craftium.modernboard.utils.TextUtil.containsPlaceholders;
import static net.megavex.scoreboardlibrary.api.sidebar.Sidebar.MAX_LINES;

public class Sidebar
{
    private final BukkitTask task;
    private final int tickRate;
    private final List<SidebarComponentUpdater> components;
    private final net.megavex.scoreboardlibrary.api.sidebar.Sidebar api;
    private final WeakReference<Player> player;

    private int lastTick = 0;

    public Sidebar(ModernBoard plugin, Player player, SidebarSettings settings)
    {
        this.components = new LinkedList<>();
        this.api = plugin.getSidebarManager().getLibrary().createSidebar(MAX_LINES, player.locale());
        this.player = new WeakReference<>(player);

        SidebarSettings.Line title = settings.lines().getFirst();
        components.add(createComponentUpdater(plugin, player, new SidebarTitleComponent(api), title));

        for(int i = 1; i < settings.lines().size(); i++)
        {
            SidebarSettings.Line line = settings.lines().get(i);
            components.add(createComponentUpdater(plugin, player, new SidebarLineComponent(line.index(), api), line));
        }

        api.addPlayer(player);
        updateNow(); // update forcefully the first time

        this.tickRate = calculateTickRate();
        this.task = new SidebarUpdateTask(this).runTaskTimerAsynchronously(plugin, 0, tickRate);
    }

    public void close()
    {
        task.cancel();
        api.close();
    }

    public void update()
    {
        update(true);
    }

    public void updateNow()
    {
        update(false);
    }

    private void update(boolean checkInterval)
    {
        for(SidebarComponentUpdater component : components)
        {
            if(!checkInterval || component.requiresUpdate(lastTick))
                component.update(getPlayer());
        }

        this.lastTick += tickRate;
    }

    private SidebarComponentUpdater createComponentUpdater(ModernBoard plugin, Player player, SidebarComponent component, SidebarSettings.Line line)
    {
        if(line.frames().size() < 2 && !containsPlaceholders(line.frames()) &&
                !plugin.getAnimationParser().hasAnimation(line.frames()))
            return new StaticComponentUpdater(component, line.frames().getFirst());
        return new AnimatedComponentUpdater(plugin, player, component, line);
    }

    private int calculateTickRate()
    {
        int rate = 0;
        for(SidebarComponentUpdater component : components)
        {
            if(component instanceof AnimatedComponentUpdater animated)
                rate = BigInteger.valueOf(animated.getInterval()).gcd(BigInteger.valueOf(rate)).intValue();
        }

        return rate;
    }

    public Player getPlayer()
    {
        return player.get();
    }
}
