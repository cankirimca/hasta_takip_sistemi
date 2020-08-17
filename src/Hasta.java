import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Hasta {

    enum Cinsiyet {
        ERKEK,
        KADIN
    }

    private String ad;
    private String soyad;
    private int yas;
    private Cinsiyet cinsiyet;
    private Date yatmaTarihi;
    private ArrayList<Integer> nabiz;
    private ArrayList<Integer> buyukTansiyon;
    private ArrayList<Integer> kucukTansiyon;
    private ArrayList<Double> ates;

    public Hasta(String ad, String soyad, int yas, Cinsiyet cinsiyet, Date yatmaTarihi)
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
    }

    public String getSoyad() {
        return soyad;
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

    public String toString(){
        return ad + ", " + soyad + ", " + yas + ",  " + cinsiyet + ", " + yatmaTarihi;
    }

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        Date date = new Date(1978,0,15);
        System.out.println(df.format(date));
        //Hasta hasta1 = new Hasta("Ahmet", "Sütçüoğlu", 44, Cinsiyet.ERKEK, new Date(2020,11,12));
        //System.out.println(hasta1.getYatmaTarihi());
    }
}

