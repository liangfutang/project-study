#include "Solve.h"

int addNum(int a, int b) {
    int  c = a + b;
    cout <<  a << "+" << b << "=" <<  c << endl;
    return c;
}

void setCallName(Student *student) {
    student->age = 18;
    student->name = strdup("jack");
}