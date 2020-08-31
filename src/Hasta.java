import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Hasta {

    enum Cinsiyet {
        ERKEK,
        KADIN
    }

    private String ad;
    private String soyad;
    private String doktor;
    private String servis;
    private int yas;
    private int protokolNo;
    private int tcNo;
    private Cinsiyet cinsiyet;
    private Date yatmaTarihi;
    private ArrayList<Integer> nabiz;
    private ArrayList<Integer> buyukTansiyon;
    private ArrayList<Integer> kucukTansiyon;
    private ArrayList<Double> ates;
    private boolean gunubirlik;

    public Hasta(String ad, String soyad, int yas, Cinsiyet cinsiyet, Date yatmaTarihi, int protokolNo, String doktor, String servis, int tcNo, boolean gunubirlik)
    {
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
        this.cinsiyet = cinsiyet;
        this.yatmaTarihi = yatmaTarihi;
        this.ates = new ArrayList<Double>();
        this.nabiz = new ArrayList<Integer>();
        this.buyukTansiyon = new ArrayList<Integer>();
        this.kucukTansiyon = new ArrayList<Integer>();
        this.doktor = doktor;
        this.servis = servis;
        this.protokolNo = protokolNo;
        this.tcNo = tcNo;
        this.gunubirlik = gunubirlik;
    }

    public String getSoyad() {
        return soyad;
    }

    public int getProtokolNo() {
        return protokolNo;
    }

    public String getDoktorId() {
        return doktor;
    }

    public String getServis() {
        return servis;
    }

    public String getDoktor() {
        return doktor;
    }

    public String getAd() {
        return ad;
    }

    public int getYas() {
        return yas;
    }

    public ArrayList<Double> getAtes() {
        return ates;
    }

    public ArrayList<Integer> getBuyukTansiyon() {
        return buyukTansiyon;
    }

    public ArrayList<Integer> getKucukTansiyon() {
        return kucukTansiyon;
    }

    public ArrayList<Integer> getNabiz() {
        return nabiz;
    }

    public Cinsiyet getCinsiyet() {
        return cinsiyet;
    }

    public Date getYatmaTarihi() {
        return yatmaTarihi;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setAtes(ArrayList<Double> ates) {
        this.ates = ates;
    }

    public void setBuyukTansiyon(ArrayList<Integer> buyukTansiyon) {
        this.buyukTansiyon = buyukTansiyon;
    }

    public void setCinsiyet(Cinsiyet cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public void setKucukTansiyon(ArrayList<Integer> kucukTansiyon) {
        this.kucukTansiyon = kucukTansiyon;
    }

    public void setNabiz(ArrayList<Integer> nabiz) {
        this.nabiz = nabiz;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public void setYas(int yas) {
        this.yas = yas;
    }

    public void setYatmaTarihi(Date yatmaTarihi) {
        this.yatmaTarihi = yatmaTarihi;
    }

    public void setDoktor(String doktor) {
        this.doktor = doktor;
    }

    public void setProtokolNo(int protokolNo) {
        this.protokolNo = protokolNo;
    }

    public void setServis(String servis) {
        this.servis = servis;
    }

    public int getTcNo() {
        return tcNo;
    }

    public void setTcNo(int tcNo) {
        this.tcNo = tcNo;
    }

    public String toString(){
        return ad + ", " + soyad + ", " + yas + ",  " + cinsiyet + ", " + yatmaTarihi;
    }

    public static void main(String[] args) {
        //SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        //Date date = new Date(1978,0,15);
        //System.out.println(df.format(date));
        //Hasta hasta1 = new Hasta("Ahmet", "Sütçüoğlu", 44, Cinsiyet.ERKEK, new Date(2020,11,12));
        //System.out.println(hasta1.getYatmaTarihi());
        //LocalDate d = LocalDate.now();
        Date e = new Date();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println(dtf.format(LocalDateTime.now()));
        //System.out.println(e);

    }
}



