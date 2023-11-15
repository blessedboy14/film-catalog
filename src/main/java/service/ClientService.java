package service;

import beans.Result;
import beans.Review;
import beans.User;
import service.exception.ServiceException;

import java.util.List;
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
    Result signIn(String login, String password) throws ServiceException;

    /**
     * Register user, checking input data, calling DAO method
     * @param user Target User object with fields
     * @return Result of registration
     * @throws ServiceException
     */
    Result registration(User user) throws ServiceException;

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
    Result addUserReview(Map<String, String> data) throws ServiceException;

    /**
     * List all users for admin management
     * @return List of all users
     * @throws ServiceException
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Method for blocking/unblocking users
     * @return
     * @throws ServiceException
     */
    Result changeUserStatus(String email) throws ServiceException;
}
