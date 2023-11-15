package service.command.impl;

import beans.Result;
import beans.User;
import controller.JspPageName;
import service.ClientService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UsersManageCommand extends GetAndPostCommand {

    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException {
        ClientService service = ServiceFactory.getInstance().getClientService();
        try {
            List<User> users = service.getAllUsers();
            req.setAttribute("users", users);
            return new CommandAnswer("/views/users.jsp", "", false);
        } catch (ServiceException e) {
            return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
    }

    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req) throws CommandException {
        String email = req.getParameter("email");
        ClientService service = ServiceFactory.getInstance().getClientService();
        try {
            Result res = service.changeUserStatus(email);
            if (res != Result.CHANGE_STATUS_OK) {
                req.setAttribute("error", "Error while changing status");
            }
        } catch (ServiceException e) {
            req.setAttribute("error", "Error while changing status");
        }
        return executeGetReq(req);
    }
}
