package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.animations.impl.ReplacingAnimation;
import net.craftium.modernboard.entities.SidebarSettings;
import org.slf4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.reference.ConfigurationReference;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.transformation.ConfigurationTransformation;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Configuration
{
    protected final Logger logger;
    protected final ModernBoard plugin;
    private final String fileName;
    private final String templateFile;
    private final YamlConfigurationLoader loader;

    private ConfigurationReference<CommentedConfigurationNode> config;

    protected Configuration(ModernBoard plugin, File file)
    {
        this(plugin, file, null);
    }

    protected Configuration(ModernBoard plugin, String fileName, String templateFile)
    {
        this(plugin, new File(plugin.getDataFolder(), fileName), templateFile);
    }

    protected Configuration(ModernBoard plugin, File file, String templateFile)
    {
        this.logger = plugin.getSLF4JLogger();
        this.plugin = plugin;
        this.fileName = file.getName();
        this.templateFile = templateFile;
        this.loader = YamlConfigurationLoader.builder()
                .defaultOptions(opts -> opts.serializers(SERIALIZERS))
                .file(file)
                .headerMode(HeaderMode.PRESET)
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .build();

        if(templateFile != null && !file.exists())
            plugin.saveResource(templateFile, false);

        load();
    }

    private void load()
    {
        try
        {
            this.config = ConfigurationReference.fixed(loader);
        }
        catch(ConfigurateException e)
        {
            logger.error("Failed to load {}", fileName, e);
        }
    }

    public void reload()
    {
        File file = new File(plugin.getDataFolder(), fileName);

        if(templateFile != null && !file.exists())
            plugin.saveResource(templateFile, false);

        load();
        readConfig(getClass(), this);
    }

    protected void migrateConfig(ConfigurationTransformation migrator)
    {
        try
        {
            int startVersion = getInt("version", -1);
            CommentedConfigurationNode node = config.node();
            migrator.apply(node);
            int endVersion = config.get("version").getInt();

            if(endVersion > startVersion)
            {
                logger.info("Successfully migrated {} from version {} to {}!", fileName, startVersion, endVersion);
                config.save(node);
            }
        }
        catch(ConfigurateException e)
        {
            throw new RuntimeException("Failed to migrate " + fileName +  "!", e);
        }
    }

    protected void readConfig(Class<?> clazz, Object instance)
    {
        for(Method method : clazz.getDeclaredMethods())
        {
            if(Modifier.isPrivate(method.getModifiers()))
            {
                if(method.getName().startsWith("_") && method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE)
                {
                    try
                    {
                        method.setAccessible(true);
                        method.invoke(instance);
                    }
                    catch(InvocationTargetException ex) {throw new RuntimeException(ex.getCause());}
                    catch(Exception ex) {logger.error("Error invoking {}", method, ex);}
                }
            }
        }
    }

    protected void updateConfig(int latestVersion)
    {
        URL resource = plugin.getClass().getClassLoader().getResource(templateFile);
        if(resource == null)
            throw new RuntimeException("Failed to update config. File in jar not found: " + templateFile);

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .url(resource)
                .build();

        try
        {
            ConfigurationNode templateNode = loader.load();
            int currentVersion = getInt("version", 0);

            if(latestVersion <= currentVersion)
                return;

            config.node().mergeFrom(templateNode);
            config.node().node("version").raw(latestVersion);
            config.save();
            logger.info("Successfully updated {} from version {} to {}!", fileName, currentVersion, latestVersion);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to update " + fileName + " from template " + templateFile, e);
        }
    }

    protected <V> V get(String key, Class<V> clazz, V def)
    {
        try
        {
            if(key == null)
                return config.node().get(clazz, def);
            else
                return config.get(path(key)).get(clazz, def);
        }
        catch(SerializationException e)
        {
            logger.error("Failed to create object {}", key, e);
            return def;
        }
    }

    protected boolean getBoolean(String key, boolean defBool)
    {
        return config.get(path(key)).getBoolean(defBool);
    }

    protected ConfigurationNode root()
    {
        return config.node();
    }

    protected int getInt(String key, int defInt)
    {
        return config.get(path(key)).getInt(defInt);
    }

    protected Map<Object, ? extends ConfigurationNode> getMap(String key, Map<Object, ? extends ConfigurationNode> defMap)
    {
        ConfigurationNode node = config.get(path(key));

        if(!(node.isMap()) || node.virtual())
            return defMap;

        return node.childrenMap();
    }

    protected <V> List<V> getList(String key, Class<V> type, List<V> defList)
    {
        try
        {
            return config.get(path(key)).getList(type, defList);
        }
        catch(SerializationException e)
        {
            logger.error("Failed to load list {}", key, e);
            return Collections.emptyList();
        }
    }

    protected String getString(String key, String defStr)
    {
        return config.get(path(key)).getString(defStr);
    }

    private Object[] path(String path)
    {
        return path.split("\\.");
    }

    protected static final TypeSerializerCollection SERIALIZERS = TypeSerializerCollection.defaults().childBuilder()
            .register(ReplacingAnimation.class, new AnimationSerializer())
            .register(SidebarSettings.class, new SidebarSettingsSerializer())
            .build();
}
