package demo.main;

import demo.tree.Node;

import java.util.Map;

/**
 * This class contains the information read in a file.
 */
public class Info {
    private Node endNode;
    private Node iniNode;
    private char[] blocks;
    private int s;
    private Map<Character, Integer> sizeMap;

    public Info(Node endNode, Node iniNode, char[] blocks, int s,  Map<Character, Integer> sizeMap){
        this.endNode = endNode;
        this.iniNode = iniNode;
        this.blocks = blocks;
        this.s = s;
        this.sizeMap = sizeMap;
    }

    public Node getEndNode() {
        return endNode;
    }

    public Node getIniNode() {
        return iniNode;
    }

    public char[] getBlocks() {
        return blocks;
    }

    public int getS() {
        return s;
    }

    public Map<Character, Integer> getSizeMap() {
        return sizeMap;
    }
}
