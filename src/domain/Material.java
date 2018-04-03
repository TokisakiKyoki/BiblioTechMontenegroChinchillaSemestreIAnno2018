
package domain;

import java.io.Serializable;


public class Material implements Serializable {
    
    //atributos
    private int available;

    public Material(int available) {
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
      
}
