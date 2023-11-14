package service.command.impl;

import beans.Film;
import beans.Review;
import service.CatalogService;
import service.command.CommandAnswer;
import service.command.ICommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class for executing requests to /film/id
 */
public class DetailFilmCommand implements ICommand {
    /**
     * Returning detail film information
     * @param req HttpServletRequest object
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        String url = req.getRequestURL().toString();
        String film_id = url.substring(url.lastIndexOf('/') + 1);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CatalogService catalogService = serviceFactory.getCatalogService();
        Film film;
        List<Review> reviews;
        try {
            film = catalogService.getFilm(film_id);
            reviews = catalogService.getFilmReviews(film_id);
        } catch (ServiceException e) {
            throw new RuntimeException("Some error", e);
        }
        req.setAttribute("film", film);
        req.setAttribute("reviews", reviews);
        req.setAttribute("url", film_id);
        return new CommandAnswer("/views/detail.jsp", "", false);
    }
}
