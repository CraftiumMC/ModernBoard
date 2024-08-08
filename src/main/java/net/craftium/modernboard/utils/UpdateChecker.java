package net.craftium.modernboard.utils;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.tasks.UpdateCheckerTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;

import java.time.Duration;

import static net.kyori.adventure.text.format.NamedTextColor.BLUE;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

public class UpdateChecker
{
    private static String release;
    private static boolean updateAvailable;

    public static void start(ModernBoard plugin)
    {
        if(plugin.getSettings().checkUpdates)
            new UpdateCheckerTask(plugin).runTaskTimerAsynchronously(plugin, 0, Duration.ofDays(1).toSeconds() * 20);
    }

    public static boolean isUpdateAvailable()
    {
        return updateAvailable;
    }

    public static String getRelease()
    {
        return release;
    }

    public static void setUpdateAvailable(boolean updateAvailable, String release)
    {
        UpdateChecker.updateAvailable = updateAvailable;
        UpdateChecker.release = release;
    }

    public static Component createNotification()
    {
        return Component.text()
                .append(Component.text("A new version of ModernBoard is available: ", BLUE))
                .append(Component.text(release, Style.style(GOLD, BOLD)))
                .build();
    }
}
