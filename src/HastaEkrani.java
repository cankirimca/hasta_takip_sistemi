import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.time.ZoneId;


public class HastaEkrani implements MouseListener {

    JPanel hastaEkrani;
    Hasta gosterilecekHasta;
    JTextField degerGirmeYeri, degerGirmeYeri2;
    JButton geriButonu, hastaSilmeButonu, olcumEklemeButonu;


    //tablo ve grafikler için ölçülecek değerlerin depolandığı listeler
    //nabız için
    ArrayList<Integer> nDeger;
    ArrayList<java.sql.Date> nTarih;
    ArrayList<String> nSaat;
    //sistolik tansiyon için
    ArrayList<Integer> stDeger;
    ArrayList<java.sql.Date> stTarih;
    ArrayList<String> stSaat;
    //diastolik tansiyon için
    ArrayList<Integer> dtDeger;
    ArrayList<java.sql.Date> dtTarih;
    ArrayList<String> dtSaat;
    //solunum için
    ArrayList<Integer> sDeger;
    ArrayList<java.sql.Date> sTarih;
    ArrayList<String> sSaat;
    //ateş için
    ArrayList<Integer> aDeger;
    ArrayList<java.sql.Date> aTarih;
    ArrayList<String> aSaat;
    //kan şekeri için
    ArrayList<Integer> ksDeger;
    ArrayList<java.sql.Date> ksTarih;
    ArrayList<String> ksSaat;
    //oksijen satürasyonu için
    ArrayList<Integer> osDeger;
    ArrayList<java.sql.Date> osTarih;
    ArrayList<String> osSaat;

    public HastaEkrani(Hasta h, JButton geri, JButton olcum, JButton sil) {

        nDeger = new ArrayList<Integer>();
        nTarih = new ArrayList<java.sql.Date>();
        nSaat = new ArrayList<String>();

        stDeger = new ArrayList<Integer>();
        stTarih = new ArrayList<java.sql.Date>();
        stSaat = new ArrayList<String>();

        dtDeger = new ArrayList<Integer>();
        dtTarih = new ArrayList<java.sql.Date>();
        dtSaat = new ArrayList<String>();

        sDeger = new ArrayList<Integer>();
        sTarih = new ArrayList<java.sql.Date>();
        sSaat = new ArrayList<String>();

        aDeger = new ArrayList<Integer>();
        aTarih = new ArrayList<java.sql.Date>();
        aSaat = new ArrayList<String>();

        ksDeger = new ArrayList<Integer>();
        ksTarih = new ArrayList<java.sql.Date>();
        ksSaat = new ArrayList<String>();

        osDeger = new ArrayList<Integer>();
        osTarih = new ArrayList<java.sql.Date>();
        osSaat = new ArrayList<String>();

        geriButonu = geri;
        hastaSilmeButonu = sil;
        olcumEklemeButonu = olcum;

        gosterilecekHasta = h;
        hastaEkrani = new JPanel();
        hastaEkrani.setLayout(new BorderLayout());
        JPanel hastaTablosu = new JPanel();
        JPanel hastaBilgileri = new JPanel();
        hastaBilgileri.setLayout(new GridLayout(1, 4));

        JLabel adLabel = new JLabel("Ad: " + gosterilecekHasta.getAd());
        adLabel.setBackground(Color.ORANGE);
        adLabel.setOpaque(true);
        hastaBilgileri.add(adLabel);

        JLabel soyadLabel = new JLabel("Soyad: " + gosterilecekHasta.getSoyad());
        soyadLabel.setBackground(Color.ORANGE);
        soyadLabel.setOpaque(true);
        hastaBilgileri.add(soyadLabel);

        JLabel yasLabel = new JLabel("Yaş: " + gosterilecekHasta.getYas());
        yasLabel.setBackground(Color.ORANGE);
        yasLabel.setOpaque(true);
        hastaBilgileri.add(yasLabel);

        JLabel cinsiyetLabel = new JLabel("Cinsiyet: " + gosterilecekHasta.getCinsiyet());
        cinsiyetLabel.setBackground(Color.ORANGE);
        cinsiyetLabel.setOpaque(true);
        hastaBilgileri.add(cinsiyetLabel);
        hastaBilgileri.setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel grafikPaneli = new JPanel();
        grafikPaneli.setLayout(new GridLayout(4,2));

        for(int p = 0; p < 7; p++)
        {
            HastaGrafigi hg = grafikOlustur(p, 300, 100);

            hg.addMouseListener(this);
            grafikPaneli.add(hg, p);
        }
        hastaEkrani.add(grafikPaneli, BorderLayout.SOUTH);




        hastaEkrani.add(hastaBilgileri, BorderLayout.NORTH);
        hastaEkrani.add(new JScrollPane(degerTablosuOlustur()), BorderLayout.CENTER);
        JPanel butonPaneli = new JPanel();
        butonPaneli.setLayout(new BorderLayout());

        butonPaneli.add(olcumEklemeButonu, BorderLayout.CENTER);
        butonPaneli.add(hastaSilmeButonu, BorderLayout.NORTH);
        butonPaneli.add(geriButonu, BorderLayout.SOUTH);
        grafikPaneli.add(butonPaneli, 7);



    }

