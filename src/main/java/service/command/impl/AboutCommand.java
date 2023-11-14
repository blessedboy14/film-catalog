package service.command.impl;

import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command that will handle requests to /about page
 */
public class AboutCommand implements ICommand {

    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return new CommandAnswer("/views/about.jsp", "", false);
    }
}
