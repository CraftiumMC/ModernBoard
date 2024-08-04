package net.craftium.modernboard.criteria;

import org.bukkit.entity.Player;

public interface Criteria
{
    boolean isMet(Player player, String value);

    enum Operator
    {
        AND, OR
    }
}
