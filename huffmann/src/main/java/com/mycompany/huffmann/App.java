/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffmann;

import java.io.File;
import java.util.BitSet;
import java.util.PriorityQueue;

/**
 *
 * @author tiagoluz
 */
public class App {
    public static void main(String[] args) throws Exception {
        
        if(args.length != 2) {
            System.err.println("Você deve informar o nome do arquivo texto a ser compactado e o comando (compactar ou descompactar)");
            System.exit(-1);
        }
        
        File f = new File(args[0]);
        if(!f.exists()) {
            System.err.println("Arquivo inexistente.");
            System.exit(-1);
        }
        
        if(!f.isFile()) {
            System.err.println("Não é um arquivo regular.");
            System.exit(-1);
        }
        
        if(!args[1].equals("compactar") && !args[1].equals("descompactar") && !args[1].equals("tabela")) {
            System.err.println("Informe um comando válido: compactar, descompactar, tabela");
            System.exit(-1);
        }
        
        HuffmanEncoding huff = new HuffmanEncoding();
        if(args[1].equals("compactar")) {
            String content = huff.readTextFile(args[0]);
            PriorityQueue<CharFreq> freq = huff.frequencyCalculate(content);
            huff.setFt(freq);
            huff.buildBinTree();
            String freqTableBin = huff.priorityQueue2bin(freq);
            String encoded = huff.encode(content);
            huff.createBinaryFile( freqTableBin + encoded, args[0]+".compactado");
            System.out.println("Arquivo compactado: " + args[0]+".compactado");
        } else if (args[1].equals("descompactar")) {
            String readed = huff.readBinaryFile(args[0]);
            String decoded = huff.decode(readed,true);
            huff.createTextFile(decoded, args[0]+".descompactado");
            System.out.println("Arquivo descompactado: " + args[0]+".descompactado");
        } else if(args[1].equals("tabela")) {
            String content = huff.readTextFile(args[0]);
            PriorityQueue<CharFreq> freq = huff.frequencyCalculate(content);
            System.out.println("Tabel de frequencia\n");
            while(!freq.isEmpty()) {
                System.out.println(freq.poll());
            }
            System.exit(0);
        }
    }
    
    public static void main2(String[] args) throws Exception {
        
        HuffmanEncoding huff = new HuffmanEncoding();
        
        /*
        // le arquivo comprimido COM tabela de frequencia
        String readed = huff.readBinaryFile("teste.compressed.bin");
        String decoded = huff.decode(readed, true);
        System.out.println(decoded);
        */
        
        String content = huff.readTextFile("teste.txt");
        PriorityQueue<CharFreq> freq = huff.frequencyCalculate(content);
        huff.setFt(freq);
        huff.buildBinTree();
        
        String freqTableBin = huff.priorityQueue2bin(freq);
        
        System.out.println("Tabela");
        System.out.println(freqTableBin);
        
        System.out.println("Content:");
        System.out.println(content);
        
        String encoded = huff.encode(content);
        System.out.println("Encoded:");
        System.out.println(encoded);
        
        String decoded = huff.decode(encoded,false);
        System.out.println("Decoded:");
        System.out.println(decoded);
        
        huff.createBinaryFile( freqTableBin + encoded, "teste.compressed.bin");
        System.out.println("Arquivo binário criado.");
        
        String readed = huff.readBinaryFile("teste.compressed.bin");
        System.out.println("Readed: ");
        System.out.println(readed);
        
        decoded = huff.decode(readed,true);
        System.out.println("Decoded: " + decoded);
        
        huff.createTextFile(decoded, "teste-decoded.txt");
        
        
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
