/**
 * Created by ozel on 17.7.2017.
 */

/*
New operatörü sınıf adı ile birlikte kullanılarak bellekte sınıfa ait yeni
bir nesne oluşturmamızı sağlar.
 */

public class Siniflar_Telefon {

    public static void main(String[] args) {

        Telefon telefon = new Telefon(); //Telefon sınıfından telefon nesnesi oluşturduk.
        telefon.model = "Samsung S3"; //telefon nesnesine modeli atandı.
        System.out.println(telefon.model); //atanan model ekranda gösterildi.

        Telefon telefon1; //Yukarıdaki yapılan işlemlerin aynısı sadece burada nesne tanımı tek satırda
        telefon1 = new Telefon(); //değilde iki satırda yapılmıştır. telefon1 nesnesi oluşturulmuştur.
        telefon1.model = "Iphone 7"; //telefon1 nesnesine modeli atandı.
        System.out.println(telefon1.model); //telefon1 nesnesine atanan model ekranda gösterildi.
    }
}

class Telefon { //Telefon isminde sınıf oluşturduk.
    public String model; //herkese açık şekilde kullanılması için model değerini public tanımladık.
}