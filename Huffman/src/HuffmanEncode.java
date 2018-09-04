import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class HuffmanEncode {

    public static Map<Character, Integer> getCharacterFrequency(String sentence) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!map.containsKey(ch)) {
                map.put(ch, 1);
            } else {
                int temp = map.get(ch);
                map.put(ch, ++temp);
            }
        }
        return map;
    }

    public static HuffmanNode BuildTree(Map<Character, Integer> characterFrequency) {
        final Queue<HuffmanNode> nodeQueue = createNodeQueue(characterFrequency);

        while (nodeQueue.size() > 1) {
            final HuffmanNode nodeX = nodeQueue.remove();
            final HuffmanNode nodeY = nodeQueue.remove();

            HuffmanNode huffmanNode = new HuffmanNode('\0', nodeX.frequency + nodeY.frequency, nodeX, nodeY);
            nodeQueue.add(huffmanNode);
        }
        return nodeQueue.remove();
    }

    private static Queue<HuffmanNode> createNodeQueue(Map<Character, Integer> characterFrequency) {
        final Queue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(11, new HuffmanComparator());
        for (Map.Entry<Character, Integer> entry : characterFrequency.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
        }
        return queue;
    }

    public static Map<Character, String> codeGenerator(Set<Character> characters, HuffmanNode root) {
        final Map<Character, String> map = new HashMap<Character, String>();
        doGenerateCode(root, map, "");
        return map;
    }

    private static void doGenerateCode(HuffmanNode root, Map<Character, String> map, String s) {
        if (root.left == null && root.right == null) {
            map.put(root.character, s);
            return;
        }
        doGenerateCode(root.left, map, s + '0');
        doGenerateCode(root.right, map, s + '1');
    }

    public static String encodedMessage(Map<Character, String> characterCode, String sentence) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            stringBuilder.append(characterCode.get(sentence.charAt(i)));
        }
        return stringBuilder.toString();
    }

    public static void codeTree(HuffmanNode root) throws FileNotFoundException, IOException {
        final BitSet bitSet = new BitSet();
        try (ObjectOutputStream objectOutputStreamTree =
                     new ObjectOutputStream(new FileOutputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/tree"))) {
            try (ObjectOutputStream objectOutputStreamCharacter =
                         new ObjectOutputStream(new FileOutputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/character"))) {
                IntObject object = new IntObject();
                preOrder(root, objectOutputStreamCharacter, bitSet, object);
                bitSet.set(object.bitPosition, true);
                objectOutputStreamTree.writeObject(bitSet);
            }
        }
    }

    private static void preOrder(HuffmanNode root, ObjectOutputStream objectOutputStreamCharacter, BitSet bitSet, IntObject object) throws IOException {
        if (root.left == null && root.right == null) {
            bitSet.set(object.bitPosition++, false);
            objectOutputStreamCharacter.writeChar(root.character);
            return;
        }
        bitSet.set(object.bitPosition++, true);
        preOrder(root.left, objectOutputStreamCharacter, bitSet, object);

        bitSet.set(object.bitPosition++, true);
        preOrder(root.right, objectOutputStreamCharacter, bitSet, object);
    }

    public static void codeMessage(String message) {
        BitSet bitSet = getBitSet(message);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/encodedMessage"));
            objectOutputStream.writeObject(bitSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BitSet getBitSet(String message) {
        final BitSet bitSet = new BitSet();
        int i = 0;
        for (i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '0') {
                bitSet.set(i, false);
            } else {
                bitSet.set(i, true);
            }
        }
        bitSet.set(i, true);
        return bitSet;
    }
}
