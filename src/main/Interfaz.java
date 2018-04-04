package main;

import domain.Audiovisual;
import domain.Books;
import domain.Material;
import domain.RequestAudiovisual;
import domain.RequestBooks;
import domain.Student;
import file.AudiovisualFile;
import file.BooksFile;
import file.RequestAudiovisualFile;
import file.RequestBooksFile;
import file.StudentFile;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Formatter;

public class Interfaz extends javax.swing.JFrame {
    Date date = new Date();
    File file1 = new File("./student.dat");
    File file2 = new File("./book.dat");
    File file3 = new File("./audiovisual.dat");
    File file4 = new File("./rav.dat");
    File file5 = new File("./rb.dat");
    File file6 = new File("./penalty.dat");
    StudentFile studentfile = new StudentFile(file1);
    BooksFile booksFile = new BooksFile();
    AudiovisualFile audiovisualFile = new AudiovisualFile(file3);
    RequestAudiovisualFile ravFile = new RequestAudiovisualFile(file4);
    RequestBooksFile rbFile = new RequestBooksFile(file5);
    
    String [] titlesBooks = {"ISBN","Título","Autor","Año","Disponible"};
    String [] titlesAudiovisuals = {"Id","Tipo","Compañia","Disponible"};
    String [] titlesRequest = {"Estudiante","ID","Días"};
    DefaultTableModel dtm1 = new DefaultTableModel(null, titlesBooks);
    DefaultTableModel dtm2 = new DefaultTableModel(null, titlesAudiovisuals);
    DefaultTableModel dtm3 = new DefaultTableModel(null, titlesRequest);
    DefaultTableModel dtm4 = new DefaultTableModel(null, titlesRequest);
    
    ArrayList<RequestAudiovisual> myArrayList = ravFile.getAllRAV();
    ArrayList<RequestBooks> myArrayList2 = rbFile.getAllRB();
    
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
    Date today = new Date();
    
    Student s;
    boolean c;
    String IDU;
    
    public void readAudiovisualData() throws IOException{
        
        String datos[] = new String[4];
        ArrayList<Audiovisual> myArrayList = audiovisualFile.getAllAudiovisual();
        
        for (int i = 0; i < myArrayList.size(); i++) {
            datos[0] = myArrayList.get(i).getId();
            datos[1] = myArrayList.get(i).getObject();
            datos[2] = myArrayList.get(i).getCompany();
            datos[3] = Integer.toString(myArrayList.get(i).getAvailable());
            dtm2.addRow(datos);
        }
        jTable1.setModel(dtm2);
        
    }
    
    public void readRAVData() throws IOException{
        
        String datos[] = new String[3];
        myArrayList = ravFile.getAllRAV();
        for (int i = 0; i < myArrayList.size(); i++) {
            datos[0] = myArrayList.get(i).getIDU();
            datos[1] = myArrayList.get(i).getId();
            datos[2] = Integer.toString(myArrayList.get(i).getDays());
            dtm3.addRow(datos);
        }
        jTable1.setModel(dtm3);
        
    }
    
    public void readRBData() throws IOException{
        
        String datos[] = new String[3];
        myArrayList2 = rbFile.getAllRB();
        for (int i = 0; i < myArrayList2.size(); i++) {
            datos[0] = myArrayList2.get(i).getIDU();
            datos[1] = myArrayList2.get(i).getIsbn();
            datos[2] = Integer.toString(myArrayList2.get(i).getDays());
            dtm4.addRow(datos);
        }
        jTable1.setModel(dtm4);
        
    }
    
    public void limpiaTabla(){
        dtm1.setRowCount(0);
        dtm2.setRowCount(0);
        dtm3.setRowCount(0);
        dtm4.setRowCount(0);
    }
    
