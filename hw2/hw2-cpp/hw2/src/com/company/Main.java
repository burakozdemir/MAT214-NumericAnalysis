package com.company;

public class Main {
    public static final int JACOBI = 1;
    public static final int GESP = 0;
    public static void main(String[] args) {
	    int method;
        String filename;
        if(args.length == 1)
        {
            filename = args[1];
            method = JACOBI;
        }
//        else if (args.length == 3){
//            if(args[3].contentEquals("GESP") == true){
//                method = GESP;
//            }
//            method = JACOBI;
//            filename = args[1];
//        }
//        else{
//            System.out.println("USAGE : ......");
//            return;
//        }
//        int retVal;
//        if(method == JACOBI){
//            retVal = SolveEqu.JacobiMethod(filename);
//        }
//        else{
//            retVal = SolveEqu.GaussElim(filename);
//        }
        SolveEqu.JacobiMethod("test.txt");
        double xs[] = SolveEqu.getXValues();
        for (int i = 0; i < xs.length; ++i){
            System.out.println("X " + i + " =  " + xs[i]);
        }
    }
}
