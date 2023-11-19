package Part_2.Vectors;

import java.io.*;
import java.util.Vector;

import static java.io.StreamTokenizer.TT_EOF;

public class Vectors {

    /**
     * умножение вектора на скаляр;
     * @param v - вектор в виде объекта
     * @param k - скаляр
     * @return - возвращает новый вектор
     */
    public static Vector<Double> multiplicationScalar(Vector<Double> v, double k){
        Vector<Double> newVector = new Vector<Double>();
        for (int i = 0; i < v.size(); i++) {
            newVector.set(i, v.get(i) * k);
        }
        return newVector;
    }

    /**
     * Вектор заполнить нулями
     * @param v - пустой вектор
     * @param size - размерность вектора
     */
    public static void fillVector(Vector<Double> v, int size){
        for (int i = 0; i < size; i++){
            v.add(0.0);
        }
    }

    public static void viewVector(Vector<Double> v){
        System.out.print("[");
        for (int i = 0; i < v.size(); i++){
            System.out.print(" "+String.format("%.2f", v.elementAt(i)));
        }
        System.out.println(" ]");
    }

    /**
     * сложение двух векторов;
     * @param v1 - первый вектор
     * @param v2 - второй вектор
     * @return - возвращает новый вектор
     */
    public static Vector<Double> sum(Vector<Double> v1, Vector<Double> v2){
        Vector<Double> newVector = new Vector<Double>();
        int size = v1.size();
        for (int i = 0; i < size; i++) {
            newVector.set(i, v1.get(i) + v2.get(i));
        }
        return newVector;
    }

    /**
     * нахождение скалярного произведения двух векторов
     * @param v1 - первый вектор
     * @param v2 - второй вектор
     * @return - возвращает новый вектор
     */
    public static Vector<Double> scalarProduct(Vector<Double> v1, Vector<Double> v2){
        Vector<Double> newVector = new Vector<Double>();
        for (int i = 0; i < v1.size(); i++) {
            newVector.set(i,v1.get(i) * v2.get(i));
        }
        return newVector;
    }

    /**
     * Записи вектора в байтовый поток void outputVector(Vector v, OutputStream out)
     * @param v - Вектор
     * @param out - поток вывода
     * @throws IOException
     */
    public static void outputVector(Vector<Double> v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(v.size()); //размерность вектора (целое число)
        for (int i = 0; i < v.size(); i++){
            dos.writeDouble(v.get(i)); //точка вектора
        }
        dos.flush();
    }

    /**
     * Чтения вектора из байтового потока
     * 1) Размер вектора хранится в целочисленном ввиде
     * 2) точки состоят из 8-байтов типа double
     * @param in - входящий поток
     * @return - объект типа Vector
     * @throws IOException
     */
    public static Vector<Double> inputVector(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        int size = dis.readInt(); //т.к. длину отправили как целое число
        System.out.println(size);
        Vector<Double> newVector = new Vector<Double>();
        for (int i = 0; i < size; i++){
            newVector.add(dis.readDouble()); //считывает из потока 8-байтовое значение double
        }

        return newVector;
    };

    /**
     * Записи вектора в символьный поток
     * числа разделены пробелами
     * @param v - объект вектор
     * @param out - исходящий поток
     * @throws IOException
     */
    public static void writeVector(Vector<Double> v, Writer out) throws IOException {
        BufferedWriter bw = new BufferedWriter(out);
        int size = v.size();
        bw.write(String.valueOf(size));
        for (Double aDouble : v) {
            bw.write(" " + aDouble); //добавляем точку сразделителем " "
        }
        bw.flush();
    }

    /**
     * чтения вектора из символьного потока
     * @param in - входяший строковый поток
     * @return
     * @throws IOException
     */
    public static Vector<Double> readVector(Reader in) throws IOException {
        StreamTokenizer st = new StreamTokenizer(in);
        st.nextToken();
        //double[] points = new double[(int)st.nval];
        Vector<Double> newVector = new Vector<Double>();
        int i = 0;
        //Если метод nextToken вернул значение TT_EOF, следует завершить цикл разбора входного потока.
        while (st.nextToken() != TT_EOF){
            newVector.add(st.nval);
        }
        return newVector;
    }

}
