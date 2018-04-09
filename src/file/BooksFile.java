
package file;

import domain.Books;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class BooksFile {
    

  //Escribe libros en el archivo
  public void writeBooksFile(Books book, File file) throws ClassNotFoundException{
      List<Books> booksList = new ArrayList<Books>();
        try{
            if(file.exists()){
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(file));
            Object bookTemp = ois.readObject();
            //casting a lista
            booksList = (List<Books>)bookTemp;
            ois.close();  
        }
        //agrega el libro a la lista
        booksList.add(book);
        //Escribe la lista(contiene objetos de tipo libro) en el archivo    
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeUnshared(booksList); 
        oos.close();
        } 
        catch (FileNotFoundException fnfe) {
        JOptionPane.showMessageDialog(null, "Invalid File", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        catch (IOException ioe) {
        JOptionPane.showMessageDialog(null, "Invalid File", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Lee el archivo y retorna una lista de libros
    public List<Books> readBooksFile(File file) throws IOException, ClassNotFoundException{
       List<Books> booksList = new ArrayList<Books>();
       //Valida si el archivo existe
       if(file.exists()){
            //Lee la lista
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            Object bookTemp = objectInput.readObject();
            //casting del objeto
            booksList = (List<Books>)bookTemp;
            objectInput.close();  
        }
    return booksList;   
    }
    
    public Books getPerson(String isbn, File file) throws IOException, ClassNotFoundException {
        //Lista de libros a recorrer
        List<Books> booksList = new ArrayList<Books>();
        //si el archivo existe entonces recupero el libro
        if(file.exists()){
            //lee la lista
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            Object aux = objectInput.readObject();
            
            //casting del objeto
            booksList = (List<Books>)aux;
            objectInput.close();  
        }
        //mi instancia a retornar
        Books book = new Books();
        //recorre la lista para buscar el libro
        for(int i = 0; i < booksList.size(); i++){
            //pregunta si es la persona
            if(booksList.get(i).getIsbn().equalsIgnoreCase(isbn)){
                
                book = booksList.get(i);
                
                //esta linea me hace salir del ciclo
                break;
            }
        }
        //retorna el libro con o sin los datos
        return book;
    }
    
    public void disminuir(String isbn,  File file) throws IOException, ClassNotFoundException{
        Books bTemp;
        List<Books> arrayList = readBooksFile(file);
        for (int i = 0; i <arrayList.size(); i++) {
            bTemp = arrayList.get(i);
            if (bTemp.getIsbn().equals(isbn)) {
                bTemp.setAvailable(bTemp.getAvailable()-1);
                
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeUnshared(arrayList); 
    }
    
    public void aumentar(String isbn, File file) throws IOException, ClassNotFoundException{
        Books bTemp;
        List<Books> arrayList = readBooksFile(file);
        for (int i = 0; i <arrayList.size(); i++) {
            bTemp = arrayList.get(i);
            if (bTemp.getIsbn().equals(isbn)) {
                bTemp.setAvailable(bTemp.getAvailable()+1);
    
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeUnshared(arrayList);    
    }
}
