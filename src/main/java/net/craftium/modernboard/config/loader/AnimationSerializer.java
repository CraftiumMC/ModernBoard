package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.animations.Animation;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Objects;

public class AnimationSerializer implements TypeSerializer<Animation>
{
    @Override
    public Animation deserialize(Type type, ConfigurationNode node) throws SerializationException
    {
        return new Animation(Objects.toString(node.key()),
                node.node("frames").get(String[].class),
                node.node("repeat-backwards").getBoolean());
    }

    @Override
    public void serialize(Type type, Animation obj, ConfigurationNode node) throws SerializationException
    {
        if(obj == null)
        {
            node.raw(null);
            return;
        }

        node.node("frames").set(obj.frames());
        node.node("repeat-backwards").set(obj.repeatBackwards());
    }
}
