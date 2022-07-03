/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private int freq;
    private Character character;
    private HuffmanNode leftChild;
    private HuffmanNode rightChild;

    public HuffmanNode() {
        this.freq = 0;
        this.character = null;
        this.leftChild = null;
        this.rightChild = null;
    }   

    public HuffmanNode(Character character, int freq, HuffmanNode leftChild, HuffmanNode rightChild) {
        this.freq = freq;
        this.character = character;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character c) {
        this.character = c;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isLeaf() {
        return this.getLeftChild() == null && this.getRightChild() == null && this.getCharacter() != null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.getFreq() - o.getFreq();
    }
}
