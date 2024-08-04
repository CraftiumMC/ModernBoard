package net.craftium.modernboard.criteria;

import net.craftium.modernboard.criteria.impl.PermissionCriteria;
import net.craftium.modernboard.criteria.impl.WorldCriteria;

public enum CriteriaType
{
    //EVENT,
    PERMISSION(new PermissionCriteria()),
    WORLD(new WorldCriteria());

    private final Criteria checker;

    CriteriaType(Criteria checker)
    {
        this.checker = checker;
    }

    public Criteria getChecker()
    {
        return checker;
    }
}
