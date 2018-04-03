
package domain;

import java.util.Date;


public class RequestBooks{
    
    private String IDU;
    private int days;
    private String isbn;

    public RequestBooks(String IDU, String isbn, int days) {
        this.IDU = IDU;
        this.days = days;
        this.isbn = isbn;
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

    @Override
    public String toString() {
        return "RequestBooks{" + "IDU=" + IDU + ", days=" + days + ", isbn=" + isbn + '}';
    }
    
    public int sizeInBytes(){
        //retornar la suma en bytes de todos los atributos
        return this.getIDU().length() * 2 +
               this.getIsbn().length() * 2 + 4;
    }
    
}
