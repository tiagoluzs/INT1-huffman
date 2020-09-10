/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffmann;

import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 *
 * @author tiagoluz
 */
public class App {
    public static void main(String[] args) throws Exception {
        
        HuffmanEncoding huff = new HuffmanEncoding();
        String content = huff.readTextFile("teste.txt");
        PriorityQueue<CharFreq> freq = huff.frequencyCalculate(content);
        
        /*
        //FreqTable table = new FreqTable(3);

        Hashtable<Character,Double> table = new Hashtable<Character,Double>();

        table.put('a', 45.0);
        table.put('b', 13.0);
        table.put('c', 12.0);
        table.put('d', 16.0);
        table.put('e', 9.0);
        table.put('f', 5.0);

        HuffmanEncoding hf = new HuffmanEncoding(table);

        hf.printEncoding();
        */

	}
}
