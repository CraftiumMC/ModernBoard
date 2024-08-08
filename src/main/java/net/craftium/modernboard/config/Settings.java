package net.craftium.modernboard.config;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.entities.SidebarSettings;
import net.craftium.modernboard.config.loader.Configuration;
import net.craftium.modernboard.config.loader.SidebarConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Settings extends Configuration
{
    public Settings(ModernBoard plugin)
    {
        super(plugin, "config.yml", "config.yml");
        readConfig(Settings.class, this);
    }

    public boolean checkUpdates;
    private void _checkUpdates()
    {
        this.checkUpdates = getBoolean("check-updates", true);
    }

    public List<SidebarSettings> sidebars;
    private void _sidebars()
    {
        List<SidebarSettings> ret = new ArrayList<>();
        File folder = new File(plugin.getDataFolder(), "scoreboards");

        if(!folder.exists())
        {
            if(!folder.mkdirs())
                throw new RuntimeException("Could not create scoreboards directory");

            plugin.saveResource("scoreboards/main.yml", false);
        }

        File[] files = folder.listFiles((file, fileName) -> fileName.toLowerCase().endsWith(".yml"));
        if(files != null)
        {
            for(File file : files)
            {
                plugin.getSLF4JLogger().info("Loading scoreboard from {}...", file.getName());
                SidebarConfig config = new SidebarConfig(plugin, file);
                ret.add(config.load());
            }
        }

        this.sidebars = ret;
    }
}
