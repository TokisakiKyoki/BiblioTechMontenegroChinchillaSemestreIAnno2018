
package file;

import domain.Audiovisual;
import domain.RequestAudiovisual;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;


public class RequestAudiovisualFile {
    
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize;
    private String myFilePath;
    Date fecha = new Date();
    
    //constructor
    public RequestAudiovisualFile(File file) throws IOException{
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
    
    public boolean putValue(int position,RequestAudiovisual ravToInsert) throws IOException{
        //vamos a hacer una pequeña validación de position
        if (position >= 0 && position <= this.regsQuantity) {
            
            //verificar que el tamaño sea el adecuado
            if (ravToInsert.sizeInBytes() > this.regSize) {
                System.err.println("1002 - record size is too large");
                return false;
            }else{
                //escribir en archivo
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(ravToInsert.getIDU());
                randomAccessFile.writeUTF(ravToInsert.getId());
                randomAccessFile.writeInt(ravToInsert.getDays());
                return true;
            }
            
        }else{
            System.err.println("1001 - position is out of bounds");//numerar error
            return false;
        }
    }//end method
    
    //método para insertar al final del archivo
    public boolean addEndRecord(RequestAudiovisual rav) throws IOException{
        boolean success = putValue(this.regsQuantity, rav);

        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }//end method
    
    public RequestAudiovisual getrav(int position) throws IOException{
        //validar la posición
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            RequestAudiovisual audiovisualTemp = new RequestAudiovisual();
            audiovisualTemp.setIDU(randomAccessFile.readUTF());
            audiovisualTemp.setId(randomAccessFile.readUTF());
            audiovisualTemp.setDays(randomAccessFile.readInt());
            
            if (audiovisualTemp.getId().equals("")) {
                    return null;
            }else{
                    return audiovisualTemp;
            }
        }
        else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    public boolean buscar(String ID) throws IOException{
        RequestAudiovisual ravTemp = this.getrav(0);
        boolean a = false;
            for (int i = 0; i <+ this.regsQuantity; i++) {
                ravTemp = this.getrav(i);
                if (ravTemp.getId().equals(ID)) {
                    a=true;
                }
            }return a;
            
        }//end method
    
    public boolean deleteRecord(String ID) throws IOException{
            RequestAudiovisual ravTemp = this.getrav(0);
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                ravTemp = this.getrav(i);
                
                if (ravTemp.getId().equals(ID)) {
                    ravTemp.setId("Entregado");
                    return this.putValue(i, ravTemp);
                }
            }
            return false;
        }//end method
    
    
    public ArrayList<RequestAudiovisual> getAllRAV() throws IOException{
        ArrayList<RequestAudiovisual> carsArray = new ArrayList<>();
        
        for(int i = 0; i < this.regsQuantity; i++){
            RequestAudiovisual ravTemp = this.getrav(i);
            
            if(ravTemp != null){
                carsArray.add(ravTemp);
            }
        }//end for
        return carsArray;
    }
    
    
}
