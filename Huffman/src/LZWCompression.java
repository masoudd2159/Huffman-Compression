import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Scanner;

public class LZWCompression {

    HashMap<String, Integer> dictionary = new HashMap<>();
    int dictionarySize = 256;
    String string = "";
    byte inputByte;
    byte[] buffer = new byte[3];
    boolean onLeft = true;

    public void compress(String uncompressed) throws IOException {
        for (int i = 0; i < 256; i++) {
            dictionary.put(Character.toString((char) i), i);
        }

        RandomAccessFile randomAccessFileRead = new RandomAccessFile(uncompressed, "r");
        RandomAccessFile randomAccessFileOut = new RandomAccessFile(uncompressed.concat(".lzw"), "rw");

        try {
            inputByte = randomAccessFileRead.readByte();
            int i = new Byte(inputByte).intValue();
            if (i < 0) {
                i += 256;
            }
            char ch = (char) i;
            string = "" + ch;

            while (true) {
                inputByte = randomAccessFileRead.readByte();
                i = new Byte(inputByte).intValue();

                if (i < 0) {
                    i += 256;
                }
                System.out.print(i + " , ");
                ch = (char) i;

                if (dictionary.containsKey(string + ch)) {
                    string += ch;
                } else {
                    String s12 = to12bit(dictionary.get(string));

                    if (onLeft) {
                        buffer[0] = (byte) Integer.parseInt(s12.substring(0, 8), 2);
                        buffer[1] = (byte) Integer.parseInt(s12.substring(8, 12) + "0000", 2);
                    } else {
                        buffer[1] += (byte) Integer.parseInt(s12.substring(0, 4), 2);
                        buffer[2] = (byte) Integer.parseInt(s12.substring(4, 12), 2);

                        for (int j = 0; j < buffer.length; j++) {
                            randomAccessFileOut.writeByte(buffer[j]);
                            buffer[j] = 0;
                        }
                    }
                    onLeft = !onLeft;

                    if (dictionarySize < 4096) {
                        dictionary.put(string + ch, dictionarySize++);
                    }
                    string = "" + ch;
                }
            }
        } catch (Exception e) {
            String str12bit = to12bit(dictionary.get(string));
            if (onLeft) {
                buffer[0] = (byte) Integer.parseInt(str12bit.substring(0, 8), 2);
                buffer[1] = (byte) Integer.parseInt(str12bit.substring(8, 12) + "0000", 2);
                randomAccessFileOut.writeByte(buffer[0]);
                randomAccessFileOut.writeByte(buffer[1]);
            } else {
                buffer[1] += (byte) Integer.parseInt(str12bit.substring(0, 4), 2);
                buffer[2] = (byte) Integer.parseInt(str12bit.substring(4, 12), 2);

                for (int j = 0; j < buffer.length; j++) {
                    randomAccessFileOut.writeByte(buffer[j]);
                    buffer[j] = 0;
                }
            }
            randomAccessFileRead.close();
            randomAccessFileOut.close();
        }
    }

    private String to12bit(int i) {
        String string = Integer.toBinaryString(i);
        while (string.length() < 12) {
            string += "0";
        }
        return string;
    }

    public static void main(String[] args) throws IOException {
        try {
            LZWCompression lzw = new LZWCompression();

            Scanner input = new Scanner(System.in);

            System.out.println("Enter the name of your (input.txt) file.");

            String str = input.nextLine();

            File file = new File(str);

            Scanner fileScanner = new Scanner(file);

            String line = "";

            while (fileScanner.hasNext()) {
                line = fileScanner.nextLine();
                System.out.println("Contents of your file being compressed: \n"
                        + line);
            }
            lzw.compress(str);
            System.out.println("\nCompression of your file is complete!");
            System.out.println("Your new file is named: " + str.concat(".lzw"));
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        }
    }
}

//C://Users//Masoud//Desktop//test.txt
