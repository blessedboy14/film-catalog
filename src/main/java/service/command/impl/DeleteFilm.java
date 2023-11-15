package service.command.impl;

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

/**
 * Class for executing requests to /film/id/delete
 */
public class DeleteFilm extends GetAndPostCommand {
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    private String getFilmIdFromUrl(String url) {
        url = url.substring(0, url.lastIndexOf('/'));
        return url.substring(url.lastIndexOf('/')+1);
    }

    /**
     * Showing window with form for submit deleting
     * @param req Request itself
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException {
        CommandAnswer answer = new CommandAnswer();
        HttpSession session = req.getSession();
        String film_id = getFilmIdFromUrl(req.getRequestURL().toString());
        if (session.getAttribute("adminStatus") == "admin") {
            req.setAttribute("film_id", film_id);
            answer.setRedirect(false);
            answer.setForwardPage("/views/delete-film.jsp");
        } else {
            answer.setRedirect(false);
            answer.setForwardPage(JspPageName.ERROR_PAGE);
        }
        return answer;
    }

    /**
     * Deleting film itself
     * @param req
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req) throws CommandException {
        HttpSession session = req.getSession();
        String film_id = req.getParameter("id");
        if (session.getAttribute("adminStatus") == "admin" && film_id != null) {
            if (req.getParameter("confirm") != null) {
                CatalogService catalogService = ServiceFactory.getInstance().getCatalogService();
                try {
                    Result result = catalogService.deleteFilm(film_id);
                    return new CommandAnswer("", "/films", true);
                } catch (ServiceException e) {
                    throw new CommandException("Error, when deleting film", e);
                }
            } else {
                return new CommandAnswer("", "/films", true);
            }
        } else {
            return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
    }
}
