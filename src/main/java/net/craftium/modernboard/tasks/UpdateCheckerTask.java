package net.craftium.modernboard.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.utils.UpdateChecker;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UpdateCheckerTask extends BukkitRunnable
{
    private final ModernBoard plugin;

    public UpdateCheckerTask(ModernBoard plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        plugin.getLogger().info("Checking for updates...");

        try(HttpClient httpClient = HttpClient.newHttpClient())
        {
            URI url = URI.create(GITHUB_URL);
            HttpRequest request = HttpRequest.newBuilder(url)
                    .header("User-Agent", "CraftiumMC/ModernBoard Update Checker")
                    .timeout(Duration.ofSeconds(60))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200)
            {
                plugin.getSLF4JLogger().warn("Failed to check for updates. Status code: {}", response.statusCode());
                return;
            }

            Gson gson = new Gson();
            JsonObject obj = gson.fromJson(response.body(), JsonObject.class);
            String latestRelease = obj.getAsJsonPrimitive("tag_name").getAsString();

            // Check the version
            latestRelease = latestRelease.replaceAll("(-SNAPSHOT)", "");
            String currentRelease = plugin.getPluginMeta().getVersion().replaceAll("(-SNAPSHOT)", "");

            int latVer = Integer.parseInt(latestRelease.replace(".", ""));
            int curVer = Integer.parseInt(currentRelease.replace(".", ""));

            if(latVer > curVer)
            {
                UpdateChecker.setUpdateAvailable(true, latestRelease);
                Component message = UpdateChecker.createNotification();
                plugin.getServer().broadcast(plugin.getMessages().withPrefix(message),
                        "modernboard.updatenotify");
            }
        }
        catch(Exception e)
        {
            plugin.getSLF4JLogger().warn("Failed to check for updates: {}: {}", e.getClass(), e.getMessage());
            return;
        }

        plugin.getLogger().info("No updates found.");
    }

    private static final String GITHUB_URL = "https://api.github.com/repos/CraftiumMC/ModernBoard/releases/latest";
}
