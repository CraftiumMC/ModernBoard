package net.craftium.modernboard.wrappers;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.boards.SidebarSettings;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.List;

public class Scoreboard
{
    private final SidebarSettings settings;
    private final Sidebar api;
    private final WeakReference<Player> player;

    public Scoreboard(ModernBoard plugin, Player player, SidebarSettings settings)
    {
        this.settings = settings;
        this.api = plugin.getScoreboardManager().getLibrary().createSidebar(Sidebar.MAX_LINES, player.locale());
        this.player = new WeakReference<>(player);

        api.addPlayer(player);
        updateNow(); // update forcefully the first time
    }

    public void close()
    {
        api.close();
    }

    public void update()
    {
        List<SidebarSettings.Line> lines = settings.lines();

        updateTitle(true, lines.get(0));
        updateLines(true, lines);
    }

    public void updateNow()
    {
        List<SidebarSettings.Line> lines = settings.lines();

        updateTitle(false, lines.get(0));
        updateLines(false, lines);
    }

    private void updateTitle(boolean checkInterval, SidebarSettings.Line title)
    {
        update0(checkInterval, -1, title);
    }

    private void updateLines(boolean checkInterval, List<SidebarSettings.Line> lines)
    {
        for(int i = 1; i < lines.size(); i++)
        {
            SidebarSettings.Line line = lines.get(i);
            update0(checkInterval, line.index(), line);
        }
    }

    private void update0(boolean checkInterval, int index, SidebarSettings.Line line)
    {
        int lastTick = line.lastTick();

        if(!checkInterval || lastTick >= line.interval())
        {
            // update frame
            String frame = line.frames().next();
            if(index < 0) title(updateFrame(frame));
            else line(index, updateFrame(frame));
            line.setLastTick(0);
        }
        else
            line.setLastTick(++lastTick);
    }

    private Component updateFrame(String frame)
    {
        return Component.text(frame); // todo placeholders & minimsg
    }

    public Player getPlayer()
    {
        return player.get();
    }

    public Scoreboard line(int line, Component text)
    {
        api.line(line, text);
        return this;
    }

    public Scoreboard title(Component text)
    {
        api.title(text);
        return this;
    }
}
