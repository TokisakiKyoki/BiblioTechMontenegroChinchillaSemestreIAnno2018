
package domain;

/**
 *
 * @author richar
 */
public class Audiovisual extends Material {
    
    //atributos
    private String id;
    private String object;
    private String company;

    //Constructores
    public Audiovisual(int available) {
        super(available);
    }

    public Audiovisual(String id, String object, String company, int available) {
        super(available);
        this.id = id;
        this.object = object;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Audiovisual{" + "id=" + id + ", object=" + object + ", company=" + company + '}';
    }
    public int sizeInBytes(){
        //long: necesita dos bytes
        //String: necesita 2 bytes de espacio.
        return  this.getId().length()*2+
                this.getObject().length()*2+
                this.getCompany().length()*2;
    }
    
}
