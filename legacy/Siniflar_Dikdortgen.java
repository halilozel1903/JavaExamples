/**
 * Created by ozel on 17.7.2017.
 */
public class Siniflar_Dikdortgen { //Dikdörtgen sınıfı oluşturuldu.

    public double en;
    public double boy;

    public double DikdortgenAlani() { //Dikdörtegnin alanını bulan bir metod oluşturuldu.
        return en * boy;
    }

    public static void main(String[] args) {

        Siniflar_Dikdortgen dikdortgen1 = new Siniflar_Dikdortgen(); //dikdortgen1 nesnesi oluşturuldu.
        Siniflar_Dikdortgen dikdortgen2 = new Siniflar_Dikdortgen(); //dikdortgen2 nesnesi oluşturuldu.

        dikdortgen1.en = 6; //dikdortgen1 nesnesine en değeri atandı.
        dikdortgen1.boy = 9; //dikdortgen1 nesnesine boy değeri atandı.

        dikdortgen2.en = 5; //dikdortgen2 nesnesine en değeri atandı.
        dikdortgen2.boy = 10; //dikdortgen2 nesnesine boy değeri atandı.

        System.out.println("Dikdörtgen1'in alanı : " + dikdortgen1.DikdortgenAlani()); //dikdörtgen1 alanı
        System.out.println("Dikdörtgen2'in alanı : " + dikdortgen2.DikdortgenAlani()); //dikdörtgen2 alanı
    }
}
