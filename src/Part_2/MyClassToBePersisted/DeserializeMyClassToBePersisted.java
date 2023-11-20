package Part_2.MyClassToBePersisted;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class DeserializeMyClassToBePersisted {
    public static void deserializeMyClassToBePersisted(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        MyClassToBePersisted mp = (MyClassToBePersisted) objectInputStream.readObject();

        System.out.println(Arrays.toString(mp.getProfiles()));
        System.out.println(Arrays.toString(mp.getGroups()));
    }
}
