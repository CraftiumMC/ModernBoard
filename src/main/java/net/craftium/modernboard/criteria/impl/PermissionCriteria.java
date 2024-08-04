package net.craftium.modernboard.criteria.impl;

import net.craftium.modernboard.criteria.Criteria;
import org.bukkit.entity.Player;

public class PermissionCriteria implements Criteria
{
    @Override
    public boolean isMet(Player player, String value)
    {
        return player.hasPermission(value);
    }
}
