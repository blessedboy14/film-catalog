package dao;

import beans.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SQLQueryBuilder {
    public String insertOne(String tableName, String[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" VALUES (");
        for (String str: values) {
            sb.append("'").append(str).append("',");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        sb.append(");");
        return sb.toString();
    }

    public String selectWhere(String tableName, String[] valuesName, String[] values){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT 1 FROM ").append(tableName).append(" WHERE ");
        for (int i = 0; i < values.length; i++) {
            sb.append(valuesName[i]).append("='").append(values[i]).append("'").append(" AND ");
        }
        sb.replace(sb.length()-4, sb.length(), "").append(";");
        return sb.toString();
    }

    public String updateFieldsWhere(String tableName, String[] updateFields, String[] updateValues,
                                    String[] whereFields, String[] whereValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");
        for (int i = 0; i < updateFields.length; i++) {
            sb.append(updateFields[i]).append("='").append(updateValues[i]).append("',");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        sb.append(" WHERE ");
        for (int i = 0; i < whereFields.length; i++) {
            sb.append(whereFields[i]).append("='").append(whereValues[i]).append("' AND ");
        }
        sb.replace(sb.length() - 4, sb.length(), "");
        return sb.toString();
    }

    public String selectLastId(String tableName) {
        StringBuilder sb = new StringBuilder();
        return sb.append("SELECT LAST_INSERT_ID() FROM ").append(tableName).append(";").toString();
    }

    public String selectWhere(String tableName, String valueName, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT 1 FROM ").append(tableName).append(" WHERE ").append(valueName).append("='").append(value)
                .append("';");
        return sb.toString();
    }

    public String selectAllWhere(String tableName, String valueName, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(valueName).append("='").append(value)
                .append("';");
        return sb.toString();
    }

    public String selectAllWhere(String tableName, String[] valueNames, String[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" WHERE ");
        for (int i = 0; i < values.length; i++) {
            sb.append(valueNames[i]).append("='").append(values[i]).append("' AND ");
        }
        sb.replace(sb.length() - 4, sb.length(), "");
        return sb.toString();
    }

    public String selectValue(String tableName, String[] valueSearch, String valueName, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        for (String str: valueSearch) {
            sb.append(str).append(",");
        }
        sb.replace(sb.length()-1, sb.length(), "").append(" FROM ").append(tableName).append(" WHERE ").append(valueName)
                .append("='").append(value).append("';");
        return sb.toString();
    }

    public String insertWithFields(String tableName, String[] fields, String[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        for (String field: fields) {
            sb.append(field).append(",");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        sb.append(") VALUES (");
        for (String value: values) {
            if (Objects.equals(value, "")) {value = "0";}
            sb.append("\"").append(value).append("\",");
        }
        sb.replace(sb.length()-1, sb.length(), "");
        sb.append(");");
        return sb.toString();
    }

    public String selectWithOrderAndLimit(String tableName, String orderBy, int limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" ORDER BY ").append(orderBy).append(" DESC LIMIT ")
                .append(limit);
        return sb.toString();
    }

    public String deleteWhere(String tableName, String fieldName, String fieldValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(tableName).append(" WHERE ").append(fieldName).append("='").append(fieldValue)
                .append("';");
        return sb.toString();
    }

    public Review parseRSToReview(ResultSet rs) throws SQLException {
        return new Review(rs.getString("film_id"), rs.getString("review_text"),
                rs.getString("review_rating"), rs.getString("user_email"),
                rs.getString("review_date"), rs.getString("edit_date"));
    }

    public String selectWhereWithOrderAndLimit(String tableName, String whereField, String whereValue,
                                               String orderBy, int limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(whereField).append("=")
                .append(whereValue).append(" ORDER BY ").append(orderBy).append(" DESC LIMIT ").append(limit);
        return sb.toString();
    }

    public String searchFilmBy(String tableName, String valueName, String valueSearch) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(valueName).append(" LIKE '%").
                append(valueSearch).append("%' ").append(" LIMIT 30");
        return sb.toString();
    }
}
