/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.emre.structures;

public interface Hash_Interface {
    void ReadFileandGenerateHash(String filename, int size);
    void DisplayResultOrdered(String outputFile);
    int showFrequency(String myword);
    String showMaxRepeatedWord();
    int checkWord(String myword);
    int NumberOfCollusion();
}