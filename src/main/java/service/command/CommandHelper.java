package service.command;

import service.command.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class for choosing right handler for input command
 */
public final class CommandHelper {
    private static final CommandHelper instance = new CommandHelper();
    private Map<CommandName, ICommand> commands = new HashMap<>();

    /**
     * Constructor with initializing of map of command handlers by command name
     */
    public CommandHelper() {
        commands.put(CommandName.ABOUT, new AboutCommand());
        commands.put(CommandName.START, new StartCommand());
        commands.put(CommandName.NO_SUCH_COMMAND, new NoSuchCommand());
        commands.put(CommandName.SIGN_IN, new SignInCommand());
        commands.put(CommandName.REGISTER, new RegisterCommand());
        commands.put(CommandName.LOG_OUT, new LogOutCommand());
        commands.put(CommandName.ADD_FILM, new AddFilmCommand());
        commands.put(CommandName.LIST_FILMS, new ListFilmsCommand());
        commands.put(CommandName.DETAIL, new DetailFilmCommand());
        commands.put(CommandName.REVIEW, new AddReviewCommand());
        commands.put(CommandName.DELETE_FILM, new DeleteFilm());
        commands.put(CommandName.CHANGE_LOCALE, new LocaleChangeCommand());
    }

    public static CommandHelper getInstance() {
        return instance;
    }

    /**
     * Method for getting corresponding command for input command by name
     * @param commandName String name of command, getting from request
     * @return ICommand member, that will handle request
     */
    public ICommand getCommand(String commandName){
        ICommand command;
        try {
            CommandName name = CommandName.fromString(commandName);
            command = commands.get(name);
        } catch (IllegalArgumentException e) {
            if (Objects.equals(commandName, "")) {
                command = commands.get(CommandName.START);
            } else {
                command = commands.get(CommandName.NO_SUCH_COMMAND);
            }
        }
        return command;
    }
}
