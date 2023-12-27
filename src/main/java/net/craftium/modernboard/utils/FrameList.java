package net.craftium.modernboard.utils;

import java.util.Collection;
import java.util.LinkedList;

public class FrameList extends LinkedList<String>
{
    public FrameList(Collection<? extends String> c)
    {
        super(c);
    }

    public String next()
    {
        String next = removeFirst();
        addLast(next);
        return next;
    }
}
