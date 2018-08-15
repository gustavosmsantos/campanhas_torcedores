import java.util.ArrayList;
import java.util.List;

public class StreamElement {

    public static char getElement(Stream charactersStream) {
        String allElements = "";

        List<String> candidates = new ArrayList<>();
        String candidate = "";
        while (charactersStream.hasNext()) {
            char next = charactersStream.getNext();
            String character = Character.toString(next);
            if (isVowel(next)) {
                candidate = (candidate.isEmpty() || candidate.length() == 2) ? candidate.concat(character) : character;
            } else {
                candidate = (candidate.length() == 1) ? candidate.concat(character) : "";
            }

            if (candidate.length() == 3) {
                candidates.add(candidate);
                candidate = isVowel(next) ? character : "";
            }

            allElements = allElements.concat(character);
        }

        for (String element : candidates) {
            char c = element.charAt(2);
            int firstIndex = allElements.indexOf(c);
            char changedCase = Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
            if (firstIndex == allElements.lastIndexOf(c) && allElements.indexOf(changedCase) < 0) {
                return c;
            }
        }

        return '0';
    }

    private static boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) >= 0;
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
