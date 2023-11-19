package Part_4.WriteAndReadThreads;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class Main {
    public static Vector<Double> v1 = new Vector<Double>();
    public static Vector<Double> v2 = new Vector<Double>();
    public static Vector<Double> v3 = new Vector<Double>();

    public static void main(String[] args) throws InterruptedException {

        fillVector(v1, 5); //заполняем вектор нулями значениями
        fillVector(v2, 4); //заполняем вектор нулями значениями
        fillVector(v3, 3); //заполняем вектор нулями значениями

        first(); //первое
        second(); //второе
    }

    /**
     * Два класса нитей (наследуют от класса Thread),
     * взаимодействующих с помощью промежуточного объекта типа Vector.
     * @throws InterruptedException
     */
    public static void first() throws InterruptedException {

        System.out.println("Вектор v1");
        viewVector(v1);
        WriteToTread wt1 = new WriteToTread(v1);
        ReadingFromThread rt1 = new ReadingFromThread(v1);
        wt1.setPriority(1);
        wt1.start();
        wt1.join();
        rt1.setPriority(8);
        rt1.start();
        rt1.join();
        viewVector(v1);
        System.out.println(v1.size());


        System.out.println("Вектор v2");
        viewVector(v2);
        WriteToTread wt2 = new WriteToTread(v2);
        ReadingFromThread rt2 = new ReadingFromThread(v2);
        wt2.setPriority(7);
        wt2.start();
        wt2.join();
        rt2.setPriority(6);
        rt2.start();
        rt2.join();
        viewVector(v2);

        System.out.println("Вектор v3");
        viewVector(v3);
        WriteToTread wt3 = new WriteToTread(v3);
        ReadingFromThread rt3 = new ReadingFromThread(v3);
        wt3.setPriority(5);
        wt3.start();
        wt2.join();
        rt3.setPriority(4);
        rt3.start();
        rt3.join();
        viewVector(v3);
    }

    public static class WriteToTread extends Thread{
        private final Vector<Double> vector;

        public WriteToTread(Vector<Double> v){
            this.vector = v;
        }
        @Override
        public void run(){
            for(int i = 0; i < vector.size(); i++){
                Random rand = new Random();
                double k = rand.nextInt(9)+rand.nextDouble();
                vector.set(i,k); //заполняем случайными числами
                System.out.println("Write: " + String.format("%.2f",k) +" to position " + i);
            }
        }
    }

    public static class ReadingFromThread extends Thread{
        private final Vector vector;

        public ReadingFromThread(Vector vector){
            this.vector = vector;
        }
        @Override
        public void run(){
            for(int i = 0; i < vector.size(); i++){
                System.out.println("Read: " + String.format("%.2f",vector.elementAt(i)) +" from position " + i);
            }
        }
    }

    public static void viewVector(Vector v){
        System.out.print("[");
        for (int i = 0; i < v.size(); i++){
            System.out.print(" "+String.format("%.2f", v.elementAt(i)));
        }
        System.out.println(" ]");
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

    public static class Vector1{
        private double[] points;
        public int size;

        public Vector1(int size){
            this.size = size;
            this.points = new double[size];
        }

        public void setElement(int key, double value){
            this.points[key]=value;
        }

        public double getElement(int key){
            return this.points[key];
        }

        public void viewPoints(){
            System.out.print("[");
            for (int i = 0; i < this.size; i++){
                System.out.print(" "+String.format("%.2f",points[i]));
            }
            System.out.println(" ]");
        }
    }

    /**
     * новых модифицированных класса нитей (реализуют интерфейс Runnable),
     * обеспечивающих последовательность операций чтения-записи
     */
    public static void second() throws InterruptedException {

        Helper h1 = new Helper(v1);
        Helper h2 = new Helper(v2);
        Helper h3 = new Helper(v3);

        System.out.println("Вектор v1");
        //v1.vector.viewPoints();

        Thread t1 = new Thread(new WriteToTreadV2(h1));
        Thread t2 = new Thread(new ReadingFromThreadV2(h1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Вектор v2");
        //v2.vector.viewPoints();
        Thread t21 = new Thread(new WriteToTreadV2(h2));
        Thread t22 = new Thread(new ReadingFromThreadV2(h2));
        t21.start();
        t22.start();
        t21.join();
        t22.join();

        System.out.println("Вектор v3");
        //v3.vector.viewPoints();
        Thread t31 = new Thread(new WriteToTreadV2(h3));
        Thread t32 = new Thread(new ReadingFromThreadV2(h3));
        t31.start();
        t32.start();
        t31.join();
        t32.join();
    }
    /**
     * вспомогательный класс
     */
    public static class Helper{
        private final Vector<Double> vector;
        public boolean flag = true; //флаг разрешающий запись

        public Helper(Vector<Double> vector) {
            this.vector = vector;
        }
    }

    public static class WriteToTreadV2 implements Runnable{
        private final Helper helper; //посредник

        public WriteToTreadV2(Helper helper) {
            this.helper = helper;
        }

        @Override
        public void run(){
            for(int i = 0; i < helper.vector.size(); i++){
                Random rand = new Random(); //генерируем случайное число
                double k = rand.nextInt(9)+rand.nextDouble(); //nn.nnnnnn
                //управления доступом к общим ресурсам
                synchronized (helper){
                    while (!helper.flag){ //закрыто на запись
                        try {
                            helper.wait(); //блокируем объект
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    helper.vector.setElementAt(k, i); //заполняем случайными числами
                    System.out.println("Write: " + String.format("%.2f",k) +" to position " + i);
                    helper.flag = false; //запрещаем запись
                    helper.notify(); //разблокировать объект
                }
            }
        }
    }

    public static class ReadingFromThreadV2 implements Runnable{
        private final Helper helper; //посредник

        public ReadingFromThreadV2(Helper helper) {
            this.helper = helper;
        }

        @Override
        public void run(){
            for(int i = 0; i < helper.vector.size(); i++){
                synchronized (helper) {
                    while (helper.flag) { //открыто на запись
                        try {
                            helper.wait(); //блокируем объект
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("Read: " + String.format("%.2f", helper.vector.elementAt(i)) + " from position " + i);
                    helper.flag = true; //разрешаем на запись
                    helper.notify(); //разблокировать объект
                }
            }
        }
    }

    /**
     * Добавьте в класс со статическими методами обработки векторов
     * реализацию метода Vector synchronizedVector(Vector vector),
     */
    

}
