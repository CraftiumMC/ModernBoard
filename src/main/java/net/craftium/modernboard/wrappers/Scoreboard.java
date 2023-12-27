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
    }

    public void close()
    {
        api.close();
    }

    public void update()
    {
        List<SidebarSettings.Line> lines = settings.lines();

        updateTitle(lines.get(0));
        updateLines(lines);
    }

    private void updateTitle(SidebarSettings.Line title)
    {
        int lastTick = title.lastTick();

        if(lastTick >= title.interval())
        {
            // update frame
            String frame = title.frames().next();
            title(updateFrame(frame));
            title.setLastTick(0);
        }
        else
            title.setLastTick(++lastTick);
    }

    private void updateLines(List<SidebarSettings.Line> lines)
    {
        for(int i = 1; i < lines.size(); i++)
        {
            SidebarSettings.Line line = lines.get(i);
            int lastTick = line.lastTick();

            if(lastTick >= line.interval())
            {
                // update frame
                String frame = line.frames().next();
                line(line.index(), updateFrame(frame));
                line.setLastTick(0);
            }
            else
                line.setLastTick(++lastTick);
        }
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
