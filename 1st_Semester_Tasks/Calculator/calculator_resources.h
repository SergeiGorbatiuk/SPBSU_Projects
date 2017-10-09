#pragma once



struct num_digit{
        char digit_value;
        struct num_digit* prev;
        struct num_digit* next;

};
typedef struct num_digit digit;

struct long_num{
    digit* first_digit;
    digit* last_digit;
    char sign;
    unsigned long int length;
};

typedef struct long_num LongNumber;

int compare(LongNumber* a, LongNumber* b);
int isDigit(char c);
void get_number(LongNumber* lnum, char ch);
void print_number(LongNumber *num);
char input_comprehension();
void cut_zeros(LongNumber *lnum);
LongNumber* add(LongNumber *a, LongNumber *b, char sign);
LongNumber* sub(LongNumber *a, LongNumber *b, char sign, int freenum);
LongNumber* mult(LongNumber *a, LongNumber *b, char sign);
void add_extra(LongNumber *result, LongNumber *extra);
LongNumber* divide(LongNumber *a, LongNumber *b, char sign);
int sub_div(LongNumber *divi, LongNumber *subt);
int result_comprehension(char operand);
