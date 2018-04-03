
package file;

import domain.Returns;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;


public class PenaltyFile {
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;
    private int regSize;
    private String myFilePath;
    Date fecha = new Date();
    
    //constructor
    public PenaltyFile(File file) throws IOException{
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
    
    public boolean putValue(int position,Returns returnToInsert) throws IOException{
        //vamos a hacer una pequeña validación de position
        if (position >= 0 && position <= this.regsQuantity) {
            
            //verificar que el tamaño sea el adecuado
            if (returnToInsert.sizeInBytes() > this.regSize) {
                System.err.println("1002 - record size is too large");
                return false;
            }else{
                //escribir en archivo
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(returnToInsert.getIDU());
                randomAccessFile.writeInt(returnToInsert.getPenalty());
                return true;
            }
            
        }else{
            System.err.println("1001 - position is out of bounds");//numerar error
            return false;
        }
    }//end method
    
    //método para insertar al final del archivo
    public boolean addEndRecord(Returns returns) throws IOException{
        boolean success = putValue(this.regsQuantity, returns);

        if (success) {
            ++this.regsQuantity;
        }
        return success;
    }//end method
    
    public Returns getPenalty(int position) throws IOException{
        //validar la posición
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            Returns pTemp = new Returns(0);
            pTemp.setIDU(randomAccessFile.readUTF());
            pTemp.setPenalty(randomAccessFile.readInt());
            
            if (pTemp.getIDU().equals("deleted")) {
                    return null;
                }else{
                    return pTemp;
                }
        }
        else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    
}
