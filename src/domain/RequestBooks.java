
package domain;

import java.util.Date;


public class RequestBooks{
    
    private String IDU;
    private int days;
    private String isbn;
    private String date;

    public RequestBooks(String IDU, String isbn, int days, String date) {
        this.IDU = IDU;
        this.days = days;
        this.isbn = isbn;
        this.date = date;
    }

    public RequestBooks() {
    }
    
    

    public String getIDU() {
        return IDU;
    }

    public void setIDU(String IDU) {
        this.IDU = IDU;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "RequestBooks{" + "IDU=" + IDU + ", days=" + days + ", isbn=" + isbn + '}';
    }
    
    public int sizeInBytes(){
        //retornar la suma en bytes de todos los atributos
        return this.getIDU().length() * 2 +
               this.getDate().length() * 2 +
               this.getIsbn().length() * 2 + 4;
    }
    
}
