import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.*;
import java.time.*;

public class Main extends JFrame{

    static JButton hastaEkle, hastaSil, yardim;
    static JPanel anaEkran, hastaSilmeEkrani;
    static JFrame frame;
    static HastaTablosu ht;
    static JRadioButton kadin, erkek;
    static JPanel buttonPanel;
    static JTable tablo;

    static class HastaListesiListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int satir = tablo.getSelectedRow();
            Hasta gosterilecekHasta =ht.hastalar.get(satir);
            HastaEkrani he = new HastaEkrani(gosterilecekHasta);
            System.out.println(tablo.getSelectedRow());



            frame.setVisible(false);
            frame = new JFrame("Hasta Takip");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000,600);
            frame.getContentPane().add(he.getPanel());
            frame.setVisible(true);

        }
    }

    static public void hastaEkle() {

        JDialog hastaEklemeEkrani = new JDialog(frame, "Hasta Ekle", true);
        hastaEklemeEkrani.setSize(1000, 600);
        hastaEklemeEkrani.add(new JLabel("Lütfen eklemek istediğiniz hastanın bilgilerini giriniz\n"));
        hastaEklemeEkrani.setLayout(new GridLayout(19,2));
        JTextField adYeri = new JTextField();
        JTextField soyadYeri = new JTextField();
        JTextField dogumTarihiYeri = new JTextField();
        JTextField yatısTarihiYeri = new JTextField();
        JTextField protokolNoYeri = new JTextField();
        JTextField doktorYeri = new JTextField();
        JTextField servisYeri = new JTextField();
        kadin = new JRadioButton("Kadın");
        erkek = new JRadioButton("Erkek");
        hastaEklemeEkrani.add(new JLabel("Ad: "));
        hastaEklemeEkrani.add(adYeri);
        hastaEklemeEkrani.add(new JLabel("Soyad: "));
        hastaEklemeEkrani.add(soyadYeri);
        hastaEklemeEkrani.add(new JLabel("Cinsiyet:"));
        hastaEklemeEkrani.add(kadin);
        hastaEklemeEkrani.add(erkek);
        hastaEklemeEkrani.add(new JLabel("Doğum Tarihi (gg aa yyyy): "));
        hastaEklemeEkrani.add(dogumTarihiYeri);
        hastaEklemeEkrani.add(new JLabel("Yatış Tarihi (gg aa yyyy): "));
        hastaEklemeEkrani.add(yatısTarihiYeri);
        hastaEklemeEkrani.add(new JLabel("Protokol No: "));
        hastaEklemeEkrani.add(protokolNoYeri);
        hastaEklemeEkrani.add(new JLabel("Doktor: "));
        hastaEklemeEkrani.add(doktorYeri);
        hastaEklemeEkrani.add(new JLabel("Servis: "));
        hastaEklemeEkrani.add(servisYeri);



        JButton eklemeDugmesi = new JButton("Ekle");
        eklemeDugmesi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String isim = adYeri.getText();
                    String soyisim = soyadYeri.getText();
                    String dt = dogumTarihiYeri.getText();
                    String yt = yatısTarihiYeri.getText();
                    String dr = doktorYeri.getText();
                    String pr = protokolNoYeri.getText();
                    String ser = servisYeri.getText();

                    //ilk tarihi formatlama
                    Scanner scan = new Scanner(dt);
                    int day = scan.nextInt();
                    int month = scan.nextInt();
                    int year = scan.nextInt();
                    String dogumTarihi = day + "/" + month + "/" + year;


                    //ikinci tarihi formatlama
                    scan = new Scanner(yt);
                    day = scan.nextInt();
                    month = scan.nextInt();
                    year = scan.nextInt();
                    String yatmaTarihi = day + "/" + month + "/" + year;


                    int cinsiyet = 1;
                    if(erkek.isSelected())
                        cinsiyet = 0;
                    else if(kadin.isSelected())
                        cinsiyet = 1;
                    else
                        throw new Exception();

                    //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
                    Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
                    Statement st = con.createStatement();
                    String sqlStr = "insert into Hastalar values('" + isim + "', '" + soyisim + "', '" + dogumTarihi
                            + "', " + cinsiyet + ", '" + yatmaTarihi + "', " + pr + ", '" + dr + "', '" + ser + "')";
                    System.out.println(sqlStr);
                    ResultSet rs = st.executeQuery(sqlStr);

                    //ht = new HastaTablosu();
                    hastaEklemeEkrani.setVisible(false);
                    ht = new HastaTablosu();
                    frame.getContentPane().remove(anaEkran);
                    anaEkran = new JPanel();
                    anaEkran.setLayout(new BorderLayout());
                    tablo = ht.tabloOlustur();
                    tablo.setCellSelectionEnabled(true);
                    tablo.getSelectionModel().addListSelectionListener(new HastaListesiListener());
                    anaEkran.add(new JScrollPane(tablo), BorderLayout.CENTER);
                    anaEkran.add(buttonPanel, BorderLayout.PAGE_START);
                    frame.getContentPane().add(anaEkran);
                    frame.revalidate();

                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(frame, "Eksik veya hatalı bilgi girdiniz. \nLütfen tekrar deneyiniz.");
                }
            }
        } );
        hastaEklemeEkrani.add(eklemeDugmesi);
        hastaEklemeEkrani.setVisible(true);

    }

    public void hastaSil(){
         try {
             //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
             Class.forName("oracle.jdbc.driver.OracleDriver");
             String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
             Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
             Statement st = con.createStatement();
             String sqlStr = "insert into Hastalar values('" + isim + "', '" + soyisim + "', '" + dogumTarihi
                     + "', " + cinsiyet + ", '" + yatmaTarihi + "', " + pr + ", '" + dr + "', '" + ser + "')";
             System.out.println(sqlStr);
             ResultSet rs = st.executeQuery(sqlStr);
         }

    }

    public static void main(String[] args) {

        System.out.println("bu bir programdır");
        ht = new HastaTablosu();
        frame = new JFrame("Hasta Takip");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);

        buttonPanel = new JPanel();
        hastaEkle = new JButton("Hasta Ekle");
        hastaEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hastaEkle();
            }
        } );
        //hastaSil = new JButton("Hasta Sil");
        yardim = new JButton("Yardım");
        yardim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Hasta Takip Sistemine hoş geldiniz. Buradan hastanenizde yatan hastaların bilgilerini ve " +
                        "vital bulgularını takip edebilirsiniz. \nYeni hasta eklemek için ekranın üst kısmındaki 'Hasta Ekle' " +
                        "butonunu kullanabilirsiniz.\nTablodaki hastaların üzerine tıklayarak her hastanın özel bilgilerine ulaşabilirsiniz.\n" +
                        " Her türlü hata bildirimi ve öneriler için 'cankirimca@gmail.com' adresine ulaşabilirsiniz.");

            }
        });
        buttonPanel.add(hastaEkle, BorderLayout.LINE_START);
        //buttonPanel.add(hastaSil, BorderLayout.CENTER);
        buttonPanel.add(yardim, BorderLayout.LINE_END);

        //ana ekrandaki elementleri ana ekrana ekliyoruz
        anaEkran = new JPanel();
        anaEkran.setLayout(new BorderLayout());
        tablo = ht.tabloOlustur();
        tablo.setCellSelectionEnabled(true);
        tablo.getSelectionModel().addListSelectionListener(new HastaListesiListener());
        anaEkran.add(new JScrollPane(tablo), BorderLayout.CENTER);
        anaEkran.add(buttonPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(anaEkran);




        frame.setVisible(true);
    }
}