package service.command.impl;

import controller.JspPageName;
import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for executing requests that didn't handled by other command
 */
public class NoSuchCommand implements ICommand {

    /**
     * Returning 404 not found page
     * @param req HttpServletRequest object
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
    }
}
