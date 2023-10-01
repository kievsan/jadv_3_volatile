package ru.mail.kievsan;

public class WordChecker {

    public boolean isPalindrome(String text) {
        final char[] charArray = text.toCharArray();
        final int middleIdx = charArray.length / 2;
        for (int i = 0; i < middleIdx; i++) {
            if (charArray[i] != charArray[charArray.length - i - 1]) return false;
        }
        return true;
    }

    public boolean areIdenticalLetters(String text) {
        final char[] charArray = text.toCharArray();
        final char testChar = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            if (testChar != charArray[i]) return false;
        }
        return true;
    }

    public boolean areLettersAscending(String text) {
        final char[] charArray = text.toCharArray();
        char lastChar = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            if (lastChar > charArray[i]) return false;
            lastChar = charArray[i];
        }
        return true;
    }
}
