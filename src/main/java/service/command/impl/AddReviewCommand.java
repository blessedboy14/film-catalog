package service.command.impl;

import beans.MethodsClass;
import beans.Result;
import beans.Review;
import controller.JspPageName;
import service.ClientService;
import service.command.CommandAnswer;
import service.command.GetAndPostCommand;
import service.exception.CommandException;
import service.exception.ServiceException;
import service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Class for executing requests to film/id/review/
 */
public class AddReviewCommand extends GetAndPostCommand {
    @Override
    public CommandAnswer execute(HttpServletRequest req) throws CommandException {
        return super.execute(req);
    }

    /**
     * Getting id of film from url
     * @param url
     * @return id of film
     */
    private String getFilmIdFromUrl(String url) {
        url = url.substring(0, url.lastIndexOf('/'));
        return url.substring(url.lastIndexOf('/')+1);
    }

    /**
     * Showing existing review or empty form if not exist
     * @param req Request itself
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executeGetReq(HttpServletRequest req) throws CommandException {
        String film_id = getFilmIdFromUrl(req.getRequestURL().toString());
        HttpSession session = req.getSession();
        if (session.getAttribute("loggedIn") != null) {
            String email = (String) session.getAttribute("loggedIn");
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ClientService clientService = serviceFactory.getClientService();
            try {
                Review userReview = clientService.getUserReview(email, film_id);
                req.setAttribute("review", userReview);
            } catch (ServiceException e){
                throw new RuntimeException("Error finding user review", e);
            }
        } else {
            req.setAttribute("error", "You should be logged in to make reviews");
        }
        return new CommandAnswer("/views/review.jsp", "", false);
    }

    /**
     * Adding new review or editing existing
     * @param req
     * @return CommandAnswer
     * @throws CommandException
     */
    @Override
    protected CommandAnswer executePostReq(HttpServletRequest req) throws CommandException {
        String film_id = getFilmIdFromUrl(req.getRequestURL().toString());
        String reviewText = req.getParameter("reviewText");
        String reviewRating = req.getParameter("imdbRating");
        if (reviewText != null && reviewRating != null && req.getSession().getAttribute("loggedIn")!=null) {
            Map<String, String> data = Map.of("text", reviewText, "rating", reviewRating,
                    "film_id", film_id, "email", (String)req.getSession().getAttribute("loggedIn"));
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ClientService clientService = serviceFactory.getClientService();
            try {
                Result result = clientService.addUserReview(data);
                if (result == Result.REVIEW_ADDED || result == Result.REVIEW_UPDATED) {
                    req.setAttribute("errorBtn", result == Result.REVIEW_UPDATED ? "Review updated" : " Review added");
                } else {
                    String error = MethodsClass.chooseErrorMessage(result);
                    req.setAttribute("errorBtn", error);
                }
                return new CommandAnswer("/views/review.jsp", "", false);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new CommandAnswer(JspPageName.ERROR_PAGE, "", false);
        }
    }
}
