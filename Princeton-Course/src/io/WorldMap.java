package io;

import stdlib.StdDraw;
import stdlib.StdIn;

public class WorldMap {
    public static void main(String[] args) {
        String region = "";
        int width = 0;
        int height = 0;
        StdDraw.setCanvasSize(width = StdIn.readInt(), height = StdIn.readInt());
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        int numOfVertices = 0;
        double[] verticesX = {};
        double[] verticesY = {};
        while (!StdIn.isEmpty()) {
            region = StdIn.readString();
            numOfVertices = StdIn.readInt();
            verticesY = new double[numOfVertices];
            verticesX = new double[numOfVertices];
            for (int i = 0; i < numOfVertices*2; i++) {
                if (i % 2 == 0) {
                    verticesX[i/2] = StdIn.readDouble();
                } else {
                    verticesY[(i-1)/2] = StdIn.readDouble();
                }
            }
            StdDraw.polygon(verticesX, verticesY);
        }
    }
}
