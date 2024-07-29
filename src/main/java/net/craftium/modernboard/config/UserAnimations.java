package net.craftium.modernboard.config;

import net.craftium.modernboard.ModernBoard;
import net.craftium.modernboard.animations.Animation;
import net.craftium.modernboard.animations.impl.ReplacingAnimation;
import net.craftium.modernboard.config.loader.Configuration;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Map;

@SuppressWarnings("unused")
public class UserAnimations extends Configuration
{
    public UserAnimations(ModernBoard plugin)
    {
        super(plugin, "animations.yml", "animations.yml");
        readConfig(UserAnimations.class, this);
    }

    private void _registerAnimations() throws SerializationException
    {
        var map = root().childrenMap();

        for(Map.Entry<Object, ? extends ConfigurationNode> entry : map.entrySet())
        {
            ConfigurationNode node = entry.getValue();
            Animation animation = node.get(ReplacingAnimation.class);
            if(animation == null)
                continue;

            plugin.getAnimationRegistry().registerAnimation(animation.getIdentifier(), animation);
        }
    }
}
