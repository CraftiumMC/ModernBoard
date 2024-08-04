package net.craftium.modernboard.config.loader;

import net.craftium.modernboard.criteria.Criteria.Operator;
import net.craftium.modernboard.criteria.CriteriaSettings;
import net.craftium.modernboard.criteria.CriteriaType;
import net.craftium.modernboard.entities.SidebarSettings;
import net.craftium.modernboard.utils.FrameList;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SidebarSettingsSerializer implements TypeSerializer<SidebarSettings>
{
    @Override
    public SidebarSettings deserialize(Type type, ConfigurationNode node) throws SerializationException
    {
        AtomicInteger index = new AtomicInteger();
        List<SidebarSettings.Line> lines = new LinkedList<>();
        for(ConfigurationNode lineConfig : node.node("lines").childrenList())
        {
            lines.add(new SidebarSettings.Line(
                    index.getAndIncrement(),
                    lineConfig.node("interval").getInt(100),
                    new FrameList(lineConfig.node("frames").getList(String.class))
            ));
        }

        ConfigurationNode settingsNode = node.node("settings");
        ConfigurationNode criteriaNode = settingsNode.node("criteria");
        var criteriaMap = criteriaNode.childrenMap();
        List<CriteriaSettings> criteria = new ArrayList<>(criteriaMap.size());

        for(ConfigurationNode criterion : criteriaMap.values())
        {
            // Operator setting will also appear in this node, but we only want maps here
            if(!criterion.isMap())
                continue;

            criteria.add(new CriteriaSettings(
                    criterion.node("type").get(CriteriaType.class),
                    criterion.node("value").getString()
            ));
        }

        return new SidebarSettings(
                settingsNode.node("priority").getInt(),
                criteriaNode.node("operator").get(Operator.class, Operator.OR),
                criteria,
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

        ConfigurationNode settingsNode = node.node("settings");
        settingsNode.node("priority").set(obj.priority());

        AtomicInteger index = new AtomicInteger(1);
        ConfigurationNode criteriaNode = settingsNode.node("criteria");

        if(obj.criteriaOperator() != Operator.OR)
            criteriaNode.node("operator").set(obj.criteriaOperator().name());

        for(CriteriaSettings criterion : obj.criteria())
        {
            ConfigurationNode criterionNode = criteriaNode.node(index.getAndIncrement());
            criterionNode.node("type").set(criterion.type().name());
            criterionNode.node("value").set(criterion.value());
        }

        ConfigurationNode linesParent = node.node("lines");
        for(SidebarSettings.Line line : obj.lines())
        {
            ConfigurationNode linesChildren = linesParent.appendListNode();
            linesChildren.node("frames").setList(String.class, line.frames());
            linesChildren.node("interval").set(line.interval());
        }
    }
}
