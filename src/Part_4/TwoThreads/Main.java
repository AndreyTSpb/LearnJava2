package Part_4.TwoThreads;

import java.util.Vector;

public class Main {
    public static void main(String[] args) {
            System.out.println("Two Threads!");
            Thread firstThread = new ExtendThread();
            firstThread.start(); //запуск потока через расширеный класс

            Thread thread = new Thread(new RunnableThread()); //запуск потока через runnable
            thread.start();

        Vector vector = new Vector(5);
        System.out.println(vector.size());
    }

    /**
     * Класс расширяет класс Thread
     */
    public static class ExtendThread extends Thread{
        @Override
        public void run(){
            System.out.println("This class extend Thread");
        }
    }
    /**
     * Класс реализует интерфейс Runnable
     */
    public static class RunnableThread implements Runnable{
        @Override
        public void run() {
            System.out.println("This class uses runnable");
        }
    }

    /**
     * Альтернативная запись Runnable
     */
    public static class RunnableThreadV2{
        public RunnableThreadV2(){
            Runnable foo = new Runnable() {
                @Override
                public void run() {
                    System.out.println("This class uses runnable");
                }
            };
            Thread thread = new Thread(foo);
            thread.start();
        }
    }
}
