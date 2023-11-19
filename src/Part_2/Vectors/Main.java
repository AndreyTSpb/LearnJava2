package Part_2.Vectors;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) throws IOException {

        Vector<Double> v1 = new Vector<Double>(List.of(new Double[]{1.2, 3.4, 5.6, 7.2, 8.0}));

        System.out.println("\nРабота с вектором в потоке");
        System.out.print("Базовый вектор: ");
        Vectors.viewVector(v1);

        //Запись вектора в байтовый поток
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Vectors.outputVector(v1, baos);
        baos.flush();
        System.out.print("Запись вектора в байтовый поток: ");
        System.out.println(baos); //raw

        ByteArrayInputStream bias = new ByteArrayInputStream(baos.toByteArray());
        System.out.print("Чтение данных из байтового потока: ");
        Vector<Double> vn = Vectors.inputVector(bias);
        Vectors.viewVector(vn);

        //Записи вектора в символьный поток
        CharArrayWriter caw = new CharArrayWriter(); //запись
        Vectors.writeVector(v1, caw);
        caw.flush();

        System.out.print("Данные записанные в поток символов: ");
        System.out.println(caw);

        //чтение данных из строкового потока
        CharArrayReader car = new CharArrayReader(caw.toCharArray()); //чтение
        Vector v3  = Vectors.readVector(car);
        System.out.print("Данные прочтенные из потока символов: ");
        System.out.print("длинна=" + v3.size()+ ", ");
        System.out.print("точки- ");
        Vectors.viewVector(v3);
    }

}