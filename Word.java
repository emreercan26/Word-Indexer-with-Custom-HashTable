/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emre.structures;

import java.util.ArrayList;
//word class
public class Word {
    public String theword;
    public int count;
    public Word next;
    public ArrayList<Integer> position; 
   
    // constuctor
    public Word(String theword, int position1) {
        this.theword = theword;
        this.count = 1;
        this.position = new ArrayList<>();
        this.position.add(position1);
        this.next = null;
    }

    
    public Word(String theword, ArrayList<Integer> positions) {
        this.theword = theword;
        
        this.position = new ArrayList<>(positions); 
        this.count = positions.size();
        this.next = null;
    }

    public void incrementcount(int currentposition) {
        this.position.add(currentposition);
        this.count++;
    }

    public String toString() {
        return "word: " + this.theword + " Frequency: " + this.count + " positions: " + this.position.toString();
    }
}