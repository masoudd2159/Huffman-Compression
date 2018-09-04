class HuffmanNode {

    int frequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.character = character;
        this.left = left;
        this.right = right;
    }
}
