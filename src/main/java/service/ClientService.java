package service;

import beans.RESULT;
import beans.Review;
import beans.User;
import service.exception.ServiceException;
import java.util.Map;

/**
 * Interface to interact with client(or user)
 */
public interface ClientService {

    /**
     * Signing in user, checking input data, calling DAO method
     * @param login Email(or login) of user format admin@gmail.com
     * @param password Password of user, any format
     * @return Result of signing in
     * @throws ServiceException
     */
    RESULT signIn(String login, String password) throws ServiceException;

    /**
     * Register user, checking input data, calling DAO method
     * @param user Target User object with fields
     * @return Result of registration
     * @throws ServiceException
     */
    RESULT registration(User user) throws ServiceException;

    /**
     * Retrieving review of target user
     * @param email Email of user
     * @param film_id Id of film
     * @return Review object
     * @throws ServiceException
     */
    Review getUserReview(String email, String film_id) throws ServiceException;

    /**
     * Adding or editing target user review
     * @param data Review fields
     * @return Result of adding or editing review
     * @throws ServiceException
     */
    RESULT addUserReview(Map<String, String> data) throws ServiceException;
}
