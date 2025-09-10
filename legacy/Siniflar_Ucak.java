/**
 * Created by ozel on 17.7.2017.
 */



 /*
 new operatörüyle nesne oluşturulur ve nokta
 operatörü ile nesneye ait özellikler çağrılır.

  */
public class Siniflar_Ucak {
    public static void main(String[] args) {

        Ucuslar ucuslar1 = new Ucuslar(); //Ucuslar sınıfının ucuslar1 nesnesi oluşturuldu.
        ucuslar1.yolcuEkle(); //ucuslar1 yolcuEkle metodu ile yolcu eklendi.
        System.out.println("Uçuşlar1 yolcu sayısı: " + ucuslar1.yolcuSayisi); //yolcu sayısı gösterildi.

        Ucuslar ucuslar2 = new Ucuslar(); //Ucuslar sınıfının ucuslar2 nesnesi oluşturuldu.
        ucuslar2.yolcuEkle(); //ucuslar2 yolcuEkle metodu eklendi.
        System.out.println("Uçuşlar2 yolcu sayısı: " + ucuslar2.yolcuSayisi); //yolcu sayısı gösterildi.

        ucuslar1.yolcuEkle(); //ucuslar1 yolcuEkle metodu ile yolcu eklendi.
        ucuslar1.yolcuEkle(); //ucuslar1 yolcuEkle metodu ile yolcu eklendi.
        ucuslar1.yolcuEkle(); //ucuslar1 yolcuEkle metodu ile yolcu eklendi.
        System.out.println("Uçuşlar1 yolcu sayısı: " + ucuslar1.yolcuSayisi);

        System.out.println("Koltuk sayısı: " + ucuslar1.koltukSayisi); //koltuk sayısı gösterildi.

    }
}

class Ucuslar {
    public int yolcuSayisi;
    public int koltukSayisi;

    public Ucuslar() { //Yapıcı sınıf oluşturuldu.
        yolcuSayisi = 0; //yolcu sayısı değişken olduğu için 0 atandı.
        koltukSayisi = 150; //koltuk sayısı sabit olduğu için 150 değeri atandı.
    }

    public void yolcuEkle() { //yolcuEkle metodu oluşturuldu.

        if (yolcuSayisi < koltukSayisi) //yolcuSayısı koltukSayısından büyük olduğu sürece yolcuSayısı arttırılıyor.
            yolcuSayisi++;
    }
}