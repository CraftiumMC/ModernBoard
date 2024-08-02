package net.craftium.modernboard.command;

import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.SimpleCommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import net.craftium.modernboard.ModernBoard;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

@SuppressWarnings("UnstableApiUsage")
public class CommandManager
{
    private final AnnotationParser<CommandSender> parser;
    private final PaperCommandManager<CommandSender> manager;

    public CommandManager(ModernBoard plugin)
    {
        try
        {
            this.manager = new PaperCommandManager<>(
                plugin,
                CommandExecutionCoordinator.simpleCoordinator(),
                Function.identity(),
                Function.identity());
            this.parser = new AnnotationParser<>(manager, CommandSender.class,
                params -> SimpleCommandMeta.empty());
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to initialize command manager", e);
        }

        manager.registerAsynchronousCompletions();
    }

    public void registerCommands(Object... commands)
    {
        for(Object command : commands)
            parser.parse(command);
    }

    public PaperCommandManager<?> getAPI()
    {
        return manager;
    }
}
