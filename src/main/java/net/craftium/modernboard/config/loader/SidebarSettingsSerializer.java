package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.boards.SidebarSettings;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class SidebarSettingsSerializer implements TypeSerializer<SidebarSettings>
{
    @Override
    public SidebarSettings deserialize(Type type, ConfigurationNode node) throws SerializationException
    {
        List<SidebarSettings.Line> lines = new LinkedList<>();
        for(ConfigurationNode lineConfig : node.node("lines").childrenList())
        {
            lines.add(new SidebarSettings.Line(
                    lineConfig.node("interval").getInt(10),
                    lineConfig.node("frames").getList(String.class)
            ));
        }

        return new SidebarSettings(
                node.node("priority").getInt(),
                lines
        );
    }

    @Override
    public void serialize(Type type, SidebarSettings obj, ConfigurationNode node) throws SerializationException
    {
        if(obj == null)
        {
            node.raw(null);
            return;
        }

        node.node("priority").set(obj.priority());
        ConfigurationNode linesParent = node.node("lines");
        for(SidebarSettings.Line line : obj.lines())
        {
            ConfigurationNode linesChildren = linesParent.appendListNode();
            linesChildren.node("frames").setList(String.class, line.frames());
            linesChildren.node("interval").set(line.interval());
        }
    }
}
