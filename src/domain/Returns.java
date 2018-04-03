
package domain;


public class Returns {
    
    private String IDU;
    private int penalty;
    private int days;

    public Returns(String IDU, int penalty) {
        this.IDU = IDU;
        this.penalty = penalty;
    }

    public Returns(int days) {
        this.days = days;
    }

    public String getIDU() {
        return IDU;
    }

    public void setIDU(String IDU) {
        this.IDU = IDU;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "Returns{" + "IDU=" + IDU + ", penalty=" + penalty + ", days=" + days + '}';
    }
    
    public int sizeInBytes(){
        //long: necesita dos bytes
        //String: necesita 2 bytes de espacio.
        return  this.getIDU().length()*2 + 4 + 4;
    }
}
