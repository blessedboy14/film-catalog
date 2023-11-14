package service;

import beans.RESULT;
import beans.Review;
import beans.User;
import service.exception.ServiceException;
import java.util.Map;

public interface ClientService {
    RESULT signIn(String login, String password) throws ServiceException;
    RESULT registration(User user) throws ServiceException;
    Review getUserReview(String email, String film_id) throws ServiceException;
    RESULT addUserReview(Map<String, String> data) throws ServiceException;
}
