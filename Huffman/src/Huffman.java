import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class Huffman {

    private Huffman() {
        /*<Empty>*/
    }

    public static void compress(String sentence) throws IOException, FileNotFoundException {
         if (sentence == null && sentence.length() == 0) { return; }
        writeSentenceToFile(sentence);

        final Map<Character, Integer> characterFrequency = HuffmanEncode.getCharacterFrequency(sentence);
        final HuffmanNode root = HuffmanEncode.BuildTree(characterFrequency);
        final Map<Character, String> characterCode = HuffmanEncode.codeGenerator(characterFrequency.keySet(), root);
        final String encodedMessage = HuffmanEncode.encodedMessage(characterCode, sentence);

        HuffmanEncode.codeTree(root);
        HuffmanEncode.codeMessage(encodedMessage);
    }

    private static void writeSentenceToFile(String sentence) {
        try {
            Path file = Paths.get("D:/Programming/Java/IntelliJ IDEA/Huffman/file/sentence.txt");
            Files.write(file, Collections.singleton(sentence), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
