/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emre.structures;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Word_Indexer implements Hash_Interface {
    
    
    WordList[] hashArray;
    int arraysize;
    int itemcount;
    
   
    int collisionCount = 0; 

    
    public Word_Indexer() {
       
    }

    @Override
    public void ReadFileandGenerateHash(String filename, int size) {
        this.arraysize = size;
        this.hashArray = new WordList[size];
        for (int i = 0; i < arraysize; i++) {
            hashArray[i] = new WordList();
        }
        this.itemcount = 0;
        this.collisionCount = 0;

        File file = new File(filename);
        int globalPos = 0; 

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
              
                
                line = line.replace("-", " ");
                
                // Kelimelere böl (Boşluklara göre)
                String[] rawTokens = line.split("\\s+");

                for (String token : rawTokens) {
                    // Lowercase
                    String processed = token.toLowerCase();

                   
                    
                   
                    while (processed.length() > 0 && !Character.isLetterOrDigit(processed.charAt(0))) {
                        processed = processed.substring(1);
                    }
                    while (processed.length() > 0 && !Character.isLetterOrDigit(processed.charAt(processed.length() - 1))) {
                        processed = processed.substring(0, processed.length() - 1);
                    }

                    if (processed.length() > 0) {
                        globalPos++;
                        insert(processed, globalPos);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }

    // HASH FONKSİYONU 
    public int stringHashFunction(String wordToHash) {
        int hashKeyValue = 0;
        for (int i = 0; i < wordToHash.length(); i++) {
            int CharCode = wordToHash.charAt(i);
            hashKeyValue = (hashKeyValue * 31 + CharCode) % arraysize;
        }
        if (hashKeyValue < 0) {
            hashKeyValue = hashKeyValue + arraysize;
        }
        return hashKeyValue;
    }

    //  INSERT 
    public void insert(String theword, int position) {
        // Rehash kontrolü
        if (itemcount > arraysize * 0.75) {
            rehash();
        }

        int index = stringHashFunction(theword);

      
        
        WordList bucket = hashArray[index];
        Word existing = bucket.find(theword);

        if (existing == null) {
          
           
            if (bucket.headword != null) {
                collisionCount++;
            }
            itemcount++; 
        }
        
        // Ekleme işlemini WordList'e bırakıyoruz
        bucket.insert(theword, position);
    }

    //  REHASH METODU
    public void rehash() {
        System.out.println("Rehashing... Table doubling."); // Bilgi amaçlı
        WordList[] oldArray = hashArray;
        int oldSize = arraysize;

        arraysize = getNextPrime(oldSize * 2);
        hashArray = new WordList[arraysize];

        for (int i = 0; i < arraysize; i++) {
            hashArray[i] = new WordList();
        }

        itemcount = 0; 
        collisionCount = 0; 
        for (int i = 0; i < oldSize; i++) {
            Word current = oldArray[i].headword;
            while (current != null) {
                int newIndex = stringHashFunction(current.theword);
                
                hashArray[newIndex].insert(current.theword, current.position);
                itemcount++;
                current = current.next;
            }
        }
    }

    private int getNextPrime(int minNumber) {
        for (int i = minNumber; true; i++) {
            if (isPrime(i)) return i;
        }
    }

    private boolean isPrime(int number) {
        if (number % 2 == 0) return false;
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

 

    @Override
    public int showFrequency(String myword) {
        int index = stringHashFunction(myword);
        Word found = hashArray[index].find(myword);
        if (found != null) {
            return found.count;
        }
        return -1;
    }

    @Override
    public String showMaxRepeatedWord() {
        String maxWord = "";
        int maxCount = -1;

        for (int i = 0; i < arraysize; i++) {
            Word current = hashArray[i].headword;
            while (current != null) {
                if (current.count > maxCount) {
                    maxCount = current.count;
                    maxWord = current.theword;
                } else if (current.count == maxCount) {
                   
                    if (current.theword.compareTo(maxWord) < 0) {
                        maxWord = current.theword;
                    }
                }
                current = current.next;
            }
        }
        return maxWord;
    }

    @Override
    public int checkWord(String myword) {
        int index = stringHashFunction(myword);
        Word found = hashArray[index].find(myword);
        if (found != null) {
            // Pozisyonları tek satırda yazdır
            System.out.println(found.theword + ": " + found.position.toString().replace("[", "").replace("]", "").replace(",", ""));
            return found.count;
        }
        return -1;
    }

    @Override
    public int NumberOfCollusion() {
        return collisionCount;
    }

    @Override
    public void DisplayResultOrdered(String outputFile) {
        ArrayList<Word> allWords = new ArrayList<>();

        // 1. Tüm kelimeleri topla
        for (int i = 0; i < arraysize; i++) {
            Word current = hashArray[i].headword;
            while (current != null) {
                allWords.add(current);
                current = current.next;
            }
        }

        // 2. SıraLa
        Collections.sort(allWords, new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                if (w1.count != w2.count) {
                    return w2.count - w1.count; // Frekans azalan (Büyükten küçüğe)
                }
                return w1.theword.compareTo(w2.theword); // Kelime artan (a-z)
            }
        });

        // 3. Dosyaya yaz 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (Word w : allWords) {
                // Format: word, frequency, pos1; pos2; pos3
                StringBuilder sb = new StringBuilder();
                sb.append(w.theword).append(", ").append(w.count).append(", ");
                
                for(int k=0; k<w.position.size(); k++){
                    sb.append(w.position.get(k));
                    if(k < w.position.size()-1) sb.append("; ");
                }
                
                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("Sonuçlar dosyaya yazıldı: " + outputFile);
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }
}