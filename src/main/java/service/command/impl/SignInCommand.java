package service.command.impl;

import beans.MethodsClass;
import beans.RESULT;
import service.ClientService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class for executing requests to /signin for both GET and POST
 */
public class SignInCommand extends GetAndPostCommand {

    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    /**
     * Showing Sign In form
     * @param req Request itself
     * @return CommandAnswer
     */
    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) {
        HttpSession session = req.getSession();
        CommandAnswer answer = new CommandAnswer();
        answer.setRedirect(false);
        if (session.getAttribute("loggedIn")!= null){
            answer.setForwardPage("/views/base/index.jsp");
        } else {
            answer.setForwardPage("/views/signIn.jsp");
        }
        return answer;
    }

    /**
     * Signing in new user
     * @param req
     * @return CommandAnswer
     */
    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req)  {
        CommandAnswer answer = new CommandAnswer();
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        if (email != null && pass != null) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ClientService clientService = serviceFactory.getClientService();
            try {
                RESULT result = clientService.signIn(email, pass);
                if (result == RESULT.SIGN_IN_OK || result == RESULT.SIGN_IN_OK_ADMIN) {
                    HttpSession session = req.getSession();
                    session.setAttribute("loggedIn", email);
                    session.setAttribute("adminStatus", result == RESULT.SIGN_IN_OK ? null : "admin");
                    answer.setRedirect(true);
                    answer.setRedirectUrl("/");
                    req.setAttribute("error", null);
                } else {
                    req.setAttribute("error", MethodsClass.chooseErrorMessage(result));
                    answer.setRedirect(false);
                    answer.setForwardPage("/views/signIn.jsp");
                }
            } catch (ServiceException e) {
                throw new RuntimeException("Sign In error", e);
            }
        } else {
            answer.setForwardPage("/views/signIn.jsp");
        }
        return answer;
    }
}
