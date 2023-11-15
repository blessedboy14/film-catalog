package dao.impl;

import beans.Result;
import beans.Review;
import beans.Status;
import beans.User;
import dao.builder.SQLQueryBuilder;
import dao.UserDAO;
import dao.exception.DAOException;
import dao.pool.ConnectionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLUserDAO implements UserDAO {

    private Connection con;
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final String[] DB = {"wt_lab2.clients", "wt_lab2.reviews"};
    private final SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder();
    private Statement stmt;
    private ResultSet rs;

    public SQLUserDAO() {}

    @Override
    public Result signIn(String login, String pass) throws DAOException {
        String query = sqlQueryBuilder.selectValue(DB[0], new String[]{"password_hash", "privelege",
                "status"}, "email", login);
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                String hashed = rs.getString("password_hash");
                int privilege = rs.getInt("privelege");
                Status status = Status.values()[rs.getInt("status")];
                if (status != Status.STATUS_BANNED) {
                    boolean res = BCrypt.checkpw(pass, hashed);
                    if (res) {
                        return privilege == 1 ? Result.SIGN_IN_OK : Result.SIGN_IN_OK_ADMIN;
                    } else {
                        return Result.PASS_INCORRECT;
                    }
                } else {
                    return Result.USER_BANNED;
                }
            } else {
                return Result.EMAIL_NOT_USED;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeDBObjects();
        }
    }

    private void closeDBObjects() throws DAOException {
        connectionPool.releaseConnection(con);
        try { if(stmt!=null) {stmt.close();}} catch(SQLException e) {throw new DAOException("Stmt close except", e); }
        try { if(rs!=null) {rs.close();} } catch(SQLException e) { throw new DAOException("RS close except", e);}
    }

    private boolean checkValueAvailability(String tableName, String valueName, String value) throws DAOException {
        String query = sqlQueryBuilder.selectWhere(tableName, valueName, value);
        try{
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            return !rs.next();
        } catch (SQLException e) {
            throw new DAOException("Check value error", e);
        } finally {
            closeDBObjects();
        }
    }

    @Override
    public Result changeUserStatus(String email) throws DAOException {
        String query = sqlQueryBuilder.updateField(DB[0], "status", "(status + 1)%2",
                "email", email);
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate(query);
            return Result.CHANGE_STATUS_OK;
        } catch (SQLException e) {
            throw new DAOException("Error while changing status");
        }
    }

    @Override
    public Result registration(User user) throws DAOException {
        if (checkValueAvailability(DB[0], "email", user.getEmail())){
            if (checkValueAvailability(DB[0], "phone_number", user.getPhoneNumber())) {
                String query = sqlQueryBuilder.insertOne(DB[0],
                        new String[]{user.getEmail(), user.getId(), user.getPassHash(),
                                String.valueOf(user.getUserStatus().ordinal()), String.valueOf(user.getPrivilege()),
                                user.getPhoneNumber()});
                try {
                    con = connectionPool.getConnection();
                    stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    return Result.REGISTER_OK;
                } catch (SQLException e) {
                    throw new DAOException(e);
                } finally {
                    closeDBObjects();
                }
            } else {
                return Result.PHONE_USED;
            }
        } else {
            return Result.EMAIL_USED;
        }
    }

    @Override
    public Review getUserReview(String email, String film_id) throws DAOException {
        String query = sqlQueryBuilder.selectAllWhere(DB[1], new String[]{"user_email", "film_id"},
                new String[]{email, film_id});
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                return sqlQueryBuilder.parseRSToReview(rs);
            }
        } catch (SQLException e){
            throw new DAOException("user review getting error", e);
        } finally {
            closeDBObjects();
        }
        return null;
    }

    @Override
    public Result addUserReview(Map<String, String> data) throws DAOException {
        String query = sqlQueryBuilder.updateFieldsWhere(DB[1], new String[]{"review_text", "review_rating"},
                new String[]{data.get("text"), data.get("rating")}, new String[]{"user_email", "film_id"},
                new String[]{data.get("email"), data.get("film_id")});
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate(query);
            if (rowsAffected == 0) {
                query = sqlQueryBuilder.insertWithFields(DB[1],
                        new String[]{"review_text", "review_rating", "user_email", "film_id"},
                        new String[]{data.get("text"), data.get("rating"), data.get("email"), data.get("film_id")});
                stmt.executeUpdate(query);
                return Result.REVIEW_ADDED;
            } else {
                return Result.REVIEW_UPDATED;
            }
        } catch (SQLException e) {
            throw new DAOException("Error updating review", e);
        } finally {
            closeDBObjects();
        }
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        String query = sqlQueryBuilder.selectValues(DB[0], new String[]{"email", "status", "phone_number"});
        try {
            con = connectionPool.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(sqlQueryBuilder.parseRSToUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DAOException("Error updating review", e);
        } finally {
            closeDBObjects();
        }
    }

//    @Override
//    public void addFilmsToBD() throws DAOException {
//        String csvPath = "C:/Users/USER/Desktop/my_films.csv";
//        try{
//            con = connectionPool.getConnection();
//            stmt = con.createStatement();
//            try (FileReader fileReader = new FileReader(csvPath, StandardCharsets.UTF_8)) {
//                CSVParser parser = CSVParser.parse(fileReader, CSVFormat.DEFAULT);
//                String[] fields = {"year", "length", "title", "stars", "director", "rating", "censor_rating", "reviews", "film_id"};
//                String[] values = new String[9];
//                int i = 0;
//                for (CSVRecord record : parser) {
//                    if (i > 0) {
//                        System.arraycopy(record.values(), 0, values, 0, values.length - 1);
//                        String genres = record.values()[8];
//                        values[8] = String.valueOf(i);
//                        String query = sqlQueryBuilder.insertWithFields("wt_lab2.films", fields, values);
//                        stmt.executeUpdate(query);
//                        insertGenres(genres, i);
//                    }
//                    i++;
//                }
//            } catch (IOException e){
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            throw new DAOException("Check value error", e);
//        } finally {
//            closeDBObjects();
//        }
//    }
//
//    private void insertGenres(String genresStr, int film_id) throws SQLException {
//        String[] genres = genresStr.split(",");
//        for (String genre: genres) {
//            String query = sqlQueryBuilder.selectValue("wt_lab2.genres", new String[]{"genre_id"}, "name", genre);
//            rs = stmt.executeQuery(query);
//            if (rs.next()) {
//                int id = rs.getInt("genre_id");
//                query = sqlQueryBuilder.insertOne("wt_lab2.film_genres",
//                        new String[]{String.valueOf(film_id), String.valueOf(id)});
//                stmt.executeUpdate(query);
//            }
//        }
//    }
}
