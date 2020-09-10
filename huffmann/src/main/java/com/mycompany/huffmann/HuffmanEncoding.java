/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffmann;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiagoluz
 */
public class HuffmanEncoding {
    
	public String readTextFile(String fileName) throws IOException {
            return new String(Files.readAllBytes(Path.of(new File(fileName).toURI())));            
	}
        
        

	Hashtable<Character,Double> ft;
	BinTree bt;

    public PriorityQueue<CharFreq> frequencyCalculate(String content) {
        PriorityQueue<CharFreq> pq = new PriorityQueue<>();
        HashMap<Character,CharFreq> hm = new HashMap<Character,CharFreq>();

        int l = content.length();
        for(int i = 0; i < l; i++) {
            char c = content.charAt(i);
            CharFreq f = new CharFreq(c, 1);
            if(hm.containsKey(c)) {
                hm.get(c).freq++;
            } else {
                hm.put(c, f);
            }
        }
        
        for(CharFreq cf : hm.values()) {
            pq.offer(cf);
        }
        
        return pq;
    }

	private class Node{

		private char character;
		private double freqVal;
		private Node leftChild;
		private Node rightChild;

		public Node(){

		}

		public Node(char character, double freqVal){

			this.character = character;
			this.freqVal = freqVal;

		}

		public Node(double freqVal, Node leftChild, Node rightChild){

			this.character = character;
			this.freqVal = freqVal;
			this.leftChild = leftChild;
			this.rightChild = rightChild;

		}

		public void setLeftChild(Node leftChild){

			this.leftChild = leftChild;

		}

		public void setRightChild(Node rightChild){

			this.rightChild = rightChild;

		}

		public void setFreq(Double freq){

			freqVal = freq;

		}

		public Node getLeftChild(){

			return leftChild;

		}

		public Node getRightChild(){

			return rightChild;

		}

		public Double getFreq(){

			return freqVal;

		}

	}

	private class BinTree{

		private Node root;

		public BinTree(Node root){

			this.root = root;

		}

		public Node getRoot(){

			return root;

		}

		public Double getFreq(){

			return root.freqVal;

		}

		private String print(){

			return printAux(root);

		}

		private String printAux(Node n){

			if(n == null) return "";

			return "(["+n.character+","+n.freqVal+"],"+printAux(n.leftChild)+","+printAux(n.rightChild)+")";

		}

		@Override
		public String toString() {

			return this.print();
		}


	}
        
        public HuffmanEncoding() {}

	public HuffmanEncoding(Hashtable<Character,Double> frequencyTable){

		ft = frequencyTable;
		bt = buildBinTree();

	}

	private BinTree buildBinTree(){

		int n = ft.size();

		List<BinTree> Q = new ArrayList<BinTree>();

		for(Character c : ft.keySet()){

			Node tempNode = new Node(c,ft.get(c));
			BinTree temp = new BinTree(tempNode);
			Q.add(temp);

		}

		for(int i=1; i < n; i++){

			Node z = new Node();
			z.setLeftChild(extractMin(Q).getRoot());
			z.setRightChild(extractMin(Q).getRoot());
			z.setFreq(z.getLeftChild().getFreq() + z.getRightChild().getFreq());
			Q.add(new BinTree(z));

		}

		return extractMin(Q);

	}

	private BinTree extractMin(List<BinTree> Q){

		BinTree result = Q.get(0);
		int iRemove = 0;
		for(int i=1; i < Q.size(); i++){

			if(Q.get(i).getFreq() < result.getFreq()){
				result = Q.get(i);
				iRemove = i;
			}

		}

		Q.remove(iRemove);
		return result;

	}


	public void printEncoding(){

		printEncodingAux(bt.getRoot(),"");

	}

	private void printEncodingAux(Node n, String code){

		if(n.getLeftChild() == null && n.getRightChild() == null){
			System.out.println(n.character+" - "+code);
			return;
		}

		printEncodingAux(n.getLeftChild(), code+"0");
		printEncodingAux(n.getRightChild(), code+"1");
	}

}
