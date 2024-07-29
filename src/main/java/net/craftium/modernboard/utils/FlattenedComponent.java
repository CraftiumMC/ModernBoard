package net.craftium.modernboard.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.FlattenerListener;
import net.kyori.adventure.text.format.Style;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FlattenedComponent implements FlattenerListener
{
    private final AtomicInteger index = new AtomicInteger(-1);
    private final Map<Integer, StyledText> byStart = new HashMap<>();
    private final StringBuilder sb = new StringBuilder();
    private final List<StyledText> stack = new LinkedList<>();
    
    public FlattenedComponent() {}

    public Map<Integer, StyledText> byStart()
    {
        return byStart;
    }

    public List<StyledText> stack()
    {
        return stack;
    }

    @Override
    public void pushStyle(Style pushed)
    {
        int idx = index.incrementAndGet();

        StyledText stored = stack.get(idx);
        if(stored == null)
            stack.add(idx, stored = new StyledText(sb.length()));

        stored.style = idx > 0 ? stack.get(idx - 1).style() : Style.empty();
        stored.style = stored.style.merge(pushed);
        byStart.put(sb.length(), stored);
    }

    @Override
    public void component(String text)
    {
        if(!text.isEmpty())
        {
            int idx = index.get();
            if(idx < 0)
                throw new IllegalStateException("No style has been pushed!");

            StyledText stored = stack.get(idx);

            sb.append(text);
            stored.text = text;
            stored.endsAt = sb.length();
        }
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }

    public static class StyledText
    {
        private final int startsAt;

        private boolean fullyAppended;
        private int endsAt;
        private String text;
        private Style style;

        public StyledText(int startsAt)
        {
            this.startsAt = startsAt;
        }

        public boolean isFullyAppended()
        {
            return fullyAppended;
        }
        public boolean isInBounds(int index)
        {
            return index >= startsAt && index < endsAt;
        }

        public Component component()
        {
            this.fullyAppended = true;
            return Component.text(text, style);
        }

        public int endsAt()
        {
            return endsAt;
        }

        public int startsAt()
        {
            return startsAt;
        }

        public String text()
        {
            return text;
        }

        public Style style()
        {
            return style;
        }

        @Override
        public String toString()
        {
            return "StyledText{" +
                    "startsAt=" + startsAt +
                    ", endsAt=" + endsAt +
                    ", text='" + text + '\'' +
                    ", style=" + style +
                    '}';
        }
    }
}
