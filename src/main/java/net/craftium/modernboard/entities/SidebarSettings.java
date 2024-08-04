package net.craftium.modernboard.entities;

import net.craftium.modernboard.criteria.Criteria;
import net.craftium.modernboard.criteria.CriteriaSettings;
import net.craftium.modernboard.utils.FrameList;

import java.util.List;

public record SidebarSettings(int priority, Criteria.Operator criteriaOperator,
                              List<CriteriaSettings> criteria, List<Line> lines)
{
    public record Line(int index, int interval, FrameList frames) {}
}
