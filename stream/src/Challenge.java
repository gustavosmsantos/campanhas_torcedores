import java.util.HashMap;
import java.util.Map;

public class Challenge {

    private static String vowels = "aeiouAEIOU";

    public static void main(String[] args) {

        String string = "aAbBABacafe";
        Map<Character, Integer> countMap = new HashMap<>();
        CharSequenceStream stream = new CharSequenceStream(string);

        while(stream.hasNext()) {
            char next = stream.getNext();

            System.out.println(next);


        }
    }

    private boolean isVowel(char c) {
        return vowels.indexOf(c) >= 0;
    }

    static class CharSequenceStream implements Stream {

        private char[] sequence;

        private int counter;

        public CharSequenceStream(String string) {
            this.sequence = string.toCharArray();
        }

        @Override
        public char getNext() {
            char next = sequence[counter];
            counter++;
            return next;

        }

        @Override
        public boolean hasNext() {
            return counter < sequence.length;
        }

    }

    interface Stream {

        char getNext();

        boolean hasNext();

    }

}
