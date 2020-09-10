package com.mycompany.huffmann;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanEncoding {

  PriorityQueue<CharFreq> ft;
  public BinTree bt;
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
          sb.append(s);
      }
      return sb.toString();
  }
  
  
  public String getCharEncode(char c) {
     Node n = bt.root;
     boolean found = false;
     return getCharEncodeAux("",c,n);
  }
  
    private String getCharEncodeAux(String code, char c, Node n) {
        if(n.getLeftChild() == null && n.getRightChild() == null) {
            if(n.getCharacter() == c) {
                return code;
            } else {
                return null;
            }
        } else {
            Node nn = n.getLeftChild();
            String l = getCharEncodeAux(code+"0", c, nn);
            if(l != null) {
                return l;
            }
            nn = n.getRightChild();
            String r = getCharEncodeAux(code+"1", c, nn);
            if(r != null) {
                return r;
            } 
            return null;
        }
    }
  
  public String decode(String content) {
     int l = content.length();
     Node n = bt.getRoot();
     StringBuilder sb = new StringBuilder();
     for(int i = 0; i < l; i++) {
         char c = content.charAt(i);
         if(c == '0') {
             n = n.getLeftChild();
         } else {
             n = n.getRightChild();
         }
         if(n.getLeftChild() == null && n.getRightChild() == null) {
             sb.append(n.getCharacter());
             n = bt.getRoot();
         }
     }
     return sb.toString();
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
  
    public String readBinaryFile(String filename) {
        File f = new File(filename);
        StringBuilder sb = new StringBuilder();
        try {
            DataInputStream is = new DataInputStream(new FileInputStream(f));
            BitSet bs = BitSet.valueOf(is.readAllBytes());
            for(int i = 0; i < bs.length();i++) {
                sb.append(bs.get(i)?"1":"0"); 
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
  
    void createBinaryFile(String encoded, String testecompressedbin) {
        File f = new File(testecompressedbin);
        
        int l = encoded.length();
        
        BitSet bs = new BitSet(l);
        for(int i = 0; i < l; i++) {
            char c = encoded.charAt(i);
            if(c == '1') bs.set(i);
        }
        
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(f));
            os.write(bs.toByteArray());
            os.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    

    

   

}
