import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;


public class HastaTablosu {



    ArrayList<Hasta> hastalar;
    int hastaSayisi;
    JTable tablo;
    String[] sList;

    public HastaTablosu(String[] servisListesi){
        //hasta yaşını hesaplamak için bugünün tarihine erişme
        LocalDate now = LocalDate.now(); //gets localDate
        hastaSayisi = 0;
        hastalar = new ArrayList<Hasta>();
        sList = servisListesi;

        try{
            //veritabanı ile bağlantı sağlama
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
            Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
            Statement st = con.createStatement();
            String sqlStr = "select * from Hastalar";
            ResultSet rs = st.executeQuery(sqlStr);

            //aşağıdaki döngüde hastaların bilgileri veritabanından çekilerek hasta objelerine kaydediliyor
            while(rs.next()) {
                LocalDate dob = rs.getDate(3).toLocalDate();
                Period diff = Period.between(dob, now);
                int hastaYasi = diff.getYears();
                String hastaAdi = rs.getString(1);
                String hastaSoyadi = rs.getString(2);
                Date yatmaTarihi = rs.getDate(5);
                int protokolNo = rs.getInt(6);
                String doktor = rs.getString(7);
                String servis = servisListesi[rs.getInt(8)];
                Hasta.Cinsiyet cinsiyet;

                //ZoneId defaultZoneId = ZoneId.systemDefault();
                //Date yatmaTarihi = Date.from(now.atStartOfDay(defaultZoneId).toInstant());

                //Cinsiyet değeri 0 ise erkek demek, 1 ise kadın demek
                if(rs.getInt(4) == 0){
                    cinsiyet = Hasta.Cinsiyet.ERKEK;
                }
                else{
                    cinsiyet = Hasta.Cinsiyet.KADIN;
                }

                Hasta hs = new Hasta(hastaAdi, hastaSoyadi, hastaYasi, cinsiyet, yatmaTarihi, protokolNo, doktor, servis);   //hasta objesini oluşturuyoruz
                hastalar.add(hs);    //hastayı listeye ekliyoruz
                System.out.println(hs.toString());

                hastaSayisi++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void hastaEkle(Hasta h){
        hastalar.add(h);
    }

    public void hastaSil(){}

    public JTable tabloOlustur(){
        String[][] hastaListesi = new String[hastaSayisi][8];
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy" );

        for(int u = 0; u < hastaSayisi; u++)
        {
            Hasta hasta = hastalar.get(u);
            hastaListesi[u][0] = hasta.getAd();
            hastaListesi[u][1] = hasta.getSoyad();
            hastaListesi[u][2] = hasta.getYas() + "";
            hastaListesi[u][3] = hasta.getCinsiyet() + "";
            hastaListesi[u][4] = df.format(hasta.getYatmaTarihi()) + "";
            hastaListesi[u][5] = hasta.getProtokolNo() + "";
            hastaListesi[u][6] = hasta.getDoktor();
            hastaListesi[u][7] = hasta.getServis();
        }

        String[] columns = {"Ad", "Soyad", "Yaş", "Cinsiyet", "Yatış Tarihi", "Protokol No", "Doktor", "Servis"};
        return new JTable(hastaListesi, columns);

    }

    public static void main(String[] args) {
        //HastaTablosu ht = new HastaTablosu();
       // ht.tabloOlustur();

        try {
            //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
            Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
            Statement st = con.createStatement();
            Scanner scan = new Scanner(System.in);

            int i = 7;
            while(true) {
                System.out.println("Gir: G");
                String sqlStr = "insert into servisler values(" + i++ + ", '" + scan.nextLine() + "', " + 1 + ")";
                System.out.println(sqlStr);
                ResultSet rs = st.executeQuery(sqlStr);
            }
        }
        catch(Exception ex){ex.printStackTrace();}

    }


}