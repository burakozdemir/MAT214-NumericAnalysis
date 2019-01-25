/*
 *
 * */

#include <iostream>
#include <string.h>
#include <fstream>
#include <sstream>
#include <algorithm>

using namespace std;

struct equations{
    double matrix[100][100];
    double sagMatrix[100];
    double resultMatrix[100];
    int toplamUzunluk;
    int maxIter=20;
    double tolerance = 0.0001;
};

void readEquations(string txt,equations *matrix);
void GESP(struct equations *matrix);
void Jacobis(struct equations *matrix);
void showResult(equations matrix);
/*
 * Eşitlikler sadece
 * */

int main(int argc,char* argv[]) {
    if(argc!=5) {
        cout << "Usage: ./solver -i system.txt -m GESP \n";
    }
    string mode= argv[4];
    string input = argv[2];

    equations matrixes;

    readEquations(input,&matrixes);

    try
    {
        if(mode=="GESP")
            GESP(&matrixes);
        if(mode=="JCB")
           Jacobis(&matrixes);
        else
            cout<<"wrong mode for solving equation.Mode : "<<mode<<endl;
    }catch(string& a){
        cout<<a<<endl;
    }

}


void GESP(struct equations *matrix){
    int pArray[matrix->toplamUzunluk];
    double sVector[matrix->toplamUzunluk];

    //sVector filling
    for (int i = 0; i < matrix->toplamUzunluk; ++i) {
        sVector[i]=max_element(matrix->matrix[i],matrix->matrix[i]);
    }

}
void Jacobis(struct equations *matrix){

}
void readEquations(string txt,equations *matrix){
    string line;
    ifstream file;
    int count,row=0,uzunluk=0;

    file.open(txt);
    if(file.is_open()){
        while ( file>>line )
        {
            count=0;
            istringstream iss(line);
            string token;
            while (getline(iss, token, ','))
            {
                matrix->matrix[row][count]=atof(token.c_str());
                count++;
                uzunluk++;
            }
            row++;
        }
        matrix->toplamUzunluk=uzunluk;
        file.close();
    }
    else
        throw "Couldn't file";
}