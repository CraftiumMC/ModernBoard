package net.craftium.modernboard.boards;

import net.craftium.modernboard.utils.FrameList;

import java.util.List;

public record SidebarSettings(int priority, List<Line> lines)
{
    public static class Line
    {
        private final int index, interval;
        private final FrameList frames;

        private int lastTick;

        public Line(int index, int interval, List<String> frames)
        {
            this.index = index;
            this.interval = interval;
            this.frames = new FrameList(frames);
        }

        public int index()
        {
            return index;
        }

        public int interval()
        {
            return interval;
        }

        public FrameList frames()
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
