package net.craftium.modernboard.utils;

import java.util.Collection;
import java.util.LinkedList;

public class FrameList extends LinkedList<String>
{
    public FrameList()
    {
        super();
    }

    public FrameList(Collection<? extends String> c)
    {
        super(c);
    }
}
