package service.impl;

import beans.*;
import dao.FilmDAO;
import dao.exception.DAOException;
import dao.factory.DAOFactory;
import org.apache.log4j.Logger;
import service.CatalogService;
import service.exception.ServiceException;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogServiceImpl implements CatalogService {

    private static final Logger log = Logger.getLogger(CatalogServiceImpl.class);
    @Override
    public Result addFilm(Map<String, Object> data) throws ServiceException {
        Film newFilm = checkFilmInputData(data);
        if (newFilm != null) {
            DAOFactory daoFactory = DAOFactory.getInstance();
            FilmDAO filmDAO = daoFactory.getFilmDAO();
            try {
                log.info(String.format("Adding film with title %s", data.get("title")));
                return filmDAO.addFilm(newFilm);
            } catch (DAOException e) {
                log.error(String.format("Error while adding film with title %s", data.get("title")));
                throw new ServiceException("Error in DAO film add", e);
            }
        } else {
            return Result.ADD_FILM_PARAMETER_ERROR;
        }
    }

    private Film checkFilmInputData(Map<String, Object> data) {
        boolean isValidData;
        isValidData = checkNameValid((String) data.get("firstName")) && checkNameValid((String) data.get("secondName"))
                && checkRatingValid((String) data.get("imdbRating")) && checkDurationValid((String) data.get("duration"))
                && checkAgeLimit((String) data.get("ageLimit")) && checkReleaseYear((String) data.get("releaseYear"));
        if (isValidData) {
            String firstName = formatWord((String) data.get("firstName"));
            String secondName = formatWord((String) data.get("secondName"));
            List<Genre> genres = new ArrayList<>();
            for (String str: (String[])data.get("genres[]")) {
                genres.add(Genre.get(str));
            }
            int releaseYear;
            try {
                releaseYear = Integer.parseInt((String) data.get("releaseYear"));
            } catch (NumberFormatException e){
                return null;
            }
            return new Film(new Director(firstName, secondName), (String) data.get("title"), releaseYear,
                    (String) data.get("imdbRating"), (String) data.get("ageLimit"),
                    Integer.parseInt((String) data.get("duration")), genres, (String) data.get("stars"));
        } else {
            return null;
        }
    }

    private boolean checkReleaseYear(String releaseYear) {
        int year;
        try {
            year = Integer.parseInt(releaseYear);
            return year > 1892 && year <= LocalDate.now().getYear();
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean checkAgeLimit(String ageLimit) {
        Set<String> validCensor = Stream.
                of("A", "U", "UA", "R", "NA", "0", "G", "PG", "PG-13").collect(Collectors.toSet());
        return validCensor.contains(ageLimit);
    }

    private boolean checkDurationValid(String duration) {
        String regex = "^([1-9][0-9]?|[1-2][0-9]{2})$";
        return Pattern.compile(regex).matcher(duration).matches();
    }

    private boolean checkRatingValid(String rating) {
        String regex = "^[1-9](\\.[0-9])?$";
        return Pattern.compile(regex).matcher(rating).matches();
    }

    private String formatWord(String word) {
        String lowercaseWord = word.toLowerCase();
        return lowercaseWord.substring(0, 1).toUpperCase() + lowercaseWord.substring(1);
    }

    private boolean checkNameValid(String name) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    @Override
    public List<Film> listFilms(int toList) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FilmDAO filmDAO = daoFactory.getFilmDAO();
        try {
            log.info(String.format("List films of amount of %d", toList));
            return filmDAO.listFilms(toList);
        } catch (DAOException e) {
            log.error("Error while listing");
            throw new ServiceException("List error", e);
        }
    }

    @Override
    public List<Film> listAll() throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FilmDAO filmDAO = daoFactory.getFilmDAO();
        try {
            log.info("List all");
            return filmDAO.listAll();
        } catch (DAOException e) {
            log.error("Error while list all");
            throw new ServiceException("List error", e);
        }
    }

    private boolean checkFilmId(String id) {
        try{
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Film getFilm(String film_id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FilmDAO filmDAO = daoFactory.getFilmDAO();
        Film film = null;
        if (checkFilmId(film_id)) {
            try {
                log.info(String.format("Get film with id %s", film_id));
                return filmDAO.getFilm(film_id);
            } catch (DAOException e) {
                log.error(String.format("Error while getting film with id %s", film_id));
                throw new ServiceException("Get Film error", e);
            }
        } else {
            return film;
        }
    }

    @Override
    public List<Film> searchFilms(String title) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FilmDAO filmDAO = daoFactory.getFilmDAO();
        try {
            log.info(String.format("Search film with title %s", title));
            return filmDAO.searchFilms(title);
        } catch (DAOException e) {
            log.error(String.format("Error with searching film with title %s", title));
            throw new ServiceException("Search error", e);
        }
    }

    @Override
    public Result deleteFilm(String film_id) throws ServiceException {
        FilmDAO filmDAO = DAOFactory.getInstance().getFilmDAO();
        try {
            log.info(String.format("Deleting film with id %s", film_id));
            return filmDAO.deleteFilm(film_id);
        } catch (DAOException e) {
            log.error(String.format("Error with deleting film with id %s", film_id));
            throw new ServiceException("Film deleting error", e);
        }
    }

    @Override
    public List<Review> getFilmReviews(String film_id) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        FilmDAO filmDAO = daoFactory.getFilmDAO();
        try {
            log.info(String.format("Get reviews for film with id %s", film_id));
            return filmDAO.getReviews(film_id);
        } catch (DAOException e) {
            log.error(String.format("Error while getting reviews for film with id %s", film_id));
            throw new ServiceException("review getting error", e);
        }
    }
}
