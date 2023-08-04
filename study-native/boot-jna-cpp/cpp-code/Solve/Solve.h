#pragma once

#include <iostream>
#include <string.h>

using namespace std;

struct Student
{
    char *name;
    int age;
};


extern "C" {
    /**
     *  a + b
    */
    int addNum(int a, int b);

    /**
     *  set student message
    */
    void setCallName(Student *student);
}