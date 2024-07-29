package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.animations.impl.ReplacingAnimation;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Objects;

public class AnimationSerializer implements TypeSerializer<ReplacingAnimation>
{
    @Override
    public ReplacingAnimation deserialize(Type type, ConfigurationNode node) throws SerializationException
    {
        return new ReplacingAnimation(Objects.toString(node.key()).toLowerCase(Locale.ROOT),
                node.node("repeat-backwards").getBoolean(),
                node.node("frames").get(String[].class));
    }

    @Override
    public void serialize(Type type, ReplacingAnimation obj, ConfigurationNode node) throws SerializationException
    {
        if(obj == null)
        {
            node.raw(null);
            return;
        }

        node.node("repeat-backwards").set(obj.repeatBackwards());
        node.node("frames").set(obj.getFrames());
    }
}
