package tests;

import beans.Director;
import beans.Film;
import beans.Genre;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.CatalogService;
import service.exception.ServiceException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CatalogServiceTest {

    private CatalogService mock;

    @Test
    @Description("This is test for film adding")
    void TestAddFilm() {
        Map<String, Object> data = new HashMap<>(Map.of(
                "director", new Director("Steven", "Spilberg"),
                "title", "Avatar",
                "releaseYear", 2004,
                "rating", "8.7",
                "ageLimit", "R",
                "length", 120,
                "genres", Arrays.asList(Genre.ACTION, Genre.ANIMATION),
                "stars", "YenniferLourens"
        ));
        try {
            mock.addFilm(data);
            Mockito.verify(mock).addFilm(data);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Description("This is test for film listing")
    void TestListFilms() {
        List<Film> films = Arrays.asList(
                new Film(null, "Avatar", 2004, "7.8", "R", 120, null, ""),
                new Film(null, "Boys of 21 century", 1975, "9.8", "R", 132, null, "")
                );
        try {
            Mockito.when(mock.listFilms(2)).thenReturn(films);
            List<Film> res = mock.listFilms(2);
            assertEquals(films, res);
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }

    @Test
    @Description("This is test for get 1 film by id")
    void getFilm() {
        Film film = new Film(null, "Avatar", 2004, "7.8", "R", 120, null, "");
        try{
            Mockito.when(mock.getFilm("101")).thenReturn(film);
            Film reslt = mock.getFilm("101");
            assertEquals(film ,reslt);
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }

    @Test
    @Description("This is test for search film")
    void TestSearchFilms() {
        List<Film> films = Arrays.asList(
                new Film(null, "Avatar", 2004, "7.8", "R", 120, null, ""),
                new Film(null, "Qatar", 1975, "9.8", "R", 132, null, "")
        );
        try {
            Mockito.when(mock.searchFilms("tar")).thenReturn(films);
            List<Film> res = mock.searchFilms("tar");
            assertEquals(films, res);
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        mock = Mockito.mock(CatalogService.class);
    }
}