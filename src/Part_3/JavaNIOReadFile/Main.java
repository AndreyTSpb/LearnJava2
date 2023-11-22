package Part_3.JavaNIOReadFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get(getUrl()); //путь до файла который надо прочести

        JavaNIOReadFile readFile = new JavaNIOReadFile(filePath); //считывание файла


    }

    public static String getUrl(){
        String useDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();

        System.out.println("Рабочая папка = " + useDirectory);
        Main o = new Main(); //для получения имени пакета и класса
        String fileName = getClassName(o)+".java"; //получаем имя файла
        System.out.println("Имя файла = " + fileName);
        String path = useDirectory+"\\src"+"\\"+getPackageName(o).replace('.', '\\')+"\\"+fileName;
        System.out.println("Полный путь до файла = " + path);
        System.out.println("---------------------------");
        return path;
    }

    /**
     * Получаем имя пакета
     * @param o
     * @return
     */
    public static String getPackageName(Object o){
        return o.getClass().getPackage().getName();
    }
    public static String getClassName(Object o){
        return o.getClass().getSimpleName();
    }
}