    private JTable degerTablosuOlustur(){
        try {
            //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
            Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
            Statement st = con.createStatement();

            String sqlStr = "select * from NABIZ_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            System.out.println(sqlStr);
            ResultSet rs = st.executeQuery(sqlStr);
            while (rs.next()){
                int k = rs.getInt(2);
                nDeger.add(rs.getInt(2));
                nTarih.add(rs.getDate(3));
                nSaat.add(rs.getString(4));
            }

            sqlStr = "select * from SISTOLIK_TANSIYON_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                stDeger.add(rs.getInt(2));
                stTarih.add(rs.getDate(3));
                stSaat.add(rs.getString(4));
            }

            sqlStr = "select * from DIASTOLIK_TANSIYON_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                dtDeger.add(rs.getInt(2));
                dtTarih.add(rs.getDate(3));
                dtSaat.add(rs.getString(4));
            }

            sqlStr = "select * from SOLUNUM_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                sDeger.add(rs.getInt(2));
                sTarih.add(rs.getDate(3));
                sSaat.add(rs.getString(4));
            }

            sqlStr = "select * from KAN_SEKERI_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                ksDeger.add(rs.getInt(2));
                ksTarih.add(rs.getDate(3));
                ksSaat.add(rs.getString(4));
            }

            sqlStr = "select * from ATES_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                aDeger.add(rs.getInt(2));
                aTarih.add(rs.getDate(3));
                aSaat.add(rs.getString(4));
            }

            sqlStr = "select * from OKSIJEN_SATURASYON_DEGERLERI where protokol_no = " + gosterilecekHasta.getProtokolNo();
            rs = st.executeQuery(sqlStr);
            while (rs.next()){
                osDeger.add(rs.getInt(2));
                osTarih.add(rs.getDate(3));
                osSaat.add(rs.getString(4));
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        System.out.println(aDeger.size());
        System.out.println(nDeger.size());
        System.out.println(stDeger.size());

        String sonNabiz, sonSTansiyon, sonDTansiyon, sonAtes, sonKanSekeri, sonSolunum, sonOksSat;
        try {
            sonNabiz = nDeger.get(nDeger.size() - 1) + "";
        }
        catch(Exception e)
        {
            sonNabiz = "VeriBulunamadı!";
        }

        try {
            sonSTansiyon = stDeger.get(stDeger.size() - 1) + "";
        }
        catch (Exception e)
        {
            sonSTansiyon = "Veri Bulunamadı!";
        }

        try {
            sonDTansiyon = dtDeger.get(dtDeger.size() - 1)+"";
        }
        catch (Exception e)
        {
            sonDTansiyon = "Veri Bulunamadı!";
        }

        try {
            sonAtes = aDeger.get(aDeger.size() - 1)+"";
        }
        catch(Exception e)
        {
            sonAtes = "Veri bulunamadı!";
        }

        try {
            sonKanSekeri = ksDeger.get(ksDeger.size() - 1)+"";
        }
        catch(Exception e)
        {
            sonKanSekeri = "Veri bulunamadı!";
        }

        try {
            sonSolunum = sDeger.get(sDeger.size() - 1) + "";
        }
        catch(Exception e)
        {
            sonSolunum = "Veri bulunamadı";
        }

        try {
            sonOksSat = osDeger.get(osDeger.size() - 1)+"";
        }
        catch (Exception e)
        {
            sonOksSat = "Veri bulunamadı!";
        }

        String[][] anaTablo = new String[6][7];
        String [][] maxMinTablosu = maxMinDegerBulma();
        String[] basliklar = {" ", "Son Değer", "Zaman", "Max (Son 10 gün)", "Max (Son 24 saat)", "Min (Son 10 gün)", "Min (Son 24 saat)"};
        anaTablo[0][0] = "Solunum (br/min)";
        anaTablo[0][1] = sonSolunum + "";
        try {
            anaTablo[0][2] = getInterval(( sTarih.get(sTarih.size() - 1)).toLocalDate());
        }catch (Exception e){anaTablo[0][2] = "Veri bulunamadı!";}

        anaTablo[0][3] = maxMinTablosu[0][0];
        anaTablo[0][4] = maxMinTablosu[0][1];
        anaTablo[0][5] = maxMinTablosu[0][2];
        anaTablo[0][6] = maxMinTablosu[0][3];

        anaTablo[1][0] = "Ateş (C°)";
        anaTablo[1][1] = sonAtes + "";
        try{
        anaTablo[1][2] = getInterval(( aTarih.get(aTarih.size()-1)).toLocalDate());
        }catch (Exception e){anaTablo[1][2] = "Veri bulunamadı!";}
        anaTablo[1][3] = maxMinTablosu[1][0];
        anaTablo[1][4] = maxMinTablosu[1][1];
        anaTablo[1][5] = maxMinTablosu[1][2];
        anaTablo[1][6] = maxMinTablosu[1][3];

        anaTablo[2][0] = "Tansiyon (mmHg)";
        if(sonSTansiyon.equals("Veri Bulunamadı!"))
            anaTablo[2][1] = "Veri bulunamadı!";
        else
            anaTablo[2][1] = sonSTansiyon + "-" + sonDTansiyon;
        try{
        anaTablo[2][2] = getInterval(( stTarih.get(stTarih.size()-1)).toLocalDate());
        }catch (Exception e){anaTablo[2][2] = "Veri bulunamadı!";}

        anaTablo[2][3] = maxMinTablosu[2][0];
        anaTablo[2][4] = maxMinTablosu[2][1];
        anaTablo[2][5] = maxMinTablosu[2][2];
        anaTablo[2][6] = maxMinTablosu[2][3];

        anaTablo[3][0] = "Nabız (bpm)";
        anaTablo[3][1] = sonNabiz + "";
        try{
        anaTablo[3][2] = getInterval((nTarih.get(nTarih.size()-1)).toLocalDate());
        }catch (Exception e){anaTablo[3][2] = "Veri bulunamadı!";}
        anaTablo[3][3] = maxMinTablosu[3][0];
        anaTablo[3][4] = maxMinTablosu[3][1];
        anaTablo[3][5] = maxMinTablosu[3][2];
        anaTablo[3][6] = maxMinTablosu[3][3];

        anaTablo[4][0] = "Kan Şekeri (mg/dL)";
        anaTablo[4][1] = sonKanSekeri + "";
        try{
        anaTablo[4][2] = getInterval(( ksTarih.get(ksTarih.size()-1)).toLocalDate());
        }catch (Exception e){anaTablo[4][2] = "Veri bulunamadı!";}
        anaTablo[4][3] = maxMinTablosu[4][0];
        anaTablo[4][4] = maxMinTablosu[4][1];
        anaTablo[4][5] = maxMinTablosu[4][2];
        anaTablo[4][6] = maxMinTablosu[4][3];

        anaTablo[5][0] = "Oksijen Satürasyon Takibi (%)";
        anaTablo[5][1] = sonOksSat + "";
        try{
        anaTablo[5][2] = getInterval((osTarih.get(osTarih.size()-1)).toLocalDate());
        }catch (Exception e){anaTablo[5][2] = "Veri bulunamadı!";}
        anaTablo[5][3] = maxMinTablosu[5][0];
        anaTablo[5][4] = maxMinTablosu[5][1];
        anaTablo[5][5] = maxMinTablosu[5][2];
        anaTablo[5][6] = maxMinTablosu[5][3];

        return new JTable(anaTablo, basliklar);
    }

    public JPanel getPanel(){return hastaEkrani;}

    //bu fonksiyon ile herhangi bir tarihten günümüze
    public String getInterval(LocalDate t){

        try {
            String result = "";

            LocalDate now = LocalDate.now();
            Period diff = Period.between(t, now);
            int year = diff.getYears();
            int month = diff.getMonths();
            int day = diff.getDays();


            if (year != 0) {
                result += year + " yıl ";
            } else if (month != 0) {
                result += month + " ay ";
            } else if (day != 0) {
                result += day + " gün ";
            } else if (now.getDayOfMonth() == t.getDayOfMonth()) {
                result = "Bugün";
            } else {
                result = "Dün";
            }
            return result;
        }
        catch (Exception e)
        {
            return "Veri bulunamadı!";
        }
    }

    public String[][] maxMinDegerBulma(){
        /*
                    max10    max1     min10      min1
        solunum
        ateş
        tansiyon
        nabız
        kan şekeri
        oks sat
         */
        String[][] result = new String[6][4];
        for(int i = 0; i < 6; i++)
        {
            for (int p = 0; p < 4; p++){
                result[i][p] = "Veri bulunamadı!";
            }
        }
        LocalDate now = LocalDate.now();

        int index = sTarih.size() - 1;
        int min = 2000;
        int max = -1;
        //belirtilen tarihe kadat giden kayıtlarda min ve max değerini bulma
        //solunum için min ve max değer bulma
        ;
        //System.out.println(.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        while(index >= 0 && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getDays() <= 1
        && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = sDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[0][1] = max + "";
        result[0][3] = min + "";
        while(index >= 0 && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) sTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = sDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[0][0] = max + "";
        result[0][2] = min + "";
        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        //ateş değeri için min ve max bulma
        index = aTarih.size() - 1;
        min = 2000;
        max = -1;

        while(index >= 0 && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getDays() <= 1
                && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = aDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[1][1] = max + "";
        result[1][3] = min + "";
        while(index >= 0 && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) aTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = aDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[1][0] = max + "";
        result[1][2] = min + "";
        //////////////////////////////////////////////////////////////////////////////////////////////

        //tansiyon değeri için min ve max bulma
        index = stTarih.size() - 1;
        min = 2000;
        max = -1;
        int minDia = 2000;
        int maxDia = -1;

        while(index >= 0 && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getDays() <= 1
                && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int degerSis = stDeger.get(index);
            int degerDia = dtDeger.get(index);
            if(degerSis < min){
                min = degerSis;
                minDia = degerDia;
            }
            if(degerSis > max){
                max = degerSis;
                maxDia = degerDia;
            }
            index--;
        }
        result[2][1] = max + " - " + maxDia;

        result[2][3] = min + " - " + minDia;
        while(index >= 0 && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) stTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int degerSis = stDeger.get(index);
            int degerDia = dtDeger.get(index);
            if(degerSis < min){
                min = degerSis;
                minDia = degerDia;
            }
            if(degerSis > max){
                max = degerSis;
                maxDia = degerDia;
            }
            index--;
        }
        result[2][0] = max + " - " + maxDia;
        result[2][2] = min + " - " + minDia;

        //////////////////////////////////////////////////////////////////////////////////////////////


        //nabız değeri için min ve max bulma
        index = nTarih.size() - 1;
        min = 2000;
        max = -1;

        while(index >= 0 && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getDays() <= 1
                && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = nDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[3][1] = max + "";
        result[3][3] = min + "";
        while(index >= 0 && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) nTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = nDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[3][0] = max + "";
        result[3][2] = min + "";
        ///////////////////////////////////////////////////////////////////////////////

        //kan şekeri değeri için min ve max bulma
        index = ksTarih.size() - 1;
        min = 2000;
        max = -1;

        while(index >= 0 && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getDays() <= 1
                && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = ksDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[4][1] = max + "";
        result[4][3] = min + "";
        while(index >= 0 && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) ksTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = ksDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[4][0] = max + "";
        result[4][2] = min + "";


        //oks sat değeri için min ve max bulma
        index = osTarih.size() - 1;
        min = 2000;
        max = -1;

        while(index >= 0 && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getDays() <= 1
                && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = osDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[5][1] = max + "";
        result[5][3] = min + "";
        while(index >= 0 && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getDays() <= 10
                && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getMonths() == 0
                && Period.between(((java.sql.Date) osTarih.get(index)).toLocalDate(), now).getYears() == 0)
        {
            int deger = osDeger.get(index);
            if(deger < min){
                min = deger;
            }
            if(deger > max){
                max = deger;
            }
            index--;
        }
        result[5][0] = max + "";
        result[5][2] = min + "";
        ///////////////////////////////////////////////////////////////////////////////

        for(int i = 0; i < 6; i++)
        {
            for (int y = 0; y < 4; y++)
            {
                String a = result[i][y];
                if(a.equals("-1") || a.equals("-1 - -1") || a.equals("2000") || a.equals("2000 - 2000") || a.equals("Veri Bulunamadı!-Veri Bulunamadı!") || a.equals(" "))
                    result[i][y] = "Veri bulunamadı!";
            }
        }

        return result;
    }

    public void olcumEkle(int deger, int kategori){
    //kategori 0 ile 6 arası 0:nabız, 1:sistolik tansiyon, 2:diastolik tansiyon,
        // 3:kan şekeri, 4:ateş, 5:oksijen satürasyonu, 6:solunum
        try {
            //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
            Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
            Statement st = con.createStatement();
            int day = LocalDate.now().getDayOfMonth();
            int month = LocalDate.now().getMonthValue();
            int year = LocalDate.now().getYear();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            String time = dtf.format(LocalDateTime.now());
            String tabloAdi = "";
            switch(kategori) {
                case 0: tabloAdi = "NABIZ_DEGERLERI"; break;
                case 1: tabloAdi = "SISTOLIK_TANSIYON_DEGERLERI"; break;
                case 2: tabloAdi = "KAN_SEKERI_DEGERLERI"; break;
                case 3: tabloAdi = "ATES_DEGERLERI"; break;
                case 4: tabloAdi = "OKSIJEN_SATURASYON_DEGERLERI"; break;
                case 5: tabloAdi = "SOLUNUM_DEGERLERI"; break;
                case 6: tabloAdi = "DIASTOLIK_TANSIYON_DEGERLERI"; break;
            }

            String sqlStr = "insert into " + tabloAdi + " values(" + gosterilecekHasta.getProtokolNo() + ", " +
                    deger + ", '" + day + "/" + month + "/" + year + "', '" + time.substring(0,2) + time.substring(3,5) + time.substring(6) + "')";
            System.out.println(sqlStr);
            ResultSet rs = st.executeQuery(sqlStr);
        }
        catch(Exception ex){ex.printStackTrace();}
    }

    public JButton olcumEklemeButonuOlustur(){
        JButton ekle = new JButton("Yeni Ölçüm Kaydet");

        ekle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog ekle1= new JDialog();
                ekle1.setSize(300,120);
                ekle1.setLayout(new GridLayout(3,1));
                JLabel l = new JLabel("Eklemek istediğiniz verinin türünü giriniz.");
                ekle1.add(l, 0);

                String[] secenekler = {"Nabız", "Tansiyon", "Kan Şekeri",  "Ateş", "Oksijen Satürasyonu", "Solunum"};
                JComboBox degerTurleri = new JComboBox(secenekler);
                ekle1.add(degerTurleri, 1);
                JButton araButon = new JButton("Seç");

                araButon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        int index = degerTurleri.getSelectedIndex();
                        ekle1.setVisible(false);

                        String defaultText = "";

                        switch(index){
                            case 0: defaultText = "Nabız (bpm)"; break;
                            case 1: defaultText = "Sistolik Tansiyon (mmHg)"; break;
                            case 2: defaultText = "Kan Şekeri (mg/dL)"; break;
                            case 3: defaultText = "Ateş (°C)"; break;
                            case 4: defaultText = "Oksijen Satürasyon (%)"; break;
                            case 5: defaultText = "Solunum(br/min)"; break;
                        }

                        JDialog ekle2 = new JDialog();
                        ekle2.setSize(300,150);
                        if(index == 1)
                            ekle2.setLayout(new GridLayout(4,1));
                        else
                            ekle2.setLayout(new GridLayout(3,1));
                        degerGirmeYeri = new JTextField(defaultText);
                        degerGirmeYeri2 = null;

                        JLabel label2 = new JLabel("Ölçülen değeri giriniz.");
                        ekle2.add(label2, 0);

                        if(index == 1) {
                            degerGirmeYeri2 = new JTextField("Diastolik Tansiyon (mmHg)");
                            ekle2.add(degerGirmeYeri, 1);
                            ekle2.add(degerGirmeYeri2, 2);
                        }
                        else
                        {
                            ekle2.add(degerGirmeYeri, 1);
                        }



                        ekle2.setSize(200,200);

                        JButton ekle3 = new JButton("Ekle");
                        ekle3.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try{
                                    if(index == 1) {
                                        int deger = Integer.parseInt(degerGirmeYeri.getText());
                                        olcumEkle(deger, 1);
                                        deger = Integer.parseInt(degerGirmeYeri2.getText());
                                        olcumEkle(deger, 6);
                                    }
                                    else {
                                        int deger = Integer.parseInt(degerGirmeYeri.getText());
                                        olcumEkle(deger, index);
                                    }
                                    ekle2.setVisible(false);
                                }
                                catch (Exception ex)
                                {
                                    JOptionPane.showMessageDialog(hastaEkrani, "Bir hata oluştu. Lütfen tekrar deneyiniz.");
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
        return ekle;
    }


    public JButton hastaSilmeButonuOlustur(){
        JButton sil = new JButton("Hasta Kaydını Sil");

        sil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //veritabanı ile bağlantı sağlama ve hasta bilgilerini kaydetme
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    String url = "jdbc:oracle:thin:@localhost:1522/XEPDB1";
                    Connection con = DriverManager.getConnection(url, "sys as sysdba", "orclhst");
                    Statement st = con.createStatement();
                    String sqlStr = "delete from Hastalar where protokol_no = " + gosterilecekHasta.getProtokolNo();
                    System.out.println(sqlStr);
                    ResultSet rs = st.executeQuery(sqlStr);
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
        }});






        return sil;
    }

    public HastaGrafigi grafikOlustur(int grafikTuru, int w, int h){
        HastaGrafigi hg = null;
        switch (grafikTuru){
            case 0: hg = new HastaGrafigi(nDeger, nTarih, grafikTuru, w, h, nSaat); break;
            case 1: hg = new HastaGrafigi(stDeger, stTarih, grafikTuru, w, h, stSaat); break;
            case 2: hg = new HastaGrafigi(dtDeger, dtTarih, grafikTuru, w, h, dtSaat); break;
            case 3: hg = new HastaGrafigi(aDeger, aTarih, grafikTuru, w, h, aSaat); break;
            case 4: hg = new HastaGrafigi(sDeger, sTarih, grafikTuru, w, h, sSaat); break;
            case 5: hg = new HastaGrafigi(ksDeger, ksTarih, grafikTuru, w, h, ksSaat); break;
            case 6: hg = new HastaGrafigi(osDeger, osTarih, grafikTuru, w, h, osSaat); break;
        }
        return hg;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JFrame grafik = new JFrame("Grafik");
        JPanel grafikPanel = new JPanel();
        ((HastaGrafigi)(e.getSource())).getGraphKind();
        HastaGrafigi hg = grafikOlustur(((HastaGrafigi)(e.getSource())).getGraphKind(), 900, 500);
        grafikPanel.add(hg);
        grafik.getContentPane().add(grafikPanel);
        grafik.setSize(1000, 600);
        grafik.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
