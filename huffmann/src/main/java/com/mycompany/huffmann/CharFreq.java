/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffmann;

/**
 *
 * @author tiagoluz
 */
public class CharFreq implements Comparable{
    public char c;
    public long freq;

    public CharFreq() {
    }

    public CharFreq(char c, long freq) {
        this.c = c;
        this.freq = freq;
    }

    @Override
    public boolean equals(Object obj) {
    if(obj instanceof CharFreq) {
        CharFreq c = (CharFreq)obj;
        return c.c == this.c;
    } else return false;
    }

    @Override
    public int compareTo(Object o) {
        CharFreq c = (CharFreq)o;
        if(this.freq < c.freq)
            return 1;
        else if(this.freq == c.freq)
            return 0;
        else
            return -1;
    }

    @Override
    public String toString() {
        return ((int)this.c) + " (" + ((char)this.c) + ") " + this.freq + "\n";
    }

}
