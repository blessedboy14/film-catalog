package service.command.impl;

import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for executing requests to / page
 */
public class StartCommand implements ICommand {

    /**
     * Returning start page
     * @param req HttpServletRequest object
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return new CommandAnswer("/views/base/index.jsp", "", false);
    }
}
