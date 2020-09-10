package com.mycompany.huffmann;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanEncoding {

  PriorityQueue<CharFreq> ft;
  BinTree bt;
  HashMap<Character,CharFreq> hm;
  

  public String readTextFile(String fileName) throws IOException {
      return new String(Files.readAllBytes(Path.of(new File(fileName).toURI())));
  }

  public PriorityQueue<CharFreq> frequencyCalculate(String content) {
      PriorityQueue<CharFreq> pq = new PriorityQueue<>();
      hm = new HashMap<Character,CharFreq>();

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


  public HuffmanEncoding() {}

    public void setFt(PriorityQueue<CharFreq> ft) {
        this.ft = ft;
    }


  public void buildBinTree(){
    int n = ft.size();
    Iterator it = ft.iterator();
    ArrayList<BinTree> Q = new ArrayList<>();
    while(it.hasNext()) {
      CharFreq cf = (CharFreq)it.next();
      Node tempNode = new Node(cf.c,cf.freq);
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
    bt = extractMin(Q);
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
  
  public String encode(String fileContent) {
      int l = fileContent.length();
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < l; i++) {
          char c = fileContent.charAt(i);
          String s = getCharEncode(c);
          System.out.println(c + " " + s);
          sb.append(s);
      }
      return sb.toString();
  }
  
  
  private String getCharEncode(char c) {
     CharFreq ch = hm.get(c);
     StringBuilder sb = new StringBuilder();
     Node n = bt.root;
     while( n.getLeftChild() != null || n.getRightChild() != null ) {
         if(ch.freq < n.getFreq()) {
             sb.append("0");
             n = n.getLeftChild();
         } else {
             sb.append("1");
             n = n.getRightChild();
         }
     }
     return sb.toString();
  }
  
  
  public String decode(String encodedContent) {
     return "" ;
  }
  
  public void printEncoding(){
    printEncodingAux(bt.getRoot(),"");
  }

  private void printEncodingAux(Node n, String code){
    if(n.getLeftChild() == null && n.getRightChild() == null){
            System.out.println(n.getCharacter()+" - "+code);
            return;
    }
    printEncodingAux(n.getLeftChild(), code+"0");
    printEncodingAux(n.getRightChild(), code+"1");
  }

    

   

}
