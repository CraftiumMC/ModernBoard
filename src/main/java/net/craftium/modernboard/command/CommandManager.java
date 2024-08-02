package net.craftium.modernboard.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.craftium.modernboard.ModernBoard;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.SimpleCommandMeta;
import org.incendo.cloud.paper.PaperCommandManager;

@SuppressWarnings("UnstableApiUsage")
public class CommandManager
{
    private final AnnotationParser<CommandSourceStack> parser;
    private final PaperCommandManager<CommandSourceStack> manager;

    public CommandManager(ModernBoard plugin)
    {
        this.manager = PaperCommandManager.builder(SenderMapper.identity())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(plugin);
        this.parser = new AnnotationParser<>(manager, CommandSourceStack.class,
                params -> SimpleCommandMeta.empty());
    }

    public void registerCommands(Object... commands)
    {
        for(Object command : commands)
            parser.parse(command);
    }

    public PaperCommandManager<CommandSourceStack> getAPI()
    {
        return manager;
    }
}
