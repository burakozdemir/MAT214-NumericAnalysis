package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by emre on 3/27/17.
 */
public class SolveEqu {

    final static private int MAXITER = 20;
    final static private double TOLERANCE = 0.0001;
    static private double[][] matrix;
    static private double[]  bmatrix;
    static private double[] XValues;

    private static int ReadyMatrixs(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            Scanner scanner = new Scanner(reader);
            String  lines = scanner.nextLine();
            String[] strs = lines.trim().split("\\s+");

            matrix = new double[strs.length-1][strs.length -1];
            bmatrix = new double[strs.length - 1];

            int j = 0;

            do{
                for (int i = 0; i < strs.length - 1; i++) {
                    matrix[j][i] = Integer.parseInt(strs[i]);
                }
                bmatrix[j] =  Integer.parseInt(strs[strs.length-1]);

                lines = scanner.nextLine();
                strs = lines.trim().split("\\s+");
                ++j;
            }while (scanner.hasNextLine());
            for (int i = 0; i < strs.length - 1; i++) {
                matrix[j][i] = Integer.parseInt(strs[i]);
            }
            bmatrix[j] =  Integer.parseInt(strs[strs.length-1]);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int GaussElim(String Filename){
        ReadyMatrixs(Filename);
        XValues = new double[matrix.length];
        double sVector[] = new double[matrix.length];
        int pArray[] = new int[matrix.length];

        // ready SVector
        for (int i = 0; i < matrix.length; ++i){
            sVector[i] = max(matrix[i]);
            pArray[i] = i;
        }

        int j;
        double rmax = 0;

        // pivot satırını bul
        for (int k = 0; k < matrix.length-1; ++k){
            j = k;
            for (int i = k ; i < matrix.length; ++i){
                double r = Math.abs(matrix[pArray[i]][k]/sVector[pArray[i]]);
                if(r > rmax){
                    rmax = r;
                    j = i; // largest element in j
                }
            }

            System.out.println();

            PrintMatrix();
            System.out.println();

            int temp = pArray[k];
            pArray[k] = pArray[j];
            pArray[j] = temp;
            double a;

            for (int i = k +1 ; i < matrix.length; ++i){
                a = matrix[pArray[i]][k]/matrix[pArray[k]][k];
                for (int l = k ; l < matrix.length; ++l){
                    matrix[pArray[i]][l] = matrix[pArray[i]][l] - a * matrix[pArray[k]][l];
                }
                bmatrix[pArray[i]] = bmatrix[pArray[i]] - (a) * bmatrix[pArray[k]];
            }
        }

//        for (int k = 1 ; k < matrix.length -1 ; ++k){
//            for (int i = k + 1; i <  matrix.length; i++) {
//                bmatrix[pArray[i]] = bmatrix[pArray[i]] - matrix[pArray[i]][k] * bmatrix[pArray[k]];
//            }
//        }
        XValues[matrix.length-1] = bmatrix[pArray[matrix.length-1]] / matrix[pArray[matrix.length-1]][matrix.length-1];
        for (int i = matrix.length - 2; i >= 0; i--) {
            double s = bmatrix[pArray[i]];
            for (j = i; j < matrix.length; j++) {
                s = s - matrix[pArray[i]][j] * XValues[j];
            }
            XValues[i] = s/matrix[pArray[i]][i];
        }
        System.out.println();

        PrintMatrix();
        System.out.println();
        return 1;

    }

    private static void PrintMatrix() {
        for (int i = 0; i < matrix.length ; i++) {
            System.out.print("| ");
            for (int j = 0; j < matrix.length; j++) {
                System.out.printf(" %5.3f ",matrix[i][j]);
            }
            System.out.println("|");
        }
    }

    private static double max(double m[]){
        double max = 0;
        for (int i = 0; i < m.length; ++i){
            if(max < Math.abs(m[i])){
                max = Math.abs(m[i]);
            }
        }
        return max;
    }

    public static double[] getXValues() {
        return XValues;
    }

    public static int JacobiMethod(String filename){
        ReadyMatrixs(filename);
        XValues = new double[matrix.length];
        double XtempVals[] = new double[matrix.length];
        double oldXtempVals[] = new double[matrix.length];
        int k = 1;
        while (k <= MAXITER){

            for (int i = 0; i < matrix.length ; i++) {
                double getx = 0;
                for (int j = 0; j < matrix.length; j++) {
                    if(j != i)
                        getx += matrix[i][j] * oldXtempVals[j];
                }
                XtempVals[i]  = (bmatrix[i] - getx ) / matrix[i][i];
            }

            if(CheckThisFuckingAssHoles(oldXtempVals,XtempVals))
            {
                break;
            }

            for (int i = 0; i < oldXtempVals.length; i++) {
                oldXtempVals[i] = XtempVals[i];
            }
            k++;
        }
        for (int i = 0; i < matrix.length; i++) {
            XValues[i] = XtempVals[i];
        }
        return 1;
    }

    private static boolean CheckThisFuckingAssHoles(double[] oldXtempVals, double[] xtempVals) {
        for (int i = 0; i < xtempVals.length ; i++) {
            if(Math.abs(xtempVals[i] - oldXtempVals[i]) / Math.abs(xtempVals[i]) < TOLERANCE )
                return true;
        }
        return false;
    }
}
