package net.craftium.modernboard.managers;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.criteria.CriteriaSettings;
import net.craftium.modernboard.entities.Sidebar;
import net.craftium.modernboard.entities.SidebarSettings;
import net.craftium.modernboard.utils.AddingBoolean;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SidebarManager
{
    private final Map<Player, Sidebar> boards;
    private final ModernBoard plugin;
    private final ScoreboardLibrary api;

    public SidebarManager(ModernBoard plugin)
    {
        this.boards = new WeakHashMap<>();
        this.plugin = plugin;
        this.api = loadLibrary();
    }

    public boolean hasActiveBoard(Player player)
    {
        return boards.containsKey(player);
    }

    public Map<Player, Sidebar> getBoards()
    {
        return Collections.unmodifiableMap(boards);
    }

    @Nullable
    public Sidebar addPlayer(Player player)
    {
        if(api.closed())
        {
            plugin.getSLF4JLogger().warn("Tried to add scoreboard to closed manager!");
            return null;
        }

        SidebarSettings settings = determineSidebar(player);
        if(settings == null)
            return null;

        // If we already have one close it
        Sidebar sidebar = boards.get(player);
        if(sidebar != null)
            sidebar.close();

        sidebar = new Sidebar(plugin, player, settings);
        boards.put(player, sidebar);
        return sidebar;
    }

    public void removePlayer(Player player)
    {
        if(api.closed())
            return;

        Sidebar sidebar = boards.remove(player);
        if(sidebar == null)
            return;

        sidebar.close();
    }

    public void removeAllBoards()
    {
        boards.values().forEach(Sidebar::close);
        boards.clear();
    }

    public void close()
    {
        if(api == null || api.closed())
            return;

        removeAllBoards();
        api.close();
    }

    public ScoreboardLibrary getLibrary()
    {
        return api;
    }

    private ScoreboardLibrary loadLibrary()
    {
        if(api != null)
            return api;

        try
        {
            return ScoreboardLibrary.loadScoreboardLibrary(plugin);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to load Scoreboard library:", e);
        }
    }

    @Nullable
    private SidebarSettings determineSidebar(Player player)
    {
        List<SidebarSettings> sidebars = plugin.getSettings().sidebars;

        return sidebars.stream()
                // Check assignment criteria
                .filter(sidebar -> checkCriteria(sidebar, player))
                // Order by priority
                .min(Comparator.comparingInt(SidebarSettings::priority))
                .orElse(null);
    }

    private boolean checkCriteria(SidebarSettings settings, Player player)
    {
        AddingBoolean check = new AddingBoolean(settings.criteriaOperator());
        for(CriteriaSettings criterion : settings.criteria())
            check.add(criterion.type().getChecker().isMet(player, criterion.value()));
        return check.getResult();
    }
}
