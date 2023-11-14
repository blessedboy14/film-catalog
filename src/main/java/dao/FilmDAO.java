package dao;

import beans.Film;
import beans.RESULT;
import beans.Review;
import dao.exception.DAOException;

import java.util.List;

public interface FilmDAO {
    RESULT addFilm(Film film) throws DAOException;
    RESULT deleteFilm(String film_id) throws DAOException;
    List<Film> listFilms(int toList) throws DAOException;
    List<Film> listAll() throws DAOException;
    Film getFilm(String film_id) throws DAOException;
    List<Film> searchFilms(String title) throws DAOException;
    List<Review> getReviews(String film_id) throws DAOException;
}
