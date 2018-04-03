
package file;
import domain.Audiovisual;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author richar
 */
public class AudiovisualFile {
    
    //atributes
    public RandomAccessFile randomAccessFile;
    private int regsQuantity;//cantidad de registros en el archivo
    private int regSize;//tamanno del registro
    private String myFilePath;//ruta
    
    //constructor
    public AudiovisualFile(File file) throws IOException{
        //almaceno la ruta
        myFilePath = file.getPath();
        
        //indico el tamanno máximo
        this.regSize = 140;
        
        //una validación sencilla
        if(file.exists() && !file.isFile()){
            throw new IOException(file.getName() + " is an invalid file");
        }else{
            //crear la nueva instancia de RAF
            randomAccessFile = new RandomAccessFile(file, "rw");
            
            //necesitamos indicar cuantos registros tiene el archivo
            this.regsQuantity = 
                    (int)Math.ceil((double)randomAccessFile.length() / (double)regSize);
        }
    }//end method
    
    //MUY IMPORTANTE cerrar nuestros archivos
    public void close() throws IOException{
        randomAccessFile.close();
        
    }
    
    //indicar la cantidad de registros de nuestro archivo
    public int fileSize(){
        return this.regsQuantity;
    }
    
    //insertar un nuevo registro en una posición específica
    public boolean putValue(int position, Audiovisual audiovisual) throws IOException{
        
        //primero: verificar que sea válida la inserción
        if(position < 0 && position > this.regsQuantity){
            System.err.println("1001 - Record position is out of bounds");
            return false;
        }else{
            if(audiovisual.sizeInBytes() > this.regSize){
                System.err.println("1002 - Record size is out of bounds");
                return false;
            }else{
                
                randomAccessFile.seek(position * this.regSize);
                randomAccessFile.writeUTF(audiovisual.getId());
                randomAccessFile.writeUTF(audiovisual.getObject());
                randomAccessFile.writeUTF(audiovisual.getCompany());
                randomAccessFile.writeInt(audiovisual.getAvailable());
                return true;
            }
        }
    }//end method
    
    //insertar al final del archivo
    public boolean addEndRecord(Audiovisual audiovisual) throws IOException{
        boolean success = putValue(this.regsQuantity, audiovisual);
        if(success){
            ++this.regsQuantity;
        }
        return success;
    }
    
    //obtener un audiovisual
    public Audiovisual getAudiovisual(int position) throws IOException{
        //validar la posición
        if(position >= 0 && position <= this.regsQuantity){
            //colocamos el brazo en el lugar adecuado
            randomAccessFile.seek(position * this.regSize);
            
            //llevamos a cabo la lectura
            Audiovisual audiovisualTemp = new Audiovisual(0);
            audiovisualTemp.setId(randomAccessFile.readUTF());
            audiovisualTemp.setObject(randomAccessFile.readUTF());
            audiovisualTemp.setCompany(randomAccessFile.readUTF());
            audiovisualTemp.setAvailable(randomAccessFile.readInt());
            
            return audiovisualTemp;
            
        }
        else{
            System.err.println("1003 - position is out of bounds");
            return null;
        }
    }//end method
    
    
    
    //retornar una lista de carros
    public ArrayList<Audiovisual> getAllAudiovisual() throws IOException{
        ArrayList<Audiovisual> carsArray = new ArrayList<>();
        
        for(int i = 0; i < this.regsQuantity; i++){
            Audiovisual aTemp = this.getAudiovisual(i);
            
            if(aTemp != null){
                carsArray.add(aTemp);
            }
        }//end for
        return carsArray;
    }
      
    public int cantidad(String id) throws IOException{
        Audiovisual aTemp;
        int temp=0;
            for (int i = 0; i <+ this.regsQuantity; i++) {
                aTemp = this.getAudiovisual(i);
                if (aTemp.getId().equals(id)) {
                    temp = aTemp.getAvailable();
                    return temp;
                }
            }return temp;
            
        }//end method
    
    public Audiovisual buscar(String id) throws IOException{
        Audiovisual aTemp = this.getAudiovisual(0);
            
            for (int i = 0; i <+ this.regsQuantity; i++) {
                aTemp = this.getAudiovisual(i);
                
                if (aTemp.getId().equals(id)) {
                    aTemp.getObject();
                    aTemp.getCompany();
                    aTemp.getAvailable();
                    return aTemp;
                }
            }return aTemp;
            
        }//end method
    
    public boolean disminuir(String id) throws IOException{
        Audiovisual aTemp;
        for (int i = 0; i <+ this.regsQuantity; i++) {
            aTemp = this.getAudiovisual(i);
            if (aTemp.getId().equals(id)) {
                aTemp.setAvailable(aTemp.getAvailable()-1);
                return this.putValue(i, aTemp);
            }
        }return false;
    }
    
    public boolean aumentar(String id) throws IOException{
        Audiovisual aTemp;

        for (int i = 0; i <+ this.regsQuantity; i++) {
            aTemp = this.getAudiovisual(i);
            if (aTemp.getId().equals(id)) {
                aTemp.setAvailable(aTemp.getAvailable()+1);
                return this.putValue(i, aTemp);
            }
        }return false;
    }
    
    
    public boolean editar(Audiovisual audiovisual) throws IOException{
        Audiovisual aTemp = this.getAudiovisual(0);

        for (int i = 0; i <+ this.regsQuantity; i++) {
            aTemp = this.getAudiovisual(i);
            if (aTemp.getId().equals(audiovisual.getId())) {
                aTemp.setAvailable(audiovisual.getAvailable());
                return this.putValue(i, aTemp);
            }
            return false;
        }return false;
    }
}//fin de la clase
    

