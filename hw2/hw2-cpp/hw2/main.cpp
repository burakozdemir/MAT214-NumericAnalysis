#include <iostream>
#include <cstring>

int GaussianElim(FILE* inp);
int JacobiMethod(FILE* inp);

using namespace std;

int main(int argc, char *argv[]) {
    bool isGesp = true;
    FILE *inp = NULL;
    if(argc == 3){
        inp = fopen(argv[2],"r");
    }
    else if(argc == 5){
        if(strcmp(argv[4],"JCB"))
            isGesp = false;
        inp = fopen(argv[2],"r");
    }
    else{
        cout << "USAGE : " << endl;
        return 0;
    }


    return 0;
}

int GaussianElim(FILE* inp){

}
int JacobiMethod(FILE* inp);
