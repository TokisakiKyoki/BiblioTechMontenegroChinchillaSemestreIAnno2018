/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.Audiovisual;
import domain.RequestAudiovisual;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.Student;
import file.AudiovisualFile;
import file.RequestAudiovisualFile;
import file.StudentFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author TokisakiKuro
 */
public class Tests {
    
    public Tests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
//     @Test
     public void test() throws IOException {
        File file = new File("./student.dat");
        StudentFile studentFile = new StudentFile(file);
        
        
        Student v1 = new Student(studentFile.generateIDU("Informática"), "Pablo", "Monrales", "1021931", "abcd@gmail.com", "Informática");
        Student v2 = new Student(studentFile.generateIDU("Educación"), "Pablo2", "Monrales", "1021931", "abcd@gmail.com", "Educación");
        Student v3 = new Student(studentFile.generateIDU("Agronomía"), "Pablo3", "Monrales", "1021931", "abcd@gmail.com", "Agronomía");
        
        studentFile.addEndRecord(v1);
        studentFile.addEndRecord(v2);
        studentFile.addEndRecord(v3);
     }
     
     //@Test
    public void readStudent() throws IOException{
        File file = new File("./student.dat");
        StudentFile studentFile = new StudentFile(file);
        
        Student studentTemp = studentFile.getStudent(0);
        System.out.println(studentTemp.toString());
        
    }
    
    @Test
    public void readAllStudent() throws IOException{
        File file = new File("./rav.dat");
        RequestAudiovisualFile studentFile = new RequestAudiovisualFile(file);
        
        ArrayList<RequestAudiovisual> myArrayList = studentFile.getAllRAV();
        String a = "";
    }
}
