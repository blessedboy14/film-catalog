package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film {
    private Director director;
    private String title;
    private String stars;
    private int film_id;
    private int releaseYear;
    private String IMDbRating;
    private String ageLimit;
    private int durationInMinutes;
    private List<Genre> filmGenres;

    public Director getDirector() {
        return director;
    }
    public int getReleaseYear() {
        return releaseYear;
    }
    public String getIMDbRating() {
        return IMDbRating;
    }
    public void setIMDbRating(String IMDbRating) {
        this.IMDbRating = IMDbRating;
    }
    public String getAgeLimit() {
        return ageLimit;
    }
    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
    public List<Genre> getFilmGenres() {
        return filmGenres;
    }
    public String getFilmGenres(int x) {
        StringBuilder sb = new StringBuilder();
        for (Genre genre: this.filmGenres) {
            sb.append(genre).append(", ");
        }
        sb.replace(sb.length()-2, sb.length(), "");
        return sb.toString();
    }
    public void addFilmGenre(Genre genre) {
        this.filmGenres.add(genre);
    }
    public void removeGenre(Genre genre) {
        this.filmGenres.remove(genre);
    }
    public void setFilmGenres(List<Genre> filmGenres) {
        this.filmGenres = filmGenres;
    }
    public String getStars() {return stars;}

    public int getId() {return this.film_id;}

    public void setStars(String stars) {this.stars = stars;}

    public Film(Director director, String title, int releaseYear, String IMDbRating, String ageLimit, int durationInMinutes,
                List<Genre> filmGenres, String stars) {
        this.director = director;
        this.title = title;
        this.stars = stars;
        this.releaseYear = releaseYear;
        this.IMDbRating = IMDbRating;
        this.ageLimit = ageLimit;
        this.durationInMinutes = durationInMinutes;
        this.filmGenres = filmGenres == null ? new ArrayList<>() : filmGenres;
    }

    public Film(Director director, String title, int releaseYear, String IMDbRating, String ageLimit, int durationInMinutes,
                List<Genre> filmGenres, String stars, int film_id) {
        this.director = director;
        this.title = title;
        this.stars = stars;
        this.releaseYear = releaseYear;
        this.IMDbRating = IMDbRating;
        this.ageLimit = ageLimit;
        this.durationInMinutes = durationInMinutes;
        this.filmGenres = filmGenres == null ? new ArrayList<>() : filmGenres;
        this.film_id = film_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return durationInMinutes == film.durationInMinutes && Objects.equals(director, film.director)
                && Objects.equals(releaseYear, film.releaseYear) && Objects.equals(IMDbRating, film.IMDbRating)
                && Objects.equals(ageLimit, film.ageLimit) && Objects.equals(filmGenres, film.filmGenres);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(director, releaseYear, IMDbRating, ageLimit, durationInMinutes);
        result = 31 * result + filmGenres.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Film [" +
                "director=" + director +
                ", releaseYear='" + releaseYear + '\'' +
                ", IMDbRating='" + IMDbRating + '\'' +
                ", ageLimit='" + ageLimit + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                ", filmGenres=" + filmGenres.toString() +
                " ]";
    }
}
