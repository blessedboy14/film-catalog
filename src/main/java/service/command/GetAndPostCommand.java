package service.command;

import controller.JspPageName;
import service.exception.CommandException;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represent command handler for requests with to certain url for both GET and POST methods
 */
public abstract class GetAndPostCommand implements ICommand{
    /**
     * This method will manage GET and POST requests and redirect to specified function
     * @param req HttpServletRequest object
     * @return
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        String method = req.getMethod();
        if (Objects.equals(method, POST)) {
            return executePostReq(req);
        } else if (Objects.equals(method, GET)){
            return executeGetReq(req);
        }
        return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
    }

    /**
     * This method will execute GET request
     * @param req Request itself
     * @return CommandAnswer
     * @throws CommandException
     */
    protected abstract CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException;

    /**
     * This method will execute POST request
     * @param req
     * @return CommandAnswer
     * @throws CommandException
     */
    protected abstract CommandAnswer executePostReq(HttpServletRequest req) throws CommandException;
}
