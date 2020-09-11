package com.mycompany.huffmann;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
  
  public String decode(String content,boolean parseFreqTable) {
     int realContentLength = 0;
     
     if(parseFreqTable) {
        StringBuilder ft = new StringBuilder();
        for(int i = 0; i < content.length(); i+=8) {
            // se os dois próximos caracteres são || em binário, 
            // entende que encerrou a tabela de frequencia e começam os dados
            if(content.substring(i,i+16).equals("0111110001111100")) { // ||
                content = content.substring(i+16);
                break;
            } else {
                ft.append((char)Integer.parseInt(content.substring(i,i+8), 2));
            }
        }
        String t[] = ft.toString().split("\\|");
        this.ft = new PriorityQueue<CharFreq>();
        for(String it : t) {
            String itt[] = it.split(",");
            int c = Integer.valueOf(itt[0]);
            CharFreq cf = new CharFreq((char)c, Long.valueOf(itt[1]));
            this.ft.offer(cf);
            realContentLength += cf.freq;
        }
        
        this.buildBinTree();
     }
     
     
     int l = content.length();
     int qtdCaracteres = 0;
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
             qtdCaracteres++;
             sb.append(n.getCharacter());
             if(realContentLength == qtdCaracteres) {
                 break;
             }
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
  
    void createBinaryFile(String encoded, String arquivo) {
        File f = new File(arquivo);
        
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

    public String priorityQueue2bin(PriorityQueue<CharFreq> freq) {
        Iterator it = freq.iterator();
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()) {
            CharFreq cf = (CharFreq)it.next();
            sb.append(((int)cf.c) + "," + cf.freq + "|");
        }
        sb.append("|"); // barra dupla marca o fim da tabela de frequencias
        String s = sb.toString();
        
        StringBuilder sbb = new StringBuilder();
        for(int i = 0; i < s.length();i++) {
            sbb.append(pad(Integer.toBinaryString(s.charAt(i)),8));
        }
        return sbb.toString();
    }
    
    private String pad(String s, int l) {
        return String.format("%1$" + l + "s", s).replace(' ', '0');
    }

    void createTextFile(String decoded, String file)  {
        try {
            FileOutputStream fos = new FileOutputStream(new File(file));
            fos.write(decoded.getBytes());
            fos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    

    

    

   

}
