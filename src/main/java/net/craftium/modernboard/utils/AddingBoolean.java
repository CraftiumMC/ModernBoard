package net.craftium.modernboard.utils;

import net.craftium.modernboard.criteria.Criteria.Operator;

public class AddingBoolean
{
    private final Operator operator;

    private boolean result;

    public AddingBoolean(Operator operator)
    {
        this.operator = operator;
    }

    public void add(boolean value)
    {
        switch(operator)
        {
            case AND:
                this.result &= value;
                break;
            case OR:
                this.result |= value;
                break;
        }
    }

    public boolean getResult()
    {
        return result;
    }
}
