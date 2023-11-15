package dao.impl;

import beans.*;
import dao.FilmDAO;
import dao.builder.SQLQueryBuilder;
import dao.exception.DAOException;
import dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLFilmDAO implements FilmDAO {
    private Connection con;
    private final String[] DB = {"wt_lab2.films", "wt_lab2.film_genres", "wt_lab2.genres", "wt_lab2.reviews"};
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Statement stmt;
    private ResultSet rs;
    private final SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder();

    private void closeDBObjects() throws DAOException {
        connectionPool.releaseConnection(con);
        try { if(stmt!=null) {stmt.close();}} catch(SQLException e) {throw new DAOException("Stmt close except", e); }
        try { if(rs!=null) {rs.close();} } catch(SQLException e) { throw new DAOException("RS close except", e);}
    }
    @Override
    public Result addFilm(Film film) throws DAOException {
        if (checkFilmAvailability(DB[0], new String[]{"title", "year"}, new String[]{film.getTitle(),
                String.valueOf(film.getReleaseYear())})){
                String[] fields = new String[]{"year", "length", "title", "stars", "director", "rating",
                "censor_rating", "reviews"};
                String query = sqlQueryBuilder.insertWithFields(DB[0], fields,
                        new String[]{String.valueOf(film.getReleaseYear()), String.valueOf(film.getDurationInMinutes()),
                                film.getTitle(), film.getStars(), film.getDirector().toString(),
                                film.getIMDbRating(), film.getAgeLimit(), "0"});
                try {
                    con = connectionPool.getConnection();
                    stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    int id = getFilmId();
                    addGenres(film.getFilmGenres(), id);
                    return Result.ADD_FILM_OK;
                } catch (SQLException e) {
                    throw new DAOException(e);
                } finally {
                    closeDBObjects();
                }
        } else {
            return Result.FILM_EXIST;
        }
    }

    @Override
    public Result deleteFilm(String film_id) throws DAOException {
        String query = sqlQueryBuilder.deleteWhere(DB[0], "film_id", film_id);
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            int res = stmt.executeUpdate(query);
            System.out.println(res);
            return Result.DELETE_FILM_OK;
        } catch (SQLException e) {
            throw new DAOException("DAO error in film deleting", e);
        } finally {
            closeDBObjects();
        }
    }

    private int getFilmId() throws SQLException {
        String query = sqlQueryBuilder.selectLastId(DB[0]);
        rs = stmt.executeQuery(query);
        rs.next();
        return rs.getInt(1);
    }

    private void addGenres(List<Genre> genres, int film_id) throws SQLException {
        for (Genre genre: genres) {
            String query = sqlQueryBuilder.selectValue("wt_lab2.genres",
                    new String[]{"genre_id"}, "name", genre.getName());
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("genre_id");
                query = sqlQueryBuilder.insertOne("wt_lab2.film_genres",
                        new String[]{String.valueOf(film_id), String.valueOf(id)});
                stmt.executeUpdate(query);
            }
        }
    }

    private boolean checkFilmAvailability(String tableName, String[] valuesNames, String[] values) throws DAOException {
        String query = sqlQueryBuilder.selectWhere(tableName, valuesNames, values);
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return !rs.next();
        } catch (SQLException | DAOException e) {
            throw new DAOException("Check value error", e);
        } finally {
            closeDBObjects();
        }
    }

    private String[] divideStringInTwo(String input) {
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {index = i;}
        }
        return new String[]{input.substring(0, index), input.substring(index)};
    }

    private List<Genre> receiveGenresList(int film_id) throws  DAOException {
        String query = sqlQueryBuilder.selectAllWhere(DB[1], "film_id", String.valueOf(film_id));
        try(Statement stmtGetGenres = con.createStatement();
            ResultSet newRs = stmtGetGenres.executeQuery(query);
            Statement statement = con.createStatement();) {
            List<Genre> genres = new ArrayList<>();
            while (newRs.next()) {
                int genre_id = newRs.getInt("genre_id");
                query = sqlQueryBuilder.selectValue(DB[2], new String[]{"name"}, "genre_id", String.valueOf(genre_id));
                ResultSet genre = statement.executeQuery(query);
                if (genre.next()) {
                    genres.add(Genre.get(genre.getString("name")));
                }
            }
            return genres;
        } catch (SQLException e) {
            throw new DAOException("Genres receive error", e);
        }
    }

    private Film parseResultSetToFilm(ResultSet resultSet) throws SQLException, DAOException {
        String[] directorData = divideStringInTwo(resultSet.getString("director"));
        Director director = new Director(directorData[0], directorData[1]);
        int id = resultSet.getInt("film_id");
        List<Genre> genres = receiveGenresList(id);
        return new Film(director, resultSet.getString("title"),resultSet.getInt("year"),
                resultSet.getString("rating"), resultSet.getString("censor_rating"),
                resultSet.getInt("length"), genres, resultSet.getString("stars"), id);
    }

    @Override
    public List<Film> listFilms(int toList) throws DAOException {
        String query = sqlQueryBuilder.selectWithOrderAndLimit(DB[0], "rating", toList);
        List<Film> films = new ArrayList<>();
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                films.add(parseResultSetToFilm(rs));
            }
            return films;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeDBObjects();
        }
    }

    @Override
    public List<Film> listAll() throws DAOException {
        return null;
    }

    @Override
    public Film getFilm(String film_id) throws DAOException {
        String query = sqlQueryBuilder.selectAllWhere(DB[0], "film_id", film_id);
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return parseResultSetToFilm(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Detail film get error", e);
        } finally {
            closeDBObjects();
        }
    }

    @Override
    public List<Film> searchFilms(String title) throws DAOException {
        String query = sqlQueryBuilder.searchFilmBy(DB[0], "title", title);
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<Film> films = new ArrayList<>();
            while(rs.next()) {
                films.add(parseResultSetToFilm(rs));
            }
            return films;
        } catch (SQLException e) {
            throw new DAOException("Search error", e);
        } finally {
            closeDBObjects();
        }
    }

    @Override
    public List<Review> getReviews(String film_id) throws DAOException {
        String query = sqlQueryBuilder.selectWhereWithOrderAndLimit(DB[3], "film_id", film_id,
                "review_date", 100);
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<Review> reviews = new ArrayList<>();
            while(rs.next()) {
                reviews.add(sqlQueryBuilder.parseRSToReview(rs));
            }
            return reviews;
        } catch (SQLException e) {
            throw new DAOException("review take error", e);
        }
    }
}
