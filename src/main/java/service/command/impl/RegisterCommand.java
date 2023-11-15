package service.command.impl;

import beans.MethodsClass;
import beans.Result;
import beans.User;
import service.ClientService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class for executing requests to /register use both GET and POST
 */
public class RegisterCommand extends GetAndPostCommand {
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    /**
     * Processing registration
     * @param req
     * @return CommandAnswer
     */
    @Override
    public CommandAnswer executePostReq(HttpServletRequest req) {
        CommandAnswer answer = new CommandAnswer();
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        String phoneNumber = req.getParameter("phoneNumber");
        if (email != null && pass != null && phoneNumber != null) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ClientService clientService = serviceFactory.getClientService();
            try {
                Result result = clientService.registration(new User(email, pass, phoneNumber));
                if (result == Result.REGISTER_OK) {
                    HttpSession session = req.getSession();
                    session.setAttribute("loggedIn", email);
                    answer.setRedirect(true);
                    answer.setRedirectUrl("/");
                    req.setAttribute("error", null);
                } else {
                    String errorMessage = MethodsClass.chooseErrorMessage(result);
                    req.setAttribute("error", errorMessage);
                    answer.setRedirect(false);
                    answer.setForwardPage("views/register.jsp");
                }
            } catch (ServiceException e) {
                throw new RuntimeException("Register error", e);
            }
        }
        return answer;
    }

    /**
     * Showing user register form
     * @param req Request itself
     * @return CommandAnswer
     */
    @Override
    public CommandAnswer executeGetReq(HttpServletRequest req) {
        CommandAnswer answer = new CommandAnswer();
        HttpSession session = req.getSession();
        if (session.getAttribute("loggedIn")!= null){
            answer.setRedirect(true);
            answer.setRedirectUrl("/");
        } else {
            answer.setRedirect(false);
            answer.setForwardPage("views/register.jsp");
        }
        return answer;
    }
}
