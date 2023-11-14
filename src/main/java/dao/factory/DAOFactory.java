package dao.factory;

import dao.FilmDAO;
import dao.UserDAO;
import dao.impl.SQLFilmDAO;
import dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private static final UserDAO sqlUserImpl = new SQLUserDAO();
    private static final FilmDAO sqlFilmDAO = new SQLFilmDAO();

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {return sqlUserImpl;}

    public FilmDAO getFilmDAO() {
        return sqlFilmDAO;
    }
}
