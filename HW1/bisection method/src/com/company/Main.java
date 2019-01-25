package com.company.Main

import java.util.Scanner;

import static java.lang.Math.*;

public class Main {

    private int sinirIterasyon=0;
    private double sinir;
    private double sinir2;
    private String myError;
    private double epsil;

    public Main(double sinir,double sinir2,String error,double epsilon){
        this.sinir=sinir;
        this.sinir2 = sinir2;
        this.myError=error;
        this.epsil=epsilon;
    }
    public static void main(String[] args) {
        double ilksinir=Double.parseDouble(args[0]);
        double sonsinir=Double.parseDouble(args[1]);
        String error=args[2];
        double epsil=Double.parseDouble(args[3]);
        Main object=new Main(ilksinir,sonsinir,error,epsil);
        Scanner input=new Scanner(System.in);
        int type;
            try {
                System.out.println("BISECTİON METHOD");
                System.out.printf("Sınırlar : [%f,%f] -- Error_Type:%s -- Epsylon_Value:%f",ilksinir,sonsinir,error,epsil);
                System.out.println();
                System.out.println("1. ==> f(x)= 3x − ex ");
                System.out.println("2. ==> f(x)= 2x + 3cosx − e^x = 0 ");
                System.out.println("3. ==> f(x)= x^2 − 4x + 4 − lnx ");
                System.out.println("4. ==> f(x)=  x + 1 − 2sinπx ");
                System.out.println("Select one of them");
                type=input.nextInt();
                object.bisection(type);
            } catch (Exception a) {
                System.out.println(a.getMessage());
            }

        System.out.println("Good Bye");
    }

    public double fonk(double deger,int fonkType){
        double result;
        switch (fonkType){
            case 1:result=3*deger - exp(deger);return result;
            case 2:result=2*deger + 3*cos(deger) -exp(deger);return result;
            case 3:result=deger*deger-4*deger + 4 - log(deger);return result;
            case 4:result=deger + 1 - 2*sin(Math.PI*deger);return result;
            default:System.out.println("ERROR-Dont matched function");
        }
        return 0.0;
    }

    public double bisection(int fonkType) throws Exception{
        double a,b,c=0,cOld=0,e,fa,fb,fc;
        Scanner input=new Scanner(System.in);
        a=getSinir();
        b=getSinir2();
        e=getEpsil();
        int i=1;
        if(fonk(a,fonkType)*fonk(b,fonkType)>0){
            System.out.printf("Fonk(a):%f",fonk(a,fonkType));
            System.out.println();
            System.out.printf("Fonk(b):%f",fonk(b,fonkType));
            System.out.println();
            Exception x=new Exception("Please enter a different initial or end"+"" +
                    ".There is no root");
            throw x;
        }
        else
        {
            while(abs(a-b)>=e){
                if(sinirIterasyon>100){
                    Exception x=new Exception("OVERFLOW ITER");
                    throw x;
                }
                c=(a+b)/2.0;
                fa=fonk(a,fonkType);
                fb=fonk(b,fonkType);
                fc=fonk(c,fonkType);
                System.out.printf("%d::>  a=%f      b=%f      pValue=%f      F(p)=%f      absError=%f     relError=%f",
                        i,a,b,c,fc,abs(c-cOld),abs(c-cOld)/abs(c));
                System.out.println();
                if(fc==0){
                    Exception x=new Exception("The root of the equation");
                    throw x;
                }
                if(getMyError().equals("DISTANCE_TO_ROOT")){
                    if(abs(fonk(c,fonkType))<getEpsil()){
                        Exception x=new Exception("DISTANCE_TO_ROOT");
                        throw x;
                    }
                }
                if(getMyError().equals("ABSOLUTE_ERROR")){
                    if(abs(c-cOld)<getEpsil()){
                        Exception x=new Exception("ABSOLUTE_ERROR");
                        throw x;
                    }
                }
                if(getMyError().equals("RELATIVE_ERROR")){
                    if(abs(c-cOld)/abs(c)<getEpsil()){
                        Exception x=new Exception("RELATIVE_ERROR");
                        throw x;
                    }
                }
                if(fa*fc>0){
                    a=c;
                }
                else if(fa*fc<0){
                    b=c;
                }
                i++;
                sinirIterasyon++;
                cOld=c;
            }
        }
        if(c==0.0)
            System.out.println("There is no root");
        System.out.print("The root of the equation is:");
        System.out.println(c);
        return c;
    }

    public double getSinir() {
        return sinir;
    }

    public void setSinir(double sinir) {
        this.sinir = sinir;
    }

    public String getMyError() {
        return myError;
    }

    public void setMyError(String myError) {
        this.myError = myError;
    }

    public double getEpsil() {
        return epsil;
    }

    public void setEpsil(double epsil) {
        this.epsil = epsil;
    }

    public double getSinir2() {
        return sinir2;
    }

    public void setSinir2(double sinir2) {
        this.sinir2 = sinir2;
    }
}
