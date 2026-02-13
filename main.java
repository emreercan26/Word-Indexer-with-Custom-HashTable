/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.emre.structures;


public class main {

 
    public static void main(String[] args) {
        String filename = "input.txt"; 
        String outputFile = "output.csv";

        System.out.println("=== 1. DOĞRULUK TESTİ (Size: 100) ===");
        Word_Indexer testHash = new Word_Indexer();
        testHash.ReadFileandGenerateHash(filename, 100);

        System.out.println("En çok tekrar eden kelime: " + testHash.showMaxRepeatedWord());
        System.out.println("Kelime Kontrolü ('hello'): " + testHash.checkWord("hello")); 
        System.out.println("Kelime Kontrolü ('don't'): " + testHash.checkWord("don't")); 
        System.out.println("Kelime Kontrolü ('state'): " + testHash.checkWord("state")); 
        
        testHash.DisplayResultOrdered(outputFile);
        System.out.println("\n--------------------------------------------------");

        
        
        // Test edilecek boyutlar
        int[] testSizes = {500, 1000, 10000};

        for (int size : testSizes) {
            Word_Indexer reportHash = new Word_Indexer();
            reportHash.ReadFileandGenerateHash(filename, size);
            
            System.out.println("Tablo Boyutu: " + size + " -> Çarpışma Sayısı (Collision): " + reportHash.NumberOfCollusion());
        }
        System.out.println("--------------------------------------------------");
    }
}
