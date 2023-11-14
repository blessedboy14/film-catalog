package service.command.impl;

import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class for executing requests to /logout
 */
public class LogOutCommand implements ICommand {
    /**
     * Log out current user, delete him from session
     * @param req HttpServletRequest object
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        if (session.getAttribute("loggedIn") != null) {
            session.setAttribute("loggedIn", null);
            session.setAttribute("adminStatus", null);
        }
        return new CommandAnswer("", "/", true);
    }
}
