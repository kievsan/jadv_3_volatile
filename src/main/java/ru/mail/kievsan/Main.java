package ru.mail.kievsan;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    public static final Random random = new Random();
    public static WordChecker wordChecker = new WordChecker();
    public static final AtomicInteger beautyWord3L = new AtomicInteger();
    public static final AtomicInteger beautyWord4L = new AtomicInteger();
    public static final AtomicInteger beautyWord5L = new AtomicInteger();

    public static void main(String[] args) {
        String[] words = getWords(100_000);

        threadsForChooseBeautyWords(words, List.of(
                wordChecker::isPalindrome,
                wordChecker::areIdenticalLetters,
                wordChecker::areLettersAscending)
        );

        System.out.println("Красивых слов с длиной 3: " + beautyWord3L.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautyWord4L.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautyWord5L.get() + " шт");
    }

    public static void threadsForChooseBeautyWords(
            String[] words,
            List<Predicate<String>> wordChecks
    ) {
        ThreadGroup wordChecksGroup = new ThreadGroup("WordChecks");

        wordChecks.forEach(wordCheck ->
                new Thread(wordChecksGroup, () ->
                        Stream.of(words)
                                .filter(wordCheck)
                                .forEach(Main::countBeautyWords)
                ).start()
        );

        waitsForThreadsShutdown(wordChecksGroup);
    }

    public static void countBeautyWords(String text) {
        int wordLen = text.length();
        switch (wordLen) {
            case 3:
                beautyWord3L.getAndIncrement();
                break;
            case 4:
                beautyWord4L.getAndIncrement();
                break;
            case 5:
                beautyWord5L.getAndIncrement();
                break;
            default:
                throw new UnsupportedOperationException("no necessary counter" +
                        " of beauty word " + wordLen + "L size"
                );
        }
    }

    protected static void waitsForThreadsShutdown(ThreadGroup threadGroup) {
        try {
            Thread[] threads = new Thread[1];
            while (threadGroup.activeCount() > 0) {
                threadGroup.enumerate(threads, false);
                threads[0].join();
            }
        } catch (InterruptedException ignored) {
        }
    }

    public static String[] getWords(int number) {
        Random random = new Random();
        String[] texts = new String[number];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}