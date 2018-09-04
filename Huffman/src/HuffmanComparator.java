import java.util.Comparator;

class HuffmanComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode nodeX, HuffmanNode nodeY) {
        return nodeX.frequency - nodeY.frequency;
    }
}
