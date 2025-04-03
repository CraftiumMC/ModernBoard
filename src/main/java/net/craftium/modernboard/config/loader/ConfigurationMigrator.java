package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.config.Settings;
import net.craftium.modernboard.entities.CompatibilityMode;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;

import static org.spongepowered.configurate.NodePath.path;
import static org.spongepowered.configurate.transformation.ConfigurationTransformation.builder;

public class ConfigurationMigrator
{
    public static ConfigurationTransformation versionedMigration()
    {
        return ConfigurationTransformation.versionedBuilder()
                .addVersion(Settings.VERSION, oneTo2())
                .build();
    }

    private static ConfigurationTransformation oneTo2()
    {
        return ConfigurationTransformation.chain(
                builder()
                        .addAction(path(), (path, value) ->
                        {
                            value.node("compat-mode").set(CompatibilityMode.HIDE);
                            return null;
                        })
                        .build()
        );
    }
}
