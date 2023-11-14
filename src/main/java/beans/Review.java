package beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Class that describes user review to film
 */
public class Review {
    private String reviewFilmId;
    private String dateAdded;
    private String dateChanged;

    private String reviewText;
    private String reviewRating;
    private String reviewerEmail;

    public String getReviewFilm() {
        return reviewFilmId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewer() {
        return reviewerEmail;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getPrettyDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(this.dateAdded, formatter);
        return localDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a"));
    }

    public Review(String reviewFilmId, String reviewText, String reviewRating, String reviewerEmail,
                  String dateAdded, String dateChanged) {
        this.reviewFilmId = reviewFilmId;
        this.dateAdded = dateAdded;
        this.dateChanged = dateChanged;
        this.reviewText = reviewText;
        this.reviewRating = reviewRating;
        this.reviewerEmail = reviewerEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewFilmId, review.reviewFilmId) && Objects.equals(reviewText, review.reviewText)
                && Objects.equals(reviewRating, review.reviewRating) && Objects.equals(reviewerEmail, review.reviewerEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewFilmId, reviewText, reviewRating, reviewerEmail);
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Review by ").append(this.reviewerEmail).append(".\nReview: ").append(this.reviewText)
                .append(".\nFilm rating: ").append(this.reviewRating);
        return toString.toString();
    }
}
