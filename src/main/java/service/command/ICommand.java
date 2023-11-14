package service.command;

import service.exception.CommandException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for commands received by servlet container
 */
public interface ICommand {
    String POST = "POST";
    String GET = "GET";

    /**
     * Method that will receive request and handle it
     * @param req HttpServletRequest object
     * @return Returns CommandAnswer object, that describe next move of servlet(redirect or forward)
     * @throws CommandException Throw exception on the above levels
     */
    CommandAnswer execute(HttpServletRequest req) throws CommandException;
}
