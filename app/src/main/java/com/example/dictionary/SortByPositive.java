package com.example.dictionary;

import java.util.Comparator;

/*
正序排序
 */
public class SortByPositive implements Comparator<Word> {
    public int compare(Word word1, Word word2) {
        return word1.getEnW().compareTo(word2.getEnW());
    }
}
