

import com.mycompany.huffmann.CharFreq;
import com.mycompany.huffmann.HuffmanEncoding;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.PriorityQueue;
import org.junit.Assert;
import org.junit.Test;

public class HuffmanEncodingTest {
    
    public HuffmanEncodingTest() {
    }
    
    @Test 
    public void freqTest() {
        HuffmanEncoding huff = new HuffmanEncoding();
        PriorityQueue<CharFreq> pq = huff.frequencyCalculate("Este é um teste unitário com alguns AAAAAAAAAA.");
        CharFreq mais1 = pq.poll();
        CharFreq mais2 = pq.poll();
        Assert.assertEquals(mais1.c,'A');
        Assert.assertTrue(mais1.freq >= mais2.freq);
    }
    
    @Test 
    public void freqConstrucaoTest() {
        HuffmanEncoding huff = new HuffmanEncoding();
        PriorityQueue<CharFreq> pq = huff.frequencyCalculate("E");
        Assert.assertTrue(pq.size()>0);
    }
    
    @Test 
    public void freqConstrucaoVazioTest() {
        HuffmanEncoding huff = new HuffmanEncoding();
        PriorityQueue<CharFreq> pq = huff.frequencyCalculate("");
        Assert.assertTrue(pq == null);
    }
    
     @Test 
    public void freqConstrucaoNullTest() {
        HuffmanEncoding huff = new HuffmanEncoding();
        PriorityQueue<CharFreq> pq = huff.frequencyCalculate(null);
        Assert.assertTrue(pq == null);
    }
    
    @Test
    public void textFileTest()  {
        String filename = new Date().getTime() + ".txt";
        String content = null;
        try {
            File f = new File(filename);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(filename.getBytes());
            fos.close();
            HuffmanEncoding huff = new HuffmanEncoding();
            content = huff.readTextFile(filename);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals("Conteúdo do arquivo esperado está incorreto.", content, filename);
    }
    
}
