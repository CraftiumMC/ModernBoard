package net.craftium.modernboard.boards;

import java.util.List;

public record SidebarSettings(int priority, List<Line> lines)
{
    public static class Line
    {
        private final int interval;
        private final List<String> frames;

        private int lastTick;

        public Line(int interval, List<String> frames)
        {
            this.interval = interval;
            this.frames = frames;
        }

        public int interval()
        {
            return interval;
        }

        public List<String> frames()
        {
            return frames;
        }

        public int lastTick()
        {
            return lastTick;
        }

        public void setLastTick(int lastTick)
        {
            this.lastTick = lastTick;
        }

        @Override
        public String toString()
        {
            return "Line{" +
                    "interval=" + interval +
                    ", frames=" + frames +
                    ", lastTick=" + lastTick +
                    '}';
        }
    }
}
