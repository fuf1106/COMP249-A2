
/**
 * The `Book` class represents a Book Object with various attributes including title, authors, price, ISBN, genre, and publication year.
 * It also provides methods to access and modify these attributes.
 */
import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    /**
     * Default constructor for the `Book` class.
     * Initializes all attributes to default values.
     */
    //is this constructor necessary? only valid books w 6 parameters will get passed... (no)
//    public Book() {
//        this.title = "None";
//        this.authors = "None";
//        this.price = 0;
//        this.isbn = "None";
//        this.genre = "None";
//        this.year = 0;
//    }

    /**
     * Parameterized constructor for the `Book` class.
     * Initializes the `Book` object with specific attribute values.
     *
     * @param title   The title of the book.
     * @param authors The authors of the book.
     * @param price   The price of the book.
     * @param isbn    The ISBN of the book.
     * @param genre   The genre of the book.
     * @param year    The year of publication of the book.
     */
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    /**
     * Returns the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The new title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the authors of the book.
     *
     * @return The authors of the book.
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Sets the authors of the book.
     *
     * @param authors The new authors of the book.
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Returns the price of the book.
     *
     * @return The price of the book.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the book.
     *
     * @param price The new price of the book.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return The ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn The new ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Returns the genre of the book.
     *
     * @return The genre of the book.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     *
     * @param genre The new genre of the book.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns the year of publication of the book.
     *
     * @return The year of publication of the book.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of publication of the book.
     *
     * @param year The new year of publication of the book.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return a String outputting the information of the current object
     */
    @Override
    public String toString() {
        return title + ", " + authors + ", " + price + ", " + isbn + ", " + genre + ", " + year;
    }

    /**
     * This method checks if the object the method is called on is equal to the Object parameter
     * @param obj Takes an Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        else if (this.getClass() != obj.getClass()) {
            return false;
        }
        else {
            Book book = (Book) obj;
            return this.title.equals(book.title) && this.authors.equals(book.authors) && this.price == book.price &&
                    this.isbn.equals(book.isbn) && this.genre.equals(book.genre) && this.year == book.year;
        }
    }



}
