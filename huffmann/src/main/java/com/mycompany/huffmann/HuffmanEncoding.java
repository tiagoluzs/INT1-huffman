/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffmann;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

/**
 *
 * @author tiagoluz
 */
public class HuffmanEncoding {

  Hashtable<Integer,Double> ft;
  Hashtable<Integer, String> ct;
  BinTree bt;

  /**
   * Método que cria a tabela de frequência
   * @param filename
   */
	private void buildFT(String filename){

		this.ft = new Hashtable<Integer,Double>();

		File file = new File(filename);
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			int c;

			while((c = br.read()) != -1){
				if (ft.keySet().contains(c)){
					ft.put(c, ft.get(c)+1);	
				}
				else{
					ft.put(c, 1.0);
				}
			}
			System.out.println(ft);
			System.out.println(ft.size());
		}
		catch(IOException e){
			System.out.println(e);
		}
	}


  public HuffmanEncoding() {}

  public HuffmanEncoding(Hashtable<Character,Double> frequencyTable){

      buildFT("king_james.txt"); //Cuidado ao rodar esse arquivo
      // System.out.println(ft.size());
      bt = buildBinTree();
      printEncoding();
      codify("king_james.txt"); //Cuidado ao rodar esse arquivo

  }

  
  /**
   * Método que vai transformar os códigos em bytes (ainda em construção)
   * @param fileInput
   */
	private void codify(String fileInput){
    String huff_code = codeString(fileInput);
    List<Byte> byteList = new ArrayList<>();
    Byte bt = 0x00;
    int bytePos = 0;
    int bitsNum = 0; //Número de bits que codifiquei (guardar isso nos primeiros 4 bytes, talvez?)
    System.out.println("Iam Here!");
    for(char bit : huff_code.toCharArray()){
      //Depois tenho que ler da esquerda para direita
      bitsNum++;
      bt = (byte) (bit == '0' ? (bt | 0b00000000) : (bt | 0b00000001)); //Aplicando a máscara
      bytePos++;
      if(bytePos >= 8){ //Se ja shiftei 8 bits, então vou para o próximo byte
        byteList.add(bt); //Adiciono o byte completo na lista
        bt = 0x00; //Zero o byte

        bytePos = 0; //Zerando a posicao do bit
      }
      bt = (byte) (bt << 1); //Deslocando um bit à esquerda (isso implica na leitura, temos que decodificar da esqueda para direita)
    }
    System.out.println(byteList);
    System.out.println(bitsNum);
  }
  

  /**
   * Método que codifica todo o conteúdo do arquivo em uma string (código)
   * @param filename
   * @return codigo do texto completo
   */
  private String codeString(String filename){
		File file = new File(filename);
		StringBuilder code = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			int c;

			while((c = br.read()) != -1){
				code.append(this.ct.get(c));
			}
			System.out.println("Code Size: "+code.length());
			System.out.println(code.substring(0, 8));
			System.out.println(code.substring(8, 16));
		} catch(IOException e){
			System.out.println(e);
		}
		return code.toString();
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
		
		this.ct = new Hashtable<Integer, String>();
		printEncodingAux(bt.getRoot(),"");
		System.out.println(ct);
		
	}

	private void printEncodingAux(Node n, String code){
		
		if(n.getLeftChild() == null && n.getRightChild() == null){
			System.out.println(n.getCharacter()+" - "+code);
			this.ct.put(n.getCharacter(), code); // Construindo a tabela de códigos.
			return;
		}
		
		printEncodingAux(n.getLeftChild(), code+"0");
		printEncodingAux(n.getRightChild(), code+"1");
	}

}
