package net.craftium.modernboard.config;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.config.loader.Configuration;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class AnimationRegistry extends Configuration
{
    public AnimationRegistry(ModernBoard plugin)
    {
        super(plugin, "animations.yml", "animations.yml");
        readConfig(AnimationRegistry.class, this);
    }

    public Map<String, Animation> animations;
    private void _animations() throws SerializationException
    {
        Map<String, Animation> ret = new HashMap<>();
        var map = root().childrenMap();

        for(Map.Entry<Object, ? extends ConfigurationNode> entry : map.entrySet())
        {
            String identifier = String.valueOf(entry.getKey());
            ConfigurationNode node = entry.getValue();
            Animation animation = node.get(Animation.class);
            ret.put(identifier, animation);
        }

        this.animations = Collections.unmodifiableMap(ret);
    }
}
