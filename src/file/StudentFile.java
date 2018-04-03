
package file;

import domain.Student;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;


public class StudentFile {
    
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize;
    private String myFilePath;
    Date fecha = new Date();
    String anyo = new SimpleDateFormat("yy").format(fecha);
    
    
    
    //constructor
    public StudentFile(File file) throws IOException{
        //guardar la ruta del archivo
        this.myFilePath = file.getPath();
        
        //tamaño máximo de cada registro del archivo
        this.regSize = 300;
        
        //una validación básica de file
        if (file.exists()&& !file.isFile()) {
            throw new IOException(file.getName() + " is an invaid file");
        }else{
            //crar una nueva instancia de RAF
            randomAccessFile = new RandomAccessFile(file, "rw");
            
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = (int)Math.ceil((double)randomAccessFile.length() / (double)this.regSize);//Math.ceil redondea hacia arriba
        }
    }//end method
    
    //método para insertar un estudiante en una posicion especifica
    public boolean putValue(int position,Student studentToInsert) throws IOException{
        //vamos a hacer una pequeña validación de position
        if (position >= 0 && position <= this.regsQuantity) {
            
            //verificar que el tamaño sea el adecuado
            if (studentToInsert.sizeInBytes() > this.regSize) {
                System.err.println("1002 - record size is too large");
                return false;
            }else{
                //escribir en archivo
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(studentToInsert.getIdU());
                randomAccessFile.writeUTF(studentToInsert.getName());
                randomAccessFile.writeUTF(studentToInsert.getLastName());
                randomAccessFile.writeUTF(studentToInsert.getIdentification());
                randomAccessFile.writeUTF(studentToInsert.getAddress());
                randomAccessFile.writeUTF(studentToInsert.getCareer());
                
                return true;
            }
            
        }else{
            System.err.println("1001 - position is out of bounds");//numerar error
            return false;
        }
    }//end method
    
    //método para insertar al final del archivo
    public boolean addEndRecord(Student student) throws IOException{
        boolean success = putValue(this.regsQuantity, student);

        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }//end method
    
    //método para generar un carne
    public String generateIDU (String career) throws IOException{
        Formatter fmt = new Formatter();
        String IDU = "";
        int year = Integer.parseInt(anyo)%10;
        ArrayList<Student> myArrayList = getStudentList();
        int dig = myArrayList.size()+1;
        
        if (career.equals("Agronomía")) {
            IDU = "A"+year+fmt.format("%03d", dig);
        }else if (career.equals("Educación")) {
            IDU = "E"+year+fmt.format("%03d", dig);
        }else if (career.equals("Informática")) {
            IDU = "I"+year+fmt.format("%03d", dig);
        }
        return IDU;
    }
    
    //obtener un estudiante de una posición específica
    public Student getStudent(int position) throws IOException{
        //validar position
        if (position >= 0 && position <= this.regsQuantity) {
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            //leer
            Student studentTemp = new Student();
            studentTemp.setIdU(randomAccessFile.readUTF());
            studentTemp.setName(randomAccessFile.readUTF());
            studentTemp.setLastName(randomAccessFile.readUTF());
            studentTemp.setIdentification(randomAccessFile.readUTF());
            studentTemp.setAddress(randomAccessFile.readUTF());
            studentTemp.setCareer(randomAccessFile.readUTF());

            return studentTemp;

        }else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    //método para retornar todos los estudiantes que estén dentro del archivo
    public ArrayList<Student> getStudentList() throws IOException{
        //crear un instancia de un array list
        ArrayList<Student> arrayListOfStudents = new ArrayList<>();

        //recorrer el arreglo para insertar en la lista
        for (int i = 0; i < this.regsQuantity; i++) {
            Student studentTemp = this.getStudent(i);

            //insertar ese vehiculo en la lista
            if (studentTemp != null) {
                arrayListOfStudents.add(studentTemp);
            }
        }
        return arrayListOfStudents;
    }//end method
    
    public boolean search(String IDU) throws IOException{
        Student studentTemp = this.getStudent(0);
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                studentTemp = this.getStudent(i);
                
                if (studentTemp.getIdU().equals(IDU)) {
                    
                    return true;
                }
            }
        return false;    
        }//end method
    
    public Student show(String IDU) throws IOException{
        Student sTemp = this.getStudent(0);
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                sTemp = this.getStudent(i);
                
                if (sTemp.getIdU().equals(IDU)) {
                    sTemp.getName();
                    sTemp.getLastName();
                    return sTemp;
                }
            }return sTemp;
            
        }//end method
}
