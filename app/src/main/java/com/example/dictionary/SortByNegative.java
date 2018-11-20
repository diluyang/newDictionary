package com.example.dictionary;

import java.util.Comparator;

/*
反序排序
 */
public class SortByNegative implements Comparator<Word> {
    public int compare(Word word1, Word word2) {
        return word2.getEnW().compareTo(word1.getEnW());
    }
}
