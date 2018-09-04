import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomFile {

    private static String generateRandomWords(int count) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(count);
        for (int i = 0; i < count * 100; i++) {
            char rand = (char) ('a' + random.nextInt(26));
            stringBuilder.append(rand);
        }
        return stringBuilder.toString();
    }

    public void writeRandomWordsToFile(int count) {
        try {
            Path file = Paths.get("D:/Programming/Java/IntelliJ IDEA/Huffman/file/files/" + count + ".txt");
            Files.write(file, Collections.singleton(generateRandomWords(count)), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
