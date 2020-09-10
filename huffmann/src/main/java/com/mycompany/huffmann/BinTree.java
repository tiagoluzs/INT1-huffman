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
public class BinTree {
   
    public Node root;

    public BinTree(Node root){

            this.root = root;

    }

    public Node getRoot(){

            return root;

    }

    public Double getFreq(){

            return root.getFreq();

    }

    private String print(){

            return printAux(root);

    }

    private String printAux(Node n){

            if(n == null) return "";

            return "(["+n.getCharacter()+","+n.getFreq()+"],"+printAux(n.getLeftChild())+","+printAux(n.getRightChild())+")";

    }

    @Override
    public String toString() {

            return this.print();
    }
}
