package Part_3.JavaNIOReadFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JavaNIOReadFile {

    /**
     * Читаем файл построчно
     * @param testFilePath - путь к файлу
     * @throws IOException
     */
    public JavaNIOReadFile(Path testFilePath) throws IOException {
        List<String> lines = Files.readAllLines(testFilePath, UTF_8);
        for (String s: lines) {
            System.out.println(s);
        }
    }
}
