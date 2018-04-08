
package domain;

import java.util.Date;


public class RequestAudiovisual {
    
    private String IDU;
    private int days;
    private String id;
    private String date;

    public RequestAudiovisual(String IDU, String id , int days, String date) {
        this.IDU = IDU;
        this.days = days;
        this.id = id;
        this.date = date;
    }

    public RequestAudiovisual() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    public int sizeInBytes(){
        //retornar la suma en bytes de todos los atributos
        return this.getIDU().length() * 2 +
               this.getDate().length() * 2 +
               this.getId().length() * 2;
               
    }
}
