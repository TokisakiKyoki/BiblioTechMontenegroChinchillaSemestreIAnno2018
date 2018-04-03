
package file;

import domain.RequestAudiovisual;
import domain.RequestBooks;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;


public class RequestBooksFile {

    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize;
    private String myFilePath;
    Date fecha = new Date();
    
    //constructor
    public RequestBooksFile(File file) throws IOException{
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
    
    public boolean putValue(int position,RequestBooks rbToInsert) throws IOException{
        //vamos a hacer una pequeña validación de position
        if (position >= 0 && position <= this.regsQuantity) {
            
            //verificar que el tamaño sea el adecuado
            if (rbToInsert.sizeInBytes() > this.regSize) {
                System.err.println("1002 - record size is too large");
                return false;
            }else{
                //escribir en archivo
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(rbToInsert.getIDU());
                randomAccessFile.writeUTF(rbToInsert.getIsbn());
                randomAccessFile.writeInt(rbToInsert.getDays());
                
                return true;
            }
            
        }else{
            System.err.println("1001 - position is out of bounds");//numerar error
            return false;
        }
    }//end method
    
    //método para insertar al final del archivo
    public boolean addEndRecord(RequestBooks rb) throws IOException{
        boolean success = putValue(this.regsQuantity, rb);

        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }//end method
    
    public RequestBooks getrb(int position) throws IOException{
        //validar la posición
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            RequestBooks booksTemp = new RequestBooks();
            booksTemp.setIDU(randomAccessFile.readUTF());
            booksTemp.setIsbn(randomAccessFile.readUTF());
            booksTemp.setDays(randomAccessFile.readInt());
            if (booksTemp.getIDU().equals("deleted")) {
                    return null;
                }else{
                    return booksTemp;
                }
        }
        else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    public boolean buscar(String IDU) throws IOException{
        RequestBooks rbTemp;
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                rbTemp = this.getrb(i);
                
                if (rbTemp.getIDU().equals(IDU)) {
                    return true;
                }
            }return false;
            
        }//end method
    
    public boolean deleteRecord(String IDU) throws IOException{
            RequestBooks rbTemp = this.getrb(0);
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                rbTemp = this.getrb(i);
                
                if (rbTemp.getIDU().equals(IDU)) {
                    rbTemp.setIDU("deleted");
                    return this.putValue(i, rbTemp);
                }
            }
            return false;
        }//end method
    
    public ArrayList<RequestBooks> getAllRB() throws IOException{
        ArrayList<RequestBooks> carsArray = new ArrayList<>();
        
        for(int i = 0; i < this.regsQuantity; i++){
            RequestBooks rbTemp = this.getrb(i);
            
            if(rbTemp != null){
                carsArray.add(rbTemp);
            }
        }//end for
        return carsArray;
    }
    
}
