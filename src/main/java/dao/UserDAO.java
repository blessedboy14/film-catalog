package dao;

import beans.RESULT;
import beans.Review;
import beans.User;
import dao.exception.DAOException;

import java.util.Map;

public interface UserDAO {
    RESULT signIn(String login, String password) throws DAOException;
    RESULT registration(User user) throws DAOException;
    Review getUserReview(String email, String film_id) throws DAOException;
    RESULT addUserReview(Map<String, String> data) throws DAOException;
}
