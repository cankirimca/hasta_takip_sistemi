import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main extends JFrame{

    static JButton hastaEkle, hastaSil, yardim;
    static JPanel anaEkran, hastaSilmeEkrani;
    static JFrame frame;
    static HastaTablosu ht;
    static JRadioButton kadin, erkek;


    static public void hastaEkle() {
        JDialog hastaEklemeEkrani = new JDialog(frame, "Hasta Ekle", true);
        hastaEklemeEkrani.setSize(400, 250);
        hastaEklemeEkrani.add(new JLabel("Lütfen eklemek istediğiniz hastanın bilgilerini giriniz\n"));
        hastaEklemeEkrani.setLayout(new GridLayout(9,2));
        JTextField ad = new JTextField();
        JTextField soyad = new JTextField();
        JTextField dogumTarihi = new JTextField();
        JTextField yatısTarihi = new JTextField();
        kadin = new JRadioButton("Kadın:");
        erkek = new JRadioButton("Erkek:");
        hastaEklemeEkrani.add(new JLabel("Ad: "));
        hastaEklemeEkrani.add(ad);
        hastaEklemeEkrani.add(new JLabel("Soyad: "));
        hastaEklemeEkrani.add(soyad);
        //hastaEklemeEkrani.add()
        hastaEklemeEkrani.add(new JLabel("Yatış Tarihi (gg aa yyyy): "));
        hastaEklemeEkrani.add(dogumTarihi);
        hastaEklemeEkrani.add(new JLabel("Doğum Tarihi (gg aa yyyy): "));
        hastaEklemeEkrani.add(yatısTarihi);
        hastaEklemeEkrani.setVisible(true);

        JButton eklemeDugmesi = new JButton("Ekle");
        eklemeDugmesi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String isim = ad.getText();
                    String soyisim = soyad.getText();
                    String dt = dogumTarihi.getText();
                    String yt = yatısTarihi.getText();

                    //ilk tarihi formatlama
                    Scanner scan = new Scanner(dt);
                    int day = scan.nextInt();
                    int month = scan.nextInt();
                    int year = scan.nextInt();
                    LocalDate ld1 = LocalDate.of(year, month, day);

                    //ikinci tarihi formatlama
                    scan = new Scanner(yt);
                    day = scan.nextInt();
                    month = scan.nextInt();
                    year = scan.nextInt();
                    LocalDate ld2 = LocalDate.of(year, month, day);




                    ht.hastaEkle(new Hasta(isim, soyisim, ld1, ld2));
                }
            }
        } );

    }

    public static void main(String[] args) {

        System.out.println("bu bir programdır");
        ht = new HastaTablosu();
        frame = new JFrame("Hasta Takip");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,400);

        JPanel buttonPanel = new JPanel();
        hastaEkle = new JButton("Hasta Ekle");
        hastaEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hastaEkle();
            }
        } );
        //hastaSil = new JButton("Hasta Sil");
        yardim = new JButton("Yardım");
        buttonPanel.add(hastaEkle, BorderLayout.LINE_START);
        //buttonPanel.add(hastaSil, BorderLayout.CENTER);
        buttonPanel.add(yardim, BorderLayout.LINE_END);

        //ana ekrandaki elementleri ana ekrana ekliyoruz
        anaEkran = new JPanel();
        anaEkran.setLayout(new BorderLayout());
        anaEkran.add(new JScrollPane(ht.tabloOlustur()), BorderLayout.CENTER);
        anaEkran.add(buttonPanel, BorderLayout.PAGE_START);
        frame.getContentPane().add(anaEkran);



        frame.setVisible(true);
    }
}