    public Interfaz() throws IOException{
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("../resources/iconotitle.png")).getImage());
        Font.setVisible(true);
        Student.setVisible(false);
        Material.setVisible(false);
        Others.setVisible(false);
        Books.setVisible(false);
        Carne.setVisible(false);
        Solicitar.setVisible(false);
        OtherMaterial.setVisible(false);
        Book.setVisible(false);
        Devoluciones.setVisible(false);
        Tabla.setVisible(false);
        DB.setVisible(false);
        DAV.setVisible(false);
        Carne1.setVisible(false);
    }

    //método para ingresar el estudiante
    private void addStudents() throws IOException{
        String career = (String)jComboBoxCareer.getSelectedItem();
        IDU = studentfile.generateIDU(career);
        
        Student s = new Student(IDU, jTextName.getText(), jTextLastName.getText(), jTextIdentification.getText(), jTextAddress.getText(), career);
        
        studentfile.addEndRecord(s);
        
        jTextName.setText("");
        jTextLastName.setText("");
        jTextIdentification.setText("");
        jTextAddress.setText("");
    }
    
    private void addBooks(){
        int cant = 0;//falta ingresar la cantidad
        Books b = new Books(jTextID.getText(), jTextTitle.getText(), jTextAuthor.getText(), 
                Integer.parseInt(jTextEdition.getText()), Integer.parseInt(jTextYear.getText()), cant);
        
        
        jTextTitle.setText("");
        jTextAuthor.setText("");
        jTextYear.setText("");
        jTextEdition.setText("");
    }
    
    private void addAudiovisuals() throws IOException{
        int cant = audiovisualFile.cantidad(jTextID.getText());
        String audiovisualtype = (String)otherTypes.getSelectedItem();
        Audiovisual av = new Audiovisual(jTextID.getText(), audiovisualtype, jTextCompany.getText(), cant+1);
        if (cant>0) {
            audiovisualFile.aumentar(jTextID.getText());
        }else{
            audiovisualFile.addEndRecord(av);
        }
        jTextCompany.setText("");
        jTextID.setText("");
        
    }
    
    private void toBooks(){
        Others.setVisible(false);
        Books.setVisible(true);
    }
    
    private void toOthers(){
        Books.setVisible(false);
        Others.setVisible(true);
    }
    
    private void toBooks2() throws IOException{
        OtherMaterial.setVisible(false);
        Book.setVisible(true);
        limpiaTabla();
        
    }
    
    private void toOthers2() throws IOException{
        Book.setVisible(false);
        OtherMaterial.setVisible(true);
        limpiaTabla();
        readAudiovisualData();
    }
    
    private void toBooks3() throws IOException{
        DB.setVisible(true);
        DAV.setVisible(false);
        Tabla.setVisible(true);
        limpiaTabla();
        readRBData();
    }
    
    private void toOthers3() throws IOException{
        DAV.setVisible(true);
        DB.setVisible(false);
        Tabla.setVisible(true);
        limpiaTabla();
        readRAVData();
    }
    
    private void check() throws IOException{
        c = studentfile.search(receiveIDU.getText());
        s = studentfile.show(receiveIDU.getText());
        if (c==true) {
            Solicitar.setVisible(true);
            Carne.setVisible(false);
            OtherMaterial.setVisible(false);
            Book.setVisible(false);
            Tabla.setVisible(true);
            Carne1.setVisible(false);
            notexist1.setText("");
            nameShow.setText(s.getName()+" "+s.getLastName());
        }else{
            notexist1.setText("El carné ingresado no coincide con el de ningun estudiante registrado");
        }limpiaTabla();
    }
    
    private void check2() throws IOException{
        c = studentfile.search(receiveIDU1.getText());
        s = studentfile.show(receiveIDU1.getText());
        if (c==true) {
            Devoluciones.setVisible(true);
            Solicitar.setVisible(false);
            Carne1.setVisible(false);
            Carne.setVisible(false);
            OtherMaterial.setVisible(false);
            Book.setVisible(false);
            Tabla.setVisible(true);
            notexist.setText("");
            nameShow1.setText(s.getName()+" "+s.getLastName());
        }else{
            notexist.setText("El carné ingresado no coincide con el de ningun estudiante registrado");
        }limpiaTabla();
    }
    
    
    private void searchAudiovisual() throws IOException{
        
        Audiovisual v = audiovisualFile.buscar(jTextSearch1.getText());
        
        reqtype.setText(v.getObject());
        reqcompany.setText(v.getCompany());
        reqavailable.setText(Integer.toString(v.getAvailable()));
        
    }
    
    private void addRAV() throws IOException, ParseException{
        
        
        int cant = Integer.parseInt(reqavailable.getText());
        RequestAudiovisual rav = new RequestAudiovisual(receiveIDU.getText(), jTextSearch1.getText(), Integer.parseInt((String)numDays.getSelectedItem()));
            if (cant>0) {
            ravFile.addEndRecord(rav);
            audiovisualFile.disminuir(jTextSearch1.getText());
            
        }else{
            noexistAV.setText("No hay recursos disponibles");
        }
        Audiovisual v = audiovisualFile.buscar(jTextSearch1.getText());
        reqavailable.setText(Integer.toString(v.getAvailable()));
        limpiaTabla();
        readAudiovisualData();
        jTextSearch1.setText("");
        reqtype.setText("");
        reqcompany.setText("");
        reqavailable.setText("");
    }
    
    private void addRB() throws IOException{
        
        RequestBooks b = new RequestBooks(receiveIDU.getText(), jTextSearch2.getText(), (int)numDays1.getSelectedItem());
        rbFile.addEndRecord(b);
        limpiaTabla();
        
    }
    
    private void returnsRAV() throws IOException, ParseException{
        String strFecha = dateReq.getText();
        Date fecha = null;
        try {

        fecha = formatDate.parse(strFecha);
        
        } catch (ParseException ex) {

        ex.printStackTrace();

        }
        int day = ravFile.penalty(fecha, formatDate.parse(dateToday.getText()));
        if (returnsIDU1.getText().equals("")) {
            noexistReq.setText("El usuario no tiene devoluciones pendientes");
        }else{
            boolean rav = ravFile.buscar(returnsIDU1.getText());
        if (rav == true) {
            int a = ravFile.searchDays(returnsIDU1.getText(), day);
            if (day>a) {
                ravFile.penaltyRecord(returnsIDU1.getText());
                audiovisualFile.aumentar(returnsIDU1.getText());
            }else{
                ravFile.deleteRecord(returnsIDU1.getText());
                audiovisualFile.aumentar(returnsIDU1.getText());
            }
        }else{
            noexistReq.setText("El usuario no tiene devoluciones pendientes");
        }
        }
        
        limpiaTabla();
        readRAVData();
    }
    
    private void returnsRB() throws  IOException{
        boolean rb = rbFile.buscar(returnsIDU.getText());
        if (rb==true) {
            rbFile.deleteRecord(returnsIDU.getText());
            
        }else{
            JOptionPane.showMessageDialog(null, "El usuario no tiene devoluciones pendientes");
        }
        limpiaTabla();
        readRBData();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        Student = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxCareer = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTextName = new javax.swing.JTextField();
        jTextLastName = new javax.swing.JTextField();
        jTextIdentification = new javax.swing.JTextField();
        jTextAddress = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        Material = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        toBooks = new javax.swing.JRadioButton();
        toOther = new javax.swing.JRadioButton();
        Books = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextTitle = new javax.swing.JTextField();
        jTextAuthor = new javax.swing.JTextField();
        jTextYear = new javax.swing.JTextField();
        jTextEdition = new javax.swing.JTextField();
        addBooks = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jTextISBN = new javax.swing.JTextField();
        Others = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        otherTypes = new javax.swing.JComboBox<>();
        jTextCompany = new javax.swing.JTextField();
        addOthers = new javax.swing.JToggleButton();
        jLabel15 = new javax.swing.JLabel();
        jTextID = new javax.swing.JTextField();
        Solicitar = new javax.swing.JPanel();
        Book = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextSearch2 = new javax.swing.JTextField();
        btn_search2 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        numDays1 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        OtherMaterial = new javax.swing.JPanel();
        reqtype = new javax.swing.JTextField();
        reqcompany = new javax.swing.JTextField();
        reqavailable = new javax.swing.JTextField();
        jTextSearch1 = new javax.swing.JTextField();
        btn_search1 = new javax.swing.JToggleButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        numDays = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        noexistAV = new javax.swing.JLabel();
        dateReq = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        toBooks1 = new javax.swing.JRadioButton();
        toOther1 = new javax.swing.JRadioButton();
        nameShow = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        Carne1 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        receiveIDU1 = new javax.swing.JTextField();
        check1 = new javax.swing.JToggleButton();
        notexist = new javax.swing.JLabel();
        Carne = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        receiveIDU = new javax.swing.JTextField();
        check = new javax.swing.JToggleButton();
        notexist1 = new javax.swing.JLabel();
        Tabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Devoluciones = new javax.swing.JPanel();
        DAV = new javax.swing.JPanel();
        jButtonReturn = new javax.swing.JButton();
        returnsIDU1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        noexistReq = new javax.swing.JLabel();
        dateToday = new javax.swing.JTextField();
        DB = new javax.swing.JPanel();
        jButtonReturn1 = new javax.swing.JButton();
        returnsIDU = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        toBooks2 = new javax.swing.JRadioButton();
        toOther2 = new javax.swing.JRadioButton();
        nameShow1 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Font = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BiblioTech");
        setBackground(new java.awt.Color(255, 204, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(762, 500));

        jPanel2.setBackground(new java.awt.Color(255, 255, 153));

        Student.setBackground(new java.awt.Color(255, 255, 153));
        Student.setPreferredSize(new java.awt.Dimension(397, 347));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel2.setText("Apellidos");

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel3.setText("Cédula");

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel4.setText("Correo");

        jComboBoxCareer.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jComboBoxCareer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Agronomía", "Educación", "Informática" }));
        jComboBoxCareer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCareerActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel5.setText("Carrera");

        jTextName.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextNameActionPerformed(evt);
            }
        });

        jTextLastName.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextLastName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextLastNameActionPerformed(evt);
            }
        });

        jTextIdentification.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextIdentification.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextIdentification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextIdentificationActionPerformed(evt);
            }
        });

        jTextAddress.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextAddressActionPerformed(evt);
            }
        });

        jButtonAdd.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jButtonAdd.setText("Ingresar");
        jButtonAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ingresar Estudiante");

        javax.swing.GroupLayout StudentLayout = new javax.swing.GroupLayout(Student);
        Student.setLayout(StudentLayout);
        StudentLayout.setHorizontalGroup(
            StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(StudentLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                            .addComponent(jTextIdentification, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(StudentLayout.createSequentialGroup()
                            .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4))
                            .addGap(29, 29, 29)
                            .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBoxCareer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                                .addComponent(jTextName)))
                        .addGroup(StudentLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(jTextLastName)))
                    .addComponent(jButtonAdd)))
        );
        StudentLayout.setVerticalGroup(
            StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextIdentification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCareer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jButtonAdd)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        StudentLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAdd, jComboBoxCareer, jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jTextAddress, jTextIdentification, jTextLastName});

        Material.setBackground(new java.awt.Color(255, 255, 153));
        Material.setPreferredSize(new java.awt.Dimension(397, 347));

        jLabel13.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel13.setText("Tipo");

        toBooks.setText("Libro");
        toBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toBooksActionPerformed(evt);
            }
        });

        toOther.setText("Audiovisual");
        toOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toOtherActionPerformed(evt);
            }
        });

        Books.setBackground(new java.awt.Color(255, 255, 153));

        jLabel7.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel7.setText("Título");

        jLabel8.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel8.setText("Autor");

        jLabel9.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel9.setText("Año");

        jLabel10.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel10.setText("Edición");

        jTextTitle.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTitleActionPerformed(evt);
            }
        });

        jTextAuthor.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jTextYear.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jTextEdition.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        addBooks.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addBooks.setText("Ingresar");
        addBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBooksActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel14.setText("ISBN");

        jTextISBN.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        javax.swing.GroupLayout BooksLayout = new javax.swing.GroupLayout(Books);
        Books.setLayout(BooksLayout);
        BooksLayout.setHorizontalGroup(
            BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BooksLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(BooksLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jTextEdition, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                    .addComponent(addBooks)
                    .addGroup(BooksLayout.createSequentialGroup()
                        .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14))
                        .addGap(32, 32, 32)
                        .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextAuthor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextYear)
                            .addComponent(jTextTitle)
                            .addComponent(jTextISBN))))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        BooksLayout.setVerticalGroup(
            BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BooksLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BooksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextEdition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addBooks)
                .addGap(25, 25, 25))
        );

        Others.setBackground(new java.awt.Color(255, 255, 153));

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel11.setText("Objeto");

        jLabel12.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel12.setText("Fabricante");

        otherTypes.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        otherTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laptop", "Proyector", "Parlante", "CD", "DVD", " " }));

        jTextCompany.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        addOthers.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        addOthers.setText("Ingresar");
        addOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOthersActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel15.setText("ID");

        jTextID.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        javax.swing.GroupLayout OthersLayout = new javax.swing.GroupLayout(Others);
        Others.setLayout(OthersLayout);
        OthersLayout.setHorizontalGroup(
            OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OthersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(OthersLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(40, 40, 40)
                        .addComponent(jTextCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(addOthers)
                    .addGroup(OthersLayout.createSequentialGroup()
                        .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel15))
                        .addGap(75, 75, 75)
                        .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otherTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextID))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        OthersLayout.setVerticalGroup(
            OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OthersLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(otherTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(OthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jTextCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addOthers)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MaterialLayout = new javax.swing.GroupLayout(Material);
        Material.setLayout(MaterialLayout);
        MaterialLayout.setHorizontalGroup(
            MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MaterialLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(toBooks)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(toOther)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MaterialLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Books, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MaterialLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Others, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        MaterialLayout.setVerticalGroup(
            MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(toBooks)
                    .addComponent(toOther))
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MaterialLayout.createSequentialGroup()
                    .addContainerGap(47, Short.MAX_VALUE)
                    .addComponent(Books, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
            .addGroup(MaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MaterialLayout.createSequentialGroup()
                    .addContainerGap(47, Short.MAX_VALUE)
                    .addComponent(Others, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(18, Short.MAX_VALUE)))
        );

        Solicitar.setBackground(new java.awt.Color(255, 255, 153));
        Solicitar.setMinimumSize(new java.awt.Dimension(397, 347));
        Solicitar.setPreferredSize(new java.awt.Dimension(397, 347));

        Book.setBackground(new java.awt.Color(255, 255, 153));

        jTextField2.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jTextField5.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextSearch2.setToolTipText("");
        jTextSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSearch2ActionPerformed(evt);
            }
        });

        btn_search2.setText("Buscar");
        btn_search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search2ActionPerformed(evt);
            }
        });

        jToggleButton2.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jToggleButton2.setText("Solicitar");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        numDays1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        numDays1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel23.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel23.setText("Por ");

        jLabel24.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel24.setText("días");

        jLabel31.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel31.setText("Disponible");

        jLabel33.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel33.setText("Título");

        jLabel34.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel34.setText("Autor");

        jLabel35.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel35.setText("Edición");

        jLabel36.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel36.setText("Año");

        jTextField6.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        javax.swing.GroupLayout BookLayout = new javax.swing.GroupLayout(Book);
        Book.setLayout(BookLayout);
        BookLayout.setHorizontalGroup(
            BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookLayout.createSequentialGroup()
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BookLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jTextSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search2))
                    .addGroup(BookLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(BookLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numDays1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(jLabel24))
                            .addGroup(BookLayout.createSequentialGroup()
                                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jToggleButton2)
                                    .addGroup(BookLayout.createSequentialGroup()
                                        .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel33)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel36))
                                        .addGap(18, 18, 18)
                                        .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(BookLayout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(41, 41, 41))
        );
        BookLayout.setVerticalGroup(
            BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(BookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(numDays1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        OtherMaterial.setBackground(new java.awt.Color(255, 255, 153));

        reqtype.setEditable(false);
        reqtype.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        reqcompany.setEditable(false);
        reqcompany.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        reqavailable.setEditable(false);
        reqavailable.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        jTextSearch1.setToolTipText("");
        jTextSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSearch1ActionPerformed(evt);
            }
        });

        btn_search1.setText("Buscar");
        btn_search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_search1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel18.setText("Audiovisual");

        jLabel19.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel19.setText("Compañia");

        jLabel20.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel20.setText("Disponible");

        jToggleButton1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jToggleButton1.setText("Solicitar");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        numDays.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        numDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel21.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel21.setText("Por ");

        jLabel22.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel22.setText("días");

        noexistAV.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout OtherMaterialLayout = new javax.swing.GroupLayout(OtherMaterial);
        OtherMaterial.setLayout(OtherMaterialLayout);
        OtherMaterialLayout.setHorizontalGroup(
            OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtherMaterialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(OtherMaterialLayout.createSequentialGroup()
                        .addComponent(jTextSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_search1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, OtherMaterialLayout.createSequentialGroup()
                        .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addGroup(OtherMaterialLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20))
                        .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(OtherMaterialLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(reqtype, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reqcompany, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reqavailable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41))
                            .addGroup(OtherMaterialLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel22)
                                .addGap(40, 40, 40)
                                .addComponent(dateReq, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(OtherMaterialLayout.createSequentialGroup()
                        .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton1)
                            .addComponent(noexistAV, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        OtherMaterialLayout.setVerticalGroup(
            OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OtherMaterialLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search1))
                .addGap(18, 18, 18)
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqtype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqcompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqavailable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(3, 3, 3)
                .addComponent(noexistAV, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(OtherMaterialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(dateReq, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToggleButton1)
                .addGap(19, 19, 19))
        );

        jLabel17.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel17.setText("Tipo");

        toBooks1.setText("Libro");
        toBooks1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toBooks1ActionPerformed(evt);
            }
        });

        toOther1.setText("Audiovisual");
        toOther1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toOther1ActionPerformed(evt);
            }
        });

        nameShow.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel37.setText("Estudiante:");

        javax.swing.GroupLayout SolicitarLayout = new javax.swing.GroupLayout(Solicitar);
        Solicitar.setLayout(SolicitarLayout);
        SolicitarLayout.setHorizontalGroup(
            SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SolicitarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Book, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SolicitarLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameShow, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SolicitarLayout.createSequentialGroup()
                        .addComponent(toBooks1)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(toOther1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SolicitarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(OtherMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(47, Short.MAX_VALUE)))
        );
        SolicitarLayout.setVerticalGroup(
            SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SolicitarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(nameShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(toBooks1)
                    .addComponent(toOther1))
                .addGap(18, 34, Short.MAX_VALUE)
                .addComponent(Book, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(SolicitarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SolicitarLayout.createSequentialGroup()
                    .addContainerGap(89, Short.MAX_VALUE)
                    .addComponent(OtherMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(14, 14, 14)))
        );

        Carne1.setBackground(new java.awt.Color(255, 255, 153));

        jLabel26.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel26.setText("Ingrese su carne");

        receiveIDU1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveIDU1ActionPerformed(evt);
            }
        });

        check1.setText("COMPROBAR");
        check1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check1ActionPerformed(evt);
            }
        });

        notexist.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout Carne1Layout = new javax.swing.GroupLayout(Carne1);
        Carne1.setLayout(Carne1Layout);
        Carne1Layout.setHorizontalGroup(
            Carne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Carne1Layout.createSequentialGroup()
                .addGroup(Carne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Carne1Layout.createSequentialGroup()
                        .addGroup(Carne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Carne1Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(Carne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(receiveIDU1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(Carne1Layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(check1)))
                        .addGap(0, 67, Short.MAX_VALUE))
                    .addGroup(Carne1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(notexist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Carne1Layout.setVerticalGroup(
            Carne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Carne1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(18, 18, 18)
                .addComponent(receiveIDU1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notexist, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check1)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        Carne.setBackground(new java.awt.Color(255, 255, 153));

        jLabel16.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel16.setText("Ingrese su carne");

        receiveIDU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receiveIDUActionPerformed(evt);
            }
        });

        check.setText("COMPROBAR");
        check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActionPerformed(evt);
            }
        });

        notexist1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        notexist1.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout CarneLayout = new javax.swing.GroupLayout(Carne);
        Carne.setLayout(CarneLayout);
        CarneLayout.setHorizontalGroup(
            CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CarneLayout.createSequentialGroup()
                .addGroup(CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CarneLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(receiveIDU, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CarneLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(check)))
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CarneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(notexist1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        CarneLayout.setVerticalGroup(
            CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CarneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(receiveIDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(check)
                .addContainerGap(46, Short.MAX_VALUE))
            .addGroup(CarneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CarneLayout.createSequentialGroup()
                    .addGap(77, 77, 77)
                    .addComponent(notexist1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(77, Short.MAX_VALUE)))
        );

        Tabla.setBackground(new java.awt.Color(255, 255, 153));

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 51));

        jTable1.setBackground(new java.awt.Color(255, 204, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout TablaLayout = new javax.swing.GroupLayout(Tabla);
        Tabla.setLayout(TablaLayout);
        TablaLayout.setHorizontalGroup(
            TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        TablaLayout.setVerticalGroup(
            TablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
        );

        Devoluciones.setBackground(new java.awt.Color(255, 255, 153));
        Devoluciones.setMinimumSize(new java.awt.Dimension(397, 347));
        Devoluciones.setPreferredSize(new java.awt.Dimension(397, 347));

        DAV.setBackground(new java.awt.Color(255, 255, 153));
        DAV.setMinimumSize(new java.awt.Dimension(397, 240));
        DAV.setPreferredSize(new java.awt.Dimension(397, 240));

        jButtonReturn.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jButtonReturn.setText("Devolver");
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReturnActionPerformed(evt);
            }
        });

        returnsIDU1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel28.setText("Ingrese el ID");

        noexistReq.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout DAVLayout = new javax.swing.GroupLayout(DAV);
        DAV.setLayout(DAVLayout);
        DAVLayout.setHorizontalGroup(
            DAVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DAVLayout.createSequentialGroup()
                .addGroup(DAVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DAVLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jButtonReturn))
                    .addGroup(DAVLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(DAVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DAVLayout.createSequentialGroup()
                                .addComponent(returnsIDU1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(dateToday, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(noexistReq, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        DAVLayout.setVerticalGroup(
            DAVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DAVLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DAVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateToday)
                    .addComponent(returnsIDU1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noexistReq, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonReturn)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        DB.setBackground(new java.awt.Color(255, 255, 153));
        DB.setMinimumSize(new java.awt.Dimension(397, 240));
        DB.setPreferredSize(new java.awt.Dimension(397, 240));

        jButtonReturn1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jButtonReturn1.setText("Devolver");
        jButtonReturn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReturn1ActionPerformed(evt);
            }
        });

        returnsIDU.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel27.setText("Ingrese el ISBN");

        javax.swing.GroupLayout DBLayout = new javax.swing.GroupLayout(DB);
        DB.setLayout(DBLayout);
        DBLayout.setHorizontalGroup(
            DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBLayout.createSequentialGroup()
                .addGroup(DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DBLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jButtonReturn1))
                    .addGroup(DBLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(returnsIDU))))
                .addContainerGap(161, Short.MAX_VALUE))
        );
        DBLayout.setVerticalGroup(
            DBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DBLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(returnsIDU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButtonReturn1)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N
        jLabel25.setText("Tipo");

        toBooks2.setText("Libro");
        toBooks2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toBooks2ActionPerformed(evt);
            }
        });

        toOther2.setText("Audiovisual");
        toOther2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toOther2ActionPerformed(evt);
            }
        });

        nameShow1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N

        jLabel32.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jLabel32.setText("Estudiante:");

        javax.swing.GroupLayout DevolucionesLayout = new javax.swing.GroupLayout(Devoluciones);
        Devoluciones.setLayout(DevolucionesLayout);
        DevolucionesLayout.setHorizontalGroup(
            DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DevolucionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DevolucionesLayout.createSequentialGroup()
                        .addComponent(toBooks2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addComponent(toOther2))
                    .addGroup(DevolucionesLayout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameShow1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DevolucionesLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DAV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DevolucionesLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        DevolucionesLayout.setVerticalGroup(
            DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DevolucionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(nameShow1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(toBooks2)
                    .addComponent(toOther2))
                .addContainerGap(278, Short.MAX_VALUE))
            .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DevolucionesLayout.createSequentialGroup()
                    .addContainerGap(79, Short.MAX_VALUE)
                    .addComponent(DAV, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(47, Short.MAX_VALUE)))
            .addGroup(DevolucionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DevolucionesLayout.createSequentialGroup()
                    .addContainerGap(96, Short.MAX_VALUE)
                    .addComponent(DB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Font.setBackground(new java.awt.Color(255, 255, 153));
        Font.setMinimumSize(new java.awt.Dimension(742, 379));
        Font.setPreferredSize(new java.awt.Dimension(742, 379));

        jLabel29.setBackground(new java.awt.Color(255, 255, 102));
        jLabel29.setIcon(new javax.swing.ImageIcon("C:\\Users\\TokisakiKuro\\Documents\\NetBeansProjects\\U\\BiblioTechMontenegroChinchillaSemestreIAnno2018\\src\\resources\\Logo.jpg")); // NOI18N

        jLabel30.setIcon(new javax.swing.ImageIcon("C:\\Users\\TokisakiKuro\\Documents\\NetBeansProjects\\U\\BiblioTechMontenegroChinchillaSemestreIAnno2018\\src\\resources\\Library.png")); // NOI18N

        javax.swing.GroupLayout FontLayout = new javax.swing.GroupLayout(Font);
        Font.setLayout(FontLayout);
        FontLayout.setHorizontalGroup(
            FontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FontLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(70, 70, 70))
        );
        FontLayout.setVerticalGroup(
            FontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FontLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FontLayout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(386, Short.MAX_VALUE)
                .addComponent(Tabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Student, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(390, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Material, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(391, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Solicitar, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(412, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 171, Short.MAX_VALUE)
                    .addComponent(Carne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 279, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(225, 225, 225)
                    .addComponent(Carne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(225, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Devoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(384, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Font, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addComponent(Student, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addComponent(Material, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Solicitar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 105, Short.MAX_VALUE)
                    .addComponent(Carne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 106, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(105, 105, 105)
                    .addComponent(Carne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(106, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(Devoluciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(Font, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 204, 51));
        jMenuBar1.setFont(new java.awt.Font("Bahnschrift", 0, 18)); // NOI18N

        jMenu1.setText("Ingresar");

        jMenuItem1.setText("Estudiantes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Materiales");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Solicitar");

        jMenuItem3.setText("Solicitar Material");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Devoluciones");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxCareerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCareerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCareerActionPerformed

    private void jTextNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextNameActionPerformed

    private void jTextLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextLastNameActionPerformed

    private void jTextIdentificationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextIdentificationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextIdentificationActionPerformed

    private void jTextAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextAddressActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        try {
            addStudents();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void toOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toOtherActionPerformed
        toOthers();
        toBooks.setSelected(false);
    }//GEN-LAST:event_toOtherActionPerformed

    private void toBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toBooksActionPerformed
        toBooks();
        toOther.setSelected(false);
    }//GEN-LAST:event_toBooksActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Student.setVisible(true);
        Font.setVisible(false);
        Material.setVisible(false);
        Solicitar.setVisible(false);
        Carne.setVisible(false);
        Solicitar.setVisible(false);
        Tabla.setVisible(false);
        DB.setVisible(false);
        DAV.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Material.setVisible(true);
        Font.setVisible(false);
        Student.setVisible(false);
        Solicitar.setVisible(false);
        Carne.setVisible(false);
        Solicitar.setVisible(false);
        Tabla.setVisible(false);
        DB.setVisible(false);
        DAV.setVisible(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jTextTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTitleActionPerformed

    private void addBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBooksActionPerformed
        addBooks();
    }//GEN-LAST:event_addBooksActionPerformed

    private void addOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOthersActionPerformed
        try {
            addAudiovisuals();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addOthersActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Carne.setVisible(true);
        Font.setVisible(false);
        Material.setVisible(false);
        Student.setVisible(false);
        Solicitar.setVisible(false);
        Tabla.setVisible(false);
        Carne1.setVisible(false);
        Devoluciones.setVisible(false);
        limpiaTabla();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActionPerformed
        try {
            check();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_checkActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Carne1.setVisible(true);
        Font.setVisible(false);
        Devoluciones.setVisible(false);
        DB.setVisible(false);
        DAV.setVisible(false);
        Tabla.setVisible(false);
        Material.setVisible(false);
        Student.setVisible(false);
        Solicitar.setVisible(false);
        Carne.setVisible(false);
        toOther.setSelected(false);
        toOther1.setSelected(false);
        toOther2.setSelected(false);
        toBooks.setSelected(false);
        toBooks1.setSelected(false);
        toBooks2.setSelected(false);
        limpiaTabla();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void receiveIDUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveIDUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receiveIDUActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSearch1ActionPerformed

    private void btn_search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search1ActionPerformed
        try {
            searchAudiovisual();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_search1ActionPerformed

    private void jTextSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSearch2ActionPerformed
        
    }//GEN-LAST:event_jTextSearch2ActionPerformed

    private void btn_search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_search2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_search2ActionPerformed

    private void toBooks1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toBooks1ActionPerformed
        toOther1.setSelected(false);
        try {
            toBooks2();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        toOther1.setSelected(false);
        limpiaTabla();
        try {
            readAudiovisualData();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toBooks1ActionPerformed

    private void toOther1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toOther1ActionPerformed
        toBooks1.setSelected(false);
        try {
            toOthers2();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        toBooks1.setSelected(false);
        limpiaTabla();
            try {
            readAudiovisualData();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toOther1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        try {
            addRAV();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        try {
            addRB();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButtonReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReturnActionPerformed
        try {
            returnsRAV();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonReturnActionPerformed

    private void jButtonReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReturn1ActionPerformed
        try {
            returnsRB();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonReturn1ActionPerformed

    private void toBooks2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toBooks2ActionPerformed
        toOther2.setSelected(false);
        try {
            toBooks3();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toBooks2ActionPerformed

    private void toOther2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toOther2ActionPerformed
        toBooks2.setSelected(false);
        try {
            toOthers3();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toOther2ActionPerformed

    private void receiveIDU1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receiveIDU1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receiveIDU1ActionPerformed

    private void check1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check1ActionPerformed
        try {
            check2();
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_check1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Interfaz().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Book;
    private javax.swing.JPanel Books;
    private javax.swing.JPanel Carne;
    private javax.swing.JPanel Carne1;
    private javax.swing.JPanel DAV;
    private javax.swing.JPanel DB;
    private javax.swing.JPanel Devoluciones;
    private javax.swing.JPanel Font;
    private javax.swing.JPanel Material;
    private javax.swing.JPanel OtherMaterial;
    private javax.swing.JPanel Others;
    private javax.swing.JPanel Solicitar;
    private javax.swing.JPanel Student;
    private javax.swing.JPanel Tabla;
    private javax.swing.JButton addBooks;
    private javax.swing.JToggleButton addOthers;
    private javax.swing.JToggleButton btn_search1;
    private javax.swing.JToggleButton btn_search2;
    private javax.swing.JToggleButton check;
    private javax.swing.JToggleButton check1;
    private javax.swing.JTextField dateReq;
    private javax.swing.JTextField dateToday;
    private javax.swing.JToggleButton jButtonAdd;
    private javax.swing.JButton jButtonReturn;
    private javax.swing.JButton jButtonReturn1;
    private javax.swing.JComboBox<String> jComboBoxCareer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextAddress;
    private javax.swing.JTextField jTextAuthor;
    private javax.swing.JTextField jTextCompany;
    private javax.swing.JTextField jTextEdition;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextID;
    private javax.swing.JTextField jTextISBN;
    private javax.swing.JTextField jTextIdentification;
    private javax.swing.JTextField jTextLastName;
    private javax.swing.JTextField jTextName;
    private javax.swing.JTextField jTextSearch1;
    private javax.swing.JTextField jTextSearch2;
    private javax.swing.JTextField jTextTitle;
    private javax.swing.JTextField jTextYear;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JLabel nameShow;
    private javax.swing.JLabel nameShow1;
    private javax.swing.JLabel noexistAV;
    private javax.swing.JLabel noexistReq;
    private javax.swing.JLabel notexist;
    private javax.swing.JLabel notexist1;
    private javax.swing.JComboBox<String> numDays;
    private javax.swing.JComboBox<String> numDays1;
    private javax.swing.JComboBox<String> otherTypes;
    private javax.swing.JTextField receiveIDU;
    private javax.swing.JTextField receiveIDU1;
    private javax.swing.JTextField reqavailable;
    private javax.swing.JTextField reqcompany;
    private javax.swing.JTextField reqtype;
    private javax.swing.JTextField returnsIDU;
    private javax.swing.JTextField returnsIDU1;
    private javax.swing.JRadioButton toBooks;
    private javax.swing.JRadioButton toBooks1;
    private javax.swing.JRadioButton toBooks2;
    private javax.swing.JRadioButton toOther;
    private javax.swing.JRadioButton toOther1;
    private javax.swing.JRadioButton toOther2;
    // End of variables declaration//GEN-END:variables
}
