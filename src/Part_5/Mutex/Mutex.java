package Part_5.Mutex;

public class Mutex implements Runnable{
        private static final Object mutex = new Object();
        private static int counter = 0;
        @Override
        public void run() {
            synchronized (mutex) {
                for (int i = 0; i < 100; i++) {
                    counter++;
                }
            }
        }
    //Источник: https://mealblog.ru/cto-takoe-myuteks-v-java
}
