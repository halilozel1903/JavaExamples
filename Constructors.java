/**
 * Created by ozel on 17.7.2017.
 */
public class Constructors { //Constructors sınıfı oluşturuldu.
    public double kenar;

    public Constructors(double kenar) { //Constructors sınıfının yapıcı metodu
        this.kenar = kenar;
    }

    public double KareAlan() { //Karenin alanını bulan bir metod oluşturuldu.
        return kenar * kenar;
    }

    public static void main(String[] args) {

        Constructors kare1 = new Constructors(9); //kenar değeri verildi.
        System.out.println("Karenin Alanı: " + kare1.KareAlan()); //karenin alanı ekranda gösterildi.
    }
}
