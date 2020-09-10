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
public class Node {

    private Integer character;
    private double freqVal;
    private Node leftChild;
    private Node rightChild;

    public Node(){

    }

    public Node(Integer character, double freqVal){

            this.character = character;
            this.freqVal = freqVal;

    }

    public Node(double freqVal, Node leftChild, Node rightChild){

            this.character = character;
            this.freqVal = freqVal;
            this.leftChild = leftChild;
            this.rightChild = rightChild;

    }

    public Integer getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
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
