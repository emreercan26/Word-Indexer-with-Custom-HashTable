/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emre.structures;

import java.util.ArrayList;

public class WordList {

    public Word headword = null;

    // Normal Insert
    public void insert(String theword, int position) {
        Word newword = find(theword);
        if (newword != null) {
            newword.incrementcount(position);
        } else {
            
            newword = new Word(theword, position);
            newword.next = headword;
            headword = newword;
        }
    }
    
    // Rehash için Insert 
    public void insert(String theword, ArrayList<Integer> positions) {
        Word newword = new Word(theword, positions); 
        newword.next = headword;
        headword = newword;
    }

    public void displayWordList() {
        Word current = headword;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    public Word find(String targetword) {
        Word current = headword;
        while (current != null) {
            if (equalwords(current.theword, targetword)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    
    public boolean equalwords(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            
            if (c1 >= 'A' && c1 <= 'Z') c1 = (char)(c1 + 32);
            if (c2 >= 'A' && c2 <= 'Z') c2 = (char)(c2 + 32);

            if (c1 != c2) return false;
        }
        return true;
    }
}