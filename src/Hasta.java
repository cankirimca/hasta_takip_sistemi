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
    private ArrayList<Integer> ates;

    public Hasta(String ad, String soyad, int yas, Cinsiyet cinsiyet, Date yatmaTarihi)
    {
        this.ad = ad;
        this.soyad = soyad;
        this.yas = yas;
        this.cinsiyet = cinsiyet;
        this.yatmaTarihi = yatmaTarihi;
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

    public ArrayList<Integer> getAtes() {
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

    public void setAtes(ArrayList<Integer> ates) {
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

    public static void main(String[] args) {

    }
}

