import java.io.*;
import java.util.BitSet;

public class HuffmanDecode {
    public static String deCode() throws FileNotFoundException, ClassNotFoundException, IOException {
        HuffmanNode node = deCodeTree();
        return decodeMessage(node);
    }

    private static HuffmanNode deCodeTree() throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStreamBranch =
                     new ObjectInputStream(new FileInputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/tree"))) {
            try (ObjectInputStream objectInputStreamCharacter =
                         new ObjectInputStream(new FileInputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/character"))) {
                BitSet bitSet = (BitSet) objectInputStreamBranch.readObject();
                return preOrder(bitSet, objectInputStreamCharacter, new IntObject());
            }
        }
    }

    private static HuffmanNode preOrder(BitSet bitSet, ObjectInputStream objectInputStreamCharacter, IntObject object) throws IOException {
        HuffmanNode node = new HuffmanNode('\0', 0, null, null);

        if (!bitSet.get(object.bitPosition)) {
            object.bitPosition++;
            node.character = objectInputStreamCharacter.readChar();
            return node;
        }

        object.bitPosition = object.bitPosition + 1;
        node.left = preOrder(bitSet, objectInputStreamCharacter, object);

        object.bitPosition = object.bitPosition + 1;
        node.right = preOrder(bitSet, objectInputStreamCharacter, object);

        return node;
    }

    private static String decodeMessage(HuffmanNode node) throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(new FileInputStream("D:/Programming/Java/IntelliJ IDEA/Huffman/file/encodedMessage"))) {
            final BitSet bitSet = (BitSet) objectInputStream.readObject();
            final StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (bitSet.length() - 1); ) {
                HuffmanNode temp = node;
                while (temp.left != null) {
                    if (!bitSet.get(i)) {
                        temp = temp.left;
                    } else {
                        temp = temp.right;
                    }
                    i = i + 1;
                }
                stringBuilder.append(temp.character);
            }
            return stringBuilder.toString();
        }
    }
}
