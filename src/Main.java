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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.*;
import java.time.*;



public class Main extends JFrame {

    static JButton hastaEkle, yardim;
    static JPanel anaEkran, hastaSilmeEkrani;
    static JFrame frame;
    static HastaTablosu ht;
    static JRadioButton kadin, erkek;
    static JPanel buttonPanel;
    static JTable tablo;
    static HastaEkrani he;
    static JTextField degerGirmeYeri2, tcNoYeri, tcNoYeri2;
    static String[] servisler, doktorlar;
    static int servisId, doktorId;
    static JComboBox servisListesi, doktorListesi;
    static Statement st;
    static String url;
    static Connection con;
    static ResultSet rs;
    static String sqlStr;
    static boolean kayitliHasta;
    static JDialog hastaEklemeEkrani;



    static class HastaListesiListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {

            Hasta gosterilecekHasta = ht.hastalar.get(tablo.getSelectedRow());


            JButton geriDonmeButonu = new JButton("Ana Tabloya Dön");
            geriDonmeButonu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //frame.setVisible(false);
                    //frame = new JFrame("Hasta Takip");
                    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //frame.setSize(1000,600);
                    frame.getContentPane().removeAll();
                    anaEkran = new JPanel();
                    anaEkran.setLayout(new BorderLayout());
                    tablo = ht.tabloOlustur();
                    tablo.setCellSelectionEnabled(true);
                    tablo.getSelectionModel().addListSelectionListener(new HastaListesiListener());
                    anaEkran.add(new JScrollPane(tablo), BorderLayout.CENTER);
                    anaEkran.add(buttonPanel, BorderLayout.PAGE_START);
                    frame.getContentPane().add(anaEkran);
                    frame.revalidate();
                    frame.repaint();
                    //frame.setVisible(true);
                }
            });

            JButton hastaKaydiSil = new JButton("Hasta Kaydını Sil");

            hastaKaydiSil.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                        String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
                        Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
                        Statement st = con.createStatement();
                        String sqlStr = "delete from Hastalar where protokol_no = " + he.gosterilecekHasta.getProtokolNo();
                        System.out.println(sqlStr);
                        ResultSet rs = st.executeQuery(sqlStr);

                        frame.getContentPane().removeAll();
                        anaEkran = new JPanel();
                        anaEkran.setLayout(new BorderLayout());
                        ht = new HastaTablosu(servisler, doktorlar);
                        tablo = ht.tabloOlustur();
                        tablo.setCellSelectionEnabled(true);
                        tablo.getSelectionModel().addListSelectionListener(new HastaListesiListener());
                        anaEkran.add(new JScrollPane(tablo), BorderLayout.CENTER);
                        anaEkran.add(buttonPanel, BorderLayout.PAGE_START);
                        frame.getContentPane().add(anaEkran);
                        frame.revalidate();
                        frame.repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            JButton olcumEkle = new JButton("Yeni Ölçüm Kaydet");

            olcumEkle.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog ekle1 = new JDialog();
                    ekle1.setSize(300, 120);
                    ekle1.setLayout(new GridLayout(3, 1));
                    JLabel l = new JLabel("Eklemek istediğiniz verinin türünü giriniz.");
                    ekle1.add(l, 0);

                    String[] secenekler = {"Nabız", "Tansiyon", "Kan Şekeri", "Ateş", "Oksijen Satürasyonu", "Solunum"};
                    JComboBox degerTurleri = new JComboBox(secenekler);
                    ekle1.add(degerTurleri, 1);
                    JButton araButon = new JButton("Seç");

                    araButon.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent ev) {
                            int index = degerTurleri.getSelectedIndex();
                            ekle1.setVisible(false);

                            String defaultText = "";

                            switch (index) {
                                case 0:
                                    defaultText = "Nabız (bpm)";
                                    break;
                                case 1:
                                    defaultText = "Sistolik Tansiyon (mmHg)";
                                    break;
                                case 2:
                                    defaultText = "Kan Şekeri (mg/dL)";
                                    break;
                                case 3:
                                    defaultText = "Ateş (°C)";
                                    break;
                                case 4:
                                    defaultText = "Oksijen Satürasyon (%)";
                                    break;
                                case 5:
                                    defaultText = "Solunum(br/min)";
                                    break;
                            }

                            JDialog ekle2 = new JDialog();
                            ekle2.setSize(300, 150);
                            if (index == 1)
                                ekle2.setLayout(new GridLayout(4, 1));
                            else
                                ekle2.setLayout(new GridLayout(3, 1));
                            JTextField degerGirmeYeri = new JTextField(defaultText);
                            degerGirmeYeri2 = null;

                            JLabel label2 = new JLabel("Ölçülen değeri giriniz.");
                            ekle2.add(label2, 0);

                            if (index == 1) {
                                degerGirmeYeri2 = new JTextField("Diastolik Tansiyon (mmHg)");
                                ekle2.add(degerGirmeYeri, 1);
                                ekle2.add(degerGirmeYeri2, 2);
                            } else {
                                ekle2.add(degerGirmeYeri, 1);
                            }


                            ekle2.setSize(200, 200);

                            JButton ekle3 = new JButton("Ekle");
                            ekle3.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        if (index == 1) {
                                            int deger = Integer.parseInt(degerGirmeYeri.getText());
                                            he.olcumEkle(deger, 1);
                                            deger = Integer.parseInt(degerGirmeYeri2.getText());
                                            he.olcumEkle(deger, 6);
                                        } else {
                                            int deger = Integer.parseInt(degerGirmeYeri.getText());
                                            he.olcumEkle(deger, index);
                                        }
                                        ekle2.setVisible(false);

                                        frame.getContentPane().removeAll();
                                        he = new HastaEkrani(gosterilecekHasta, geriDonmeButonu, olcumEkle, hastaKaydiSil);
                                        frame.getContentPane().add(he.getPanel());
                                        frame.revalidate();
                                        frame.repaint();
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(he.getPanel(), "Bir hata oluştu. Lütfen tekrar deneyiniz.");
                                    }
                                }
                            });
                            if (index == 1)
                                ekle2.add(ekle3, 3);
                            else
                                ekle2.add(ekle3, 2);

                            ekle2.setVisible(true);
                        }
                    });
                    ekle1.add(araButon, 2);
                    ekle1.setVisible(true);


                }
            });


            he = new HastaEkrani(gosterilecekHasta, geriDonmeButonu, olcumEkle, hastaKaydiSil);
            System.out.println(tablo.getSelectedRow());

            frame.getContentPane().removeAll();

            frame.getContentPane().add(he.getPanel());
            frame.revalidate();
            frame.repaint();

        }
    }


    public static void hastaEkle() {
        kayitliHasta = false;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
            con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
            st = con.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JDialog hastaSorgula = new JDialog(frame, "Hasta Sorgula", true);
        hastaSorgula.setSize(500, 300);

        hastaSorgula.setLayout(new BorderLayout());
        hastaSorgula.add(new JLabel("Lütfen hastanın TC Kimlik Numarasını giriniz."), BorderLayout.NORTH);
        tcNoYeri2 = new JTextField();
        hastaSorgula.add(tcNoYeri2, BorderLayout.CENTER);
        JButton tcNoSorgula = new JButton("Sorgula");
        tcNoSorgula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sqlStr = "select * from hastalar where tc_no = " + tcNoYeri2.getText();
                    rs = st.executeQuery(sqlStr);
                    //hastanın sisteme giriş yapıp yapmadığı belirlenir
                    if (rs.next())
                        kayitliHasta = true;

                    hastaSorgula.setVisible(false);
                    hastaEklemeEkrani = new JDialog(frame, "Hasta Ekle", true);
                    hastaEklemeEkrani.setSize(1000, 600);
                    hastaEklemeEkrani.add(new JLabel("Lütfen eklemek istediğiniz hastanın bilgilerini giriniz\n"));
                    hastaEklemeEkrani.setLayout(new GridLayout(19, 2));
                    JTextField adYeri = new JTextField();
                    JTextField soyadYeri = new JTextField();
                    JTextField dogumTarihiYeri = new JTextField();
                    JTextField yatısTarihiYeri = new JTextField();
                    JTextField gunubirlikYeri = new JTextField();
                    JTextField doktorYeri = new JTextField();
                    tcNoYeri = new JTextField();

                    kadin = new JRadioButton("Kadın");
                    erkek = new JRadioButton("Erkek");

                    try {
                        hastaEklemeEkrani.add(new JLabel("Ad: "));
                        if (kayitliHasta)
                            hastaEklemeEkrani.add(new JLabel(rs.getString(1)));
                        else
                            hastaEklemeEkrani.add(adYeri);

                        hastaEklemeEkrani.add(new JLabel("Soyad: "));
                        if (kayitliHasta)
                            hastaEklemeEkrani.add(new JLabel(rs.getString(2)));
                        else
                            hastaEklemeEkrani.add(soyadYeri);

                        hastaEklemeEkrani.add(new JLabel("Cinsiyet:"));
                        if (kayitliHasta) {
                            String c = "KADIN";
                            if (rs.getInt(4) == 0)
                                c = "ERKEK";
                            hastaEklemeEkrani.add(new JLabel(c));

                        } else {
                            hastaEklemeEkrani.add(kadin);
                            hastaEklemeEkrani.add(erkek);
                        }

                        hastaEklemeEkrani.add(new JLabel("Doğum Tarihi (gg aa yyyy): "));
                        if (kayitliHasta) {
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String text = df.format(rs.getDate(3));
                            hastaEklemeEkrani.add(new JLabel(text));
                        } else
                            hastaEklemeEkrani.add(dogumTarihiYeri);

                        hastaEklemeEkrani.add(new JLabel("TC Kimlik No: "));
                        if (kayitliHasta)
                            hastaEklemeEkrani.add(new JLabel(rs.getString(6)));
                        else
                            hastaEklemeEkrani.add(new JLabel(tcNoYeri2.getText()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    hastaEklemeEkrani.add(new JLabel("Yatış Tarihi (gg aa yyyy): "));
                    hastaEklemeEkrani.add(yatısTarihiYeri);


                    //hastaEklemeEkrani.add(doktorYeri);
                    hastaEklemeEkrani.add(new JLabel("Servis: "));
                    servisListesi = new JComboBox(servisler);
                    servisListesi.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            servisId = servisListesi.getSelectedIndex();
                        }
                    });

                    doktorListesi = new JComboBox(doktorlar);
                    servisListesi.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            doktorId = doktorListesi.getSelectedIndex();
                        }
                    });


                    hastaEklemeEkrani.add(servisListesi);
                    hastaEklemeEkrani.add(new JLabel("Doktor: "));
                    hastaEklemeEkrani.add(doktorListesi);

                    //hastaEklemeEkrani.add(new JLabel("Günübirlik"))


                    JButton eklemeDugmesi = new JButton("Ekle");

                    eklemeDugmesi.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                String isim, soyisim, dt, yt;
                                int drId;
                                int tc;

                                if (!kayitliHasta)
                                    isim = adYeri.getText();
                                else
                                    isim = rs.getString(1);

                                if (!kayitliHasta)
                                    soyisim = soyadYeri.getText();
                                else
                                    soyisim = rs.getString(2);

                                if (!kayitliHasta)
                                    dt = dogumTarihiYeri.getText();
                                else {
                                    SimpleDateFormat df = new SimpleDateFormat("dd MM yyyy");
                                    dt = df.format(rs.getDate(3));
                                }


                                tc = Integer.parseInt(tcNoYeri2.getText());


                                yt = yatısTarihiYeri.getText();
                                //drId = Integer.parseInt(doktorYeri.getText());

                                //String ser = servisYeri.getText();


                                Scanner scan;
                                int day, month, year;
                                String dogumTarihi = "";

                                if (!kayitliHasta) {
                                    //ilk tarihi formatlama
                                    scan = new Scanner(dt);
                                    day = scan.nextInt();
                                    month = scan.nextInt();
                                    year = scan.nextInt();
                                    dogumTarihi = day + "/" + month + "/" + year;
                                }


                                //ikinci tarihi formatlama
                                scan = new Scanner(yt);
                                day = scan.nextInt();
                                month = scan.nextInt();
                                year = scan.nextInt();
                                String yatmaTarihi = day + "/" + month + "/" + year;


                                int cinsiyet = 1;
                                if (erkek.isSelected())
                                    cinsiyet = 0;
                                else if (kadin.isSelected())
                                    cinsiyet = 1;
                                else if(!kayitliHasta)
                                    throw new Exception();

                    /*/veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
                    Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
                    Statement st = con.createStatement();*/

                                int pr = -1;
                                if(!kayitliHasta)
                                    pr = (int) (Math.random() * 100000);
                                else
                                    pr = rs.getInt(5);

                                if (!kayitliHasta)
                                    sqlStr = "insert into HASTALAR values('" + isim + "', '" + soyisim
                                            + "', '" + dogumTarihi + "', " + cinsiyet + ", " + pr + ", " + tc + ")";
                                rs = st.executeQuery(sqlStr);

                                sqlStr = "insert into HASTA_GELISLERI values( " + pr + ", '" + yatmaTarihi + "', "  + 1 + ", " + servisId + ", " + doktorId + ")";
                                rs = st.executeQuery(sqlStr);

                                String sqlStr = "select * from hastalar where protokol_no = " + pr + " AND tc_no = " + tc;
                                ResultSet rs = st.executeQuery(sqlStr);


                                //ht = new HastaTablosu();
                                hastaEklemeEkrani.setVisible(false);
                                ht = new HastaTablosu(servisler, doktorlar);
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
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(frame, "Eksik veya hatalı bilgi girdiniz. \nLütfen tekrar deneyiniz.");
                                ex.printStackTrace();
                            }
                        }
                    });




                    hastaEklemeEkrani.add(eklemeDugmesi);
                    hastaEklemeEkrani.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        hastaSorgula.add(tcNoSorgula, BorderLayout.SOUTH);
        hastaSorgula.setVisible(true);



        //hastaEklemeEkrani = null;



        //hastaEklemeEkrani.setVisible(true);

        }



        public static void main (String[]args){

            try {
                //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
                Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
                Statement st = con.createStatement();
                String sqlStr = "SELECT max(servis_id) FROM servisler";
                System.out.println(sqlStr);
                ResultSet rs = st.executeQuery(sqlStr);
                rs.next();
                int size = rs.getInt(1) + 1;
                servisler = new String[size];

                sqlStr = "select * from servisler";
                System.out.println(sqlStr);
                rs = st.executeQuery(sqlStr);

                int index = 0;

                while (rs.next()) {
                    servisler[index++] = rs.getString(2);
                }

                sqlStr = "SELECT max(doktor_id) FROM doktorlar";
                System.out.println(sqlStr);
                rs = st.executeQuery(sqlStr);
                rs.next();
                size = rs.getInt(1) + 1;
                doktorlar = new String[size];

                sqlStr = "select * from doktorlar";
                System.out.println(sqlStr);
                rs = st.executeQuery(sqlStr);

                index = 0;

                while (rs.next()) {
                    doktorlar[index++] = rs.getString(2) + " " + rs.getString(3);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            System.out.println("bu bir programdır");
            ht = new HastaTablosu(servisler, doktorlar);
            frame = new JFrame("Hasta Takip");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);

            buttonPanel = new JPanel();
            hastaEkle = new JButton("Hasta Ekle");
            hastaEkle.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hastaEkle();
                }
            });
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
