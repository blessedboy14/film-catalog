package beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Director {
    private String firstName;
    private String secondName;

    private List<Film> madeFilms;

    public String getFirstName() {
        return firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public Director(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.madeFilms = new ArrayList<>();
    }
    public void addFilm(Film film) {
        madeFilms.add(film);
    }
    public List<Film> getFilms() {
        return this.madeFilms;
    }
    public void removeFilm(Film film) {
        this.madeFilms.remove(film);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(firstName, director.firstName) && Objects.equals(secondName, director.secondName) && Objects.equals(madeFilms, director.madeFilms);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, secondName);
        result = 17 * result + madeFilms.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(secondName);
        return sb.toString();
    }
}
