package service;

import beans.Film;
import beans.Result;
import beans.Review;
import service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Interface for all interactions with catalog(films)
 */
public interface CatalogService {
    /**
     * Adding film to database(calling DAO method inside)
     * @param data Film data by fields
     * @return RESULT of film adding operation(like SUCCESS, ERROR and others)
     * @throws ServiceException
     */
    Result addFilm(Map<String, Object> data) throws ServiceException;

    /**
     * Listing films(first toList sorted by rating)
     * @param toList How many to list
     * @return List of Films from database
     * @throws ServiceException
     */
    List<Film> listFilms(int toList) throws ServiceException;

    /**
     * List all films from database
     * @return List of all films
     * @throws ServiceException
     */
    List<Film> listAll() throws ServiceException;

    /**
     * Getting film from database by film_id
     * @param film_id id of wanted film
     * @return Film or null is not found
     * @throws ServiceException
     */
    Film getFilm(String film_id) throws ServiceException;

    /**
     * Search films that contains string in their title
     * @param title Target string
     * @return List of films or empty list if not found
     * @throws ServiceException
     */
    List<Film> searchFilms(String title) throws ServiceException;

    /**
     * Retrieve all film reviews
     * @param film_id Target film id
     * @return List of reviews or empty list if not found
     * @throws ServiceException
     */
    List<Review> getFilmReviews(String film_id) throws ServiceException;

    /**
     * Deleting film with corresponding id
     * @param film_id Target film id
     * @return RESULT of deleting
     * @throws ServiceException
     */
    Result deleteFilm(String film_id) throws ServiceException;
}
