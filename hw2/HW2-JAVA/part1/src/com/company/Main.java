package com.company;

import java.io.*;
import java.util.Scanner;
import java.lang.*;

public class Main {

    final private int maximumIter = 20;
    final private double tolerance = 0.0001;

    private int uzunluk=0;
    private double[]  bmatrix;
    private double[] resultMatrix;
    private double[][] equations;

    public Main(){
    }
    public static void main(String[] args) throws IOException {
        String filename=args[1];
        int method;
        if(args.length !=4 )
        {
            System.out.println("USAGE : ./solver -i input.txt -m <solvingMode>");
            return;
        }
        Main object = new Main();
        object.readMatrix(filename);
        object.showMatrix();

        if(args[3].equals("GESP"))
            object.GESP(filename);
        else if(args[3].equals("JCB"))
            object.JCB(filename);
        else
            System.out.println("WRONG METHOD");

        double xvalues[]= object.getResultMatrix();
        System.out.println("---------------RESULT VALUES-----------");
        for (int i = 0; i < object.getResultMatrix().length; ++i){
            System.out.println("X" + i + " ==>  " + object.getResultMatrixIndis(i));
        }

    }


    public double getBmatrix(int indis) {
        return bmatrix[indis];
    }

    public void setBmatrix(double[] bmatrix) {
        this.bmatrix = bmatrix;
    }

    public double[] getResultMatrix() {
        return resultMatrix;
    }

    public double getResultMatrixIndis(int indis){ return resultMatrix[indis];}

    public void setResultMatrix(double[] resultMatrix) {
        this.resultMatrix = resultMatrix;
    }

    public double[][] getEquations() {
        return equations;
    }

    public void setEquations(double[][] equations) {
        this.equations = equations;
    }

    public void readMatrix(String fileInput) throws IOException {
        try {
            File file = new File(fileInput);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            int row = 0;
            String satir = "";

            /*
            * Eşitliklerın uzunlugunu bulma
            * */
            BufferedReader reader2 = new BufferedReader(new FileReader(fileInput));
            Scanner scanner = new Scanner(reader2);
            String  lines = scanner.nextLine();
            String[] satirUzunluk =lines.split(",");

            /*
            * Equations için alan verme
            * */
            equations=new double[satirUzunluk.length-1][satirUzunluk.length-1];
            bmatrix=new double[satirUzunluk.length];
            /*
            * Atama işlemleri*/
            while ((satir = reader.readLine())!=null) {
                String[] temizsatir = satir.split(",");
                    for (int i = 0; i < temizsatir.length-1; i++) {
                        equations[row][i] = Double.parseDouble(temizsatir[i]);
                        uzunluk++;
                    }
                    bmatrix[row]=Double.parseDouble(temizsatir[temizsatir.length-1]);
                row++;
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void GESP(String file){
        int arr[] = new int[equations.length];
        double scaledVect[] = new double[equations.length];
        resultMatrix = new double[equations.length];

        //maximum sayılar bulunur her satırdaki
        for (int i = 0; i < equations.length; ++i){
            arr[i] = i;
            scaledVect[i] = maxValue(equations[i]);
        }

        int m;
        double max = 0;

        // pivot vectoru bulunur
        for (int k = 0; k < equations.length-1; ++k){
            m = k;
            for (int i = k ; i < equations.length; ++i){
                double r = Math.abs(equations[arr[i]][k]/scaledVect[arr[i]]);
                if(r > max){
                    max = r;
                    m = i; // the largest indices
                }
            }

            System.out.println();

            showMatrix();
            System.out.println();

            int temp = arr[k];
            arr[k] = arr[m];
            arr[m] = temp;
            double a;

            for (int i = k +1 ; i < equations.length; ++i){
                a = equations[arr[i]][k]/equations[arr[k]][k];
                for (int l = k ; l < equations.length; ++l){
                    equations[arr[i]][l] = equations[arr[i]][l] - a * equations[arr[k]][l];
                }
                bmatrix[arr[i]] = bmatrix[arr[i]] - (a) * bmatrix[arr[k]];
            }
        }
        resultMatrix[equations.length-1] = bmatrix[arr[equations.length-1]] / equations[arr[equations.length-1]][equations.length-1];
        for (int i = equations.length - 2; i >= 0; i--) {
            double s = bmatrix[arr[i]];
            for (m = i; m < equations.length; m++) {
                s = s - equations[arr[i]][m] * resultMatrix[m];
            }
            resultMatrix[i] = s/equations[arr[i]][i];
        }
        System.out.println();

        showMatrix();
        System.out.println();
        System.out.println("DONE SUCCESFULLY");
    }
    public void JCB(String file){
        double oldVals[] = new double[equations.length];
        resultMatrix = new double[equations.length];
        double tempVals[] = new double[equations.length];
        int m = 1;
        while (m <= maximumIter){
            for (int i = 0; i < equations.length ; i++) {
                double getx = 0;
                for (int j = 0; j < equations.length; j++) {
                    if(j != i)
                        getx += equations[i][j] * oldVals[j];
                }
                tempVals[i]  = (bmatrix[i] - getx ) / equations[i][i];
            }

            if(control(oldVals,tempVals))
                break;

            for (int i = 0; i < oldVals.length; i++) {
                oldVals[i] = tempVals[i];
            }
            m++;
        }
        for (int i = 0; i < equations.length; i++) {
            resultMatrix[i] = tempVals[i];
        }
    }
    public void showMatrix(){
        for (int i = 0; i < equations.length ; i++) {
            System.out.print("| ");
            for (int j = 0; j < equations.length; j++) {
                System.out.printf(" %5.3f ",equations[i][j]);
            }
            System.out.println("|");
        }
    }
    public boolean control(double[] oldValues, double[] tempVal){
        for (int i = 0; i < tempVal.length ; i++) {
            if(Math.abs(tempVal[i] - oldValues[i]) / Math.abs(tempVal[i]) < tolerance )
                return true;
        }
        return false;
    }

    public int getUzunluk() {
        return uzunluk;
    }

    public void setUzunluk(int uzunluk) {
        this.uzunluk = uzunluk;
    }

    private static double maxValue(double m[]){
        double maximum = 0;
        for (int i = 0; i < m.length; ++i){
            if(maximum < Math.abs(m[i])){
                maximum = Math.abs(m[i]);
            }
        }
        return maximum;
    }
}
