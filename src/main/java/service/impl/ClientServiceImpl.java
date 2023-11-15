package service.impl;

import beans.Result;
import beans.Review;
import beans.User;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.factory.DAOFactory;
import org.apache.log4j.Logger;
import service.ClientService;
import service.exception.ServiceException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientServiceImpl implements ClientService {
    private static final Logger log = Logger.getLogger(ClientServiceImpl.class);
    @Override
    public Result signIn(String login, String password) throws ServiceException {
        if (isEmailValid(login)) {
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            try {
                log.info(String.format("User %s sign in", login));
                return userDAO.signIn(login, password);
            } catch (DAOException e) {
                log.error(String.format("Error while getting sign in of %s", login));
                throw new ServiceException("Error in DAO signIn", e);
            }
        } else {
            return Result.INCORRECT_EMAIL;
        }
    }

    private boolean isEmailValid(String email) {
        final String[] validDomens = {"mail.ru", "gmail.com", "yandex.ru", "study.bsuir.by", "yahoo.com", "outlook.com",
        "live.com", "admin.com"};
        final String EMAIL_REGEX = "^[A-Za-z0-9_.]+";
        if (!email.contains("@")) {return false;}
        String firstPart = email.substring(0, email.indexOf("@"));
        String secondPart = email.substring(email.indexOf("@") + 1);
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(firstPart);
        return matcher.find() && Arrays.asList(validDomens).contains(secondPart);
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        final String[] validStarts = {"+7", "+375"};
        if (Arrays.asList(validStarts).contains(phoneNumber.substring(0, 2)) ||
                Arrays.asList(validStarts).contains(phoneNumber.substring(0, 4))) {
            return (phoneNumber.substring(0, 2).equals(validStarts[0])  && phoneNumber.length() == 12) ||
                    (phoneNumber.substring(0, 4).equals(validStarts[1]) && phoneNumber.length() == 13);
        } else {
            return false;
        }
    }

    @Override
    public Result changeUserStatus(String email) throws ServiceException {
        if (isEmailValid(email)) {
            UserDAO dao = DAOFactory.getInstance().getUserDAO();
            try {
                return dao.changeUserStatus(email);
            } catch (DAOException e) {
                throw new ServiceException("Error while changing status");
            }
        } else {
            return Result.INCORRECT_EMAIL;
        }
    }

    @Override
    public Result registration(User user) throws ServiceException {
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        if (isEmailValid(email)) {
            if (isPhoneNumberValid(phoneNumber)) {
                DAOFactory daoFactory = DAOFactory.getInstance();
                UserDAO userDAO = daoFactory.getUserDAO();
                try {
                    log.info(String.format("User %s register", email));
                    return userDAO.registration(user);
                } catch (DAOException e) {
                    log.error(String.format("Error while register of %s", email));
                    throw new ServiceException("DAO register error", e);
                }
            } else {
                return Result.INCORRECT_PHONE;
            }
        } else {
            return Result.INCORRECT_EMAIL;
        }
    }

    @Override
    public Review getUserReview(String email, String film_id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        if (isEmailValid(email)) {
            try {
                log.info(String.format("Return %s review", email));
                return userDAO.getUserReview(email, film_id);
            } catch (DAOException e) {
                log.error(String.format("Error while getting review of %s", email));
                throw new ServiceException("Find review error", e);
            }
        } else {
            return null;
        }
    }

    @Override
    public Result addUserReview(Map<String, String> data) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();
        if (isEmailValid(data.get("email"))) {
            try {
                log.info(String.format("Adding %s review", data.get("email")));
                return userDAO.addUserReview(data);
            } catch (DAOException e) {
                log.error(String.format("Error while adding %s review", data.get("email")));
                throw new ServiceException("Error with adding a review", e);
            }
        } else {
            return Result.INCORRECT_EMAIL;
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        try {
            return dao.getAllUsers();
        } catch (DAOException e){
            throw new ServiceException("Error with getting all users");
        }
    }
}
