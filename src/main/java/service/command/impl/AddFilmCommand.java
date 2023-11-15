package service.command.impl;

import beans.Genre;
import beans.MethodsClass;
import beans.Result;
import controller.JspPageName;
import service.CatalogService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that will handle requests to /add-film command, represent adding a film to database logic use both GET and POST
 */
public class AddFilmCommand extends GetAndPostCommand {
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    /**
     * Show form for admin
     * @param req Request itself
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException {
        CommandAnswer answer = new CommandAnswer();
        HttpSession session = req.getSession();
        if (session.getAttribute("adminStatus") == "admin") {
            req.setAttribute("genres", Genre.values());
            answer.setRedirect(false);
            answer.setForwardPage("views/add.jsp");
        } else {
            answer.setRedirect(false);
            answer.setForwardPage(JspPageName.ERROR_PAGE);
        }
        return answer;
    }

    /**
     * Adding a film after submitting form
     * @param req
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req) throws CommandException {
        CommandAnswer answer = new CommandAnswer();
        answer.setRedirect(false);
        answer.setForwardPage("views/add.jsp");
        Map<String, Object> data = new HashMap<>();
        data.put("title", req.getParameter("title"));
        data.put("firstName", req.getParameter("firstName"));
        data.put("secondName", req.getParameter("secondName"));
        data.put("genres[]", req.getParameterValues("genres[]"));
        data.put("stars", req.getParameter("stars"));
        data.put("releaseYear", req.getParameter("releaseYear"));
        data.put("imdbRating", req.getParameter("imdbRating"));
        data.put("ageLimit", req.getParameter("ageLimit"));
        data.put("duration", req.getParameter("duration"));
        if (!data.containsValue(null)){
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CatalogService catalogService = serviceFactory.getCatalogService();
            try{
                Result result = catalogService.addFilm(data);
                if (result == Result.ADD_FILM_OK) {
                    req.setAttribute("errorFilm", "added");
                } else {
                    String errorMessage = MethodsClass.chooseErrorMessage(result);
                    req.setAttribute("errorFilm", errorMessage);
                }
                return answer;
            } catch (ServiceException e) {
                throw new RuntimeException("Error with film adding", e);
            }
        } else {
            return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
    }
}
