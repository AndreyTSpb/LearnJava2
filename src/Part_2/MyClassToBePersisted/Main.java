package Part_2.MyClassToBePersisted;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        System.out.println("Сериализация файла!");
        SerializeMyClassToBePersisted.serializaMyclass("save_file.data");

        DeserializeMyClassToBePersisted.deserializeMyClassToBePersisted("save_file.data");
    }

}
