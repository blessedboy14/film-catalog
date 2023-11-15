package dao;

import beans.Result;
import beans.Review;
import beans.User;
import dao.exception.DAOException;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    Result signIn(String login, String password) throws DAOException;
    Result registration(User user) throws DAOException;
    Review getUserReview(String email, String film_id) throws DAOException;
    Result addUserReview(Map<String, String> data) throws DAOException;
    List<User> getAllUsers() throws DAOException;
    Result changeUserStatus(String email) throws DAOException;
}
