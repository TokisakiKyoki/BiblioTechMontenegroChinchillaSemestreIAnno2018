
package domain;


public class Books extends Material {
    
    private String isbn;
    private String title;
    private String author;
    private int edition;
    private int year;

    //Constructores
    public Books() {
        super();
        this.isbn = "";
        this.title = "";
        this.author = "";
        this.edition = 0;
        this.year = 0;
    }

    public Books(String isbn, String title, String author, int edition, int year , int available) {
        super(available);
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.year = year;
    }

    //Getter and setter
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Books{" + "isbn=" + isbn + ", title=" + title + ", author=" + author + ", edition=" + edition + '}';
    }
    
    
}
