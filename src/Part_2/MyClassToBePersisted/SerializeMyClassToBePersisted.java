package Part_2.MyClassToBePersisted;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeMyClassToBePersisted {
    public static void serializaMyclass(String filePath) throws IOException {
        String[] profiles = new String[]{"Профиль 1", "Профиль 2", "Профиль 3", "Профиль 4"};
        String[] groups = new String[]{"ПИбд-2005б", "ПИбд-2005а", "ПИбд-2106б"};
        MyClassToBePersisted mp = new MyClassToBePersisted(profiles, groups);

        //создаем 2 потока для сериализации объекта и сохранения его в файл
        FileOutputStream outputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        // сохраняем игру в файл
        objectOutputStream.writeObject(mp);

        //закрываем поток и освобождаем ресурсы
        objectOutputStream.close();
    }
}
