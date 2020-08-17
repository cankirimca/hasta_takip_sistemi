import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.time.ZoneId;
import java.util.Vector;

public class HastaTablosu {

    ArrayList<Hasta> hastalar;
    int hastaSayisi;

    public HastaTablosu(){
        //hasta yaşını hesaplamak için bugünün tarihine erişme
        LocalDate now = LocalDate.now(); //gets localDate
        hastaSayisi = 0;
        hastalar = new ArrayList<Hasta>();

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

                Hasta hs = new Hasta(hastaAdi, hastaSoyadi, hastaYasi, cinsiyet, yatmaTarihi);   //hasta objesini oluşturuyoruz
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
        String[][] hastaListesi = new String[hastaSayisi][5];
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy" );

        for(int u = 0; u < hastaSayisi; u++)
        {
            Hasta hasta = hastalar.get(u);
            hastaListesi[u][0] = hasta.getAd();
            hastaListesi[u][1] = hasta.getSoyad();
            hastaListesi[u][2] = hasta.getYas() + "";
            hastaListesi[u][3] = hasta.getCinsiyet() + "";
            hastaListesi[u][4] = "null"; //df.format(hasta.getYatmaTarihi()) + "";
        }

        String[] columns = {"Ad", "Soyad", "Yaş", "Cinsiyet", "Yatış Tarihi"};


         return new JTable(hastaListesi, columns);
    }

    public static void main(String[] args) {
        HastaTablosu ht = new HastaTablosu();
        ht.tabloOlustur();

    }


}
