/**
 * Created by ozel on 17.7.2017.
 */
public class Siniflar_Takim { //Class ismi isteğinize göre değiştirebilirsiniz.

    public static void main(String[] args) {
        Futbolcu futbolcu = new Futbolcu(); //Futbolcu sınıfından futbolcu nesnesi oluşturuldu.
        futbolcu.isim = "Ricardo Quaresma"; //Futbolcuya isim verildi.
        futbolcu.yas = 32; //Futbolcuya yaş değeri verildi.
        futbolcu.takim = "Beşiktaş"; //Futbolcuya takım değeri verildi.
        System.out.println("Futbolcu Adı  : " + futbolcu.isim); //Futbolcunun ismi gösterildi.
        System.out.println("Futbolcu Yaşı :" + futbolcu.yas); //Futbolcunun yaşı gösterildi.
        System.out.println("Futbolcunun Takımı :" + futbolcu.takim); //Futbolcunun takımı gösterildi.
    }
}

class Futbolcu { //Futbolcu sınıfı oluşturuldu.
    String isim;
    String takim;
    int yas;
}