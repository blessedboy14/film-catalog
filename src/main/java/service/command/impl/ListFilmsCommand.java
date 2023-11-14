package service.command.impl;

import beans.Film;
import service.CatalogService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class for executing requests to /films
 */
public class ListFilmsCommand extends GetAndPostCommand {
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    /**
     * Showing 30 most rated films
     * @param req Request itself
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CatalogService catalogService = serviceFactory.getCatalogService();
        try {
            List<Film> films = catalogService.listFilms(30);
            if (films.isEmpty()) {
                req.setAttribute("error", "No films or error");
            } else {
                req.setAttribute("error", null);
                req.setAttribute("films", films);
            }
        } catch (ServiceException e){
            req.setAttribute("error", "No films or error");
        }
        return new CommandAnswer("views/films.jsp", "", false);
    }

    /**
     * Showing 30 or less finded films by using search tool
     * @param req
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req) throws CommandException {
        String searchTitle = req.getParameter("searchTitle");
        if (searchTitle != null) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            CatalogService catalogService = serviceFactory.getCatalogService();
            try {
                List<Film> films = catalogService.searchFilms(searchTitle);
                req.setAttribute("error", null);
                req.setAttribute("films", films);
            } catch (ServiceException e) {
                req.setAttribute("error", "No films or error");
            }
        } else {
            return new CommandAnswer("", "/films", true);
        }
        return new CommandAnswer("views/films.jsp", "", false);
    }
}
