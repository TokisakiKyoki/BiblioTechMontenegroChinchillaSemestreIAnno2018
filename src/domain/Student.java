
package domain;


public class Student {
    
    //atributos
    private String idU;
    private String name;
    private String lastName;
    private String identification;
    private String address;
    private String career;
    
    //constructores

    public Student() {
    }

    public Student(String idU, String name, String lastName, String identification, String address, String career) {
        this.idU = idU;
        this.name = name;
        this.lastName = lastName;
        this.identification = identification;
        this.address = address;
        this.career = career;
    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "Student{" + "idU=" + idU + ", name=" + name + ", lastName=" + lastName + ", identification=" + identification + ", address=" + address + ", career=" + career + '}';
    }

    //método para indicar el tamaño en bytes de los atributos
    public int sizeInBytes(){
        //retornar la suma en bytes de todos los atributos
        return this.getIdU().length() * 2 +
               this.getName().length() * 2 + 
               this.getLastName().length() * 2 + 
               this.getIdentification().length() * 2 +
               this.getAddress().length() * 2 + 
               this.getCareer().length() * 2;
    }
    
}
