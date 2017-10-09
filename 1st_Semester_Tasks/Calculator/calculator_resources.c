#include <stdlib.h>
#include <stdio.h>
#include "LinkedList.h"
#include "calculator_resources.h"
#include "stack.h"


int compare(LongNumber* a, LongNumber* b){
    if(a->length > b->length) return 1;
    else
        if(b->length > a->length) return -1;
        else{
            digit* p1 = a->first_digit;
            digit* p2 = b->first_digit;
            while(p1->digit_value == p2->digit_value && p1->next != NULL){
                p1 = p1->next;
                p2 = p2->next;
            }
            if(p1->digit_value > p2->digit_value) return 1;
            else{
                if(p1->digit_value < p2->digit_value) return -1;
                else return 0;
            }

        }
}

int isDigit(char c){
    if (0<= c-'0'&& 9>= c-'0') return 1;
    else return 0;
}

void get_number(LongNumber* lnum, char ch){
    char c = ch;
    while(isDigit(c)){
        c-='0';
        digit_add_end(lnum, c);
        c = getchar();
    }
    if(c == '\n') stack_add(lnum);
    else{
        printf("Unexpected symbols encountered: \"%c\". Repeat input, please\n", c);
        while(c != '\n') c = getchar();
    }
}

void print_number(LongNumber *num){
    if(num != NULL){
        if(num->sign) printf("-");
        digit *pointer = num->first_digit;
        while(pointer->next != NULL){
            printf("%d", pointer->digit_value);
            pointer = pointer->next;
        }
        printf("%d\n", pointer->digit_value);
    }
    else printf("Unable to print number\n");

}

char input_comprehension(){
    char c;
    c = getchar();
    if(isDigit(c)){
        LongNumber* lnum = longnum_init();
        lnum->sign = 0;
        get_number(lnum, c);
        return 'n';
    }
    else{
        char ch = c;
        if(ch == '-' && isDigit(c=getchar())){ //still a number
            LongNumber* lnum = longnum_init();
            lnum->sign = 1;
            get_number(lnum, c);
            return 'n';
        }
        else{
            switch(ch){
                case '+': return '+'; break;
                case '-': return '-'; break;
                case '*': return '*'; break;
                case '/': return '/'; break;
                case '=': return '='; break;
                default: return 'u';
           }
        }
    }
}

void cut_zeros(LongNumber *lnum){
    digit *pointer = lnum->first_digit;
    while(pointer->digit_value == 0 && pointer->next != NULL){
        digit *nxt = pointer->next;
        free(pointer);
        pointer = nxt;
        pointer->prev = NULL;
        lnum->length--;
    }
    lnum->first_digit = pointer;
}

void normalize(LongNumber *lnum){
    digit *p;
    p = lnum->last_digit;
    while(p->prev != NULL){
        p->prev->digit_value+=p->digit_value / 10;
        p->digit_value = p->digit_value % 10;
        p = p->prev;
    }
    if(p->digit_value > 9){
        digit_add_pre(lnum, p->digit_value /10);
        p->digit_value = p->digit_value % 10;
    }
}

LongNumber* add(LongNumber *a, LongNumber *b, char sign){//modifying addition
    digit *p1, *p2;

    p1 = a->last_digit;
    p2 = b->last_digit;

    char redun = 0, tempred;
    while(p2->prev != NULL){
        tempred = redun;
        redun = (p1->digit_value + p2->digit_value + tempred)/10;
        p1->digit_value = (p1->digit_value + p2->digit_value + tempred)%10;
        p1 = p1->prev;
        p2 = p2->prev;
        free(p2->next);
        p2->next = NULL;
    }
    tempred = redun;
    redun = (p1->digit_value + p2->digit_value + tempred)/10;
    p1->digit_value = (p1->digit_value + p2->digit_value + tempred)%10;
    free(p2);
    b->first_digit = NULL;

    if(p1->prev != NULL){
        p1 = p1->prev;
        while(p1->prev!=NULL){
            redun = (p1->digit_value + redun)/10;
            p1->digit_value = (p1->digit_value + redun)%10;
            p1 = p1->prev;
        }
        p1->digit_value += redun;
    }
    else p1->digit_value += redun*10;

    if(p1->digit_value > 9){
        digit_add_pre(a, 1);
        p1->digit_value = p1->digit_value % 10;
    }

    if(p1->digit_value == 0) a->sign = 0;
    else a->sign = sign;

    return a;
}

LongNumber* sub(LongNumber *a, LongNumber *b, char sign, int freenum){// modifying subtrahension
    digit *p1, *p2;

    p1 = a->last_digit;
    p2 = b->last_digit;

    while(p2->prev != NULL){
        p1->digit_value -= p2->digit_value;
        if(p1->digit_value < 0){
            p1->digit_value += 10;
            p1->prev->digit_value -= 1;
        }
        p1 = p1->prev;
        p2 = p2->prev;
    }

    p1->digit_value -= p2->digit_value;
    if(p1->prev != NULL){
        if(p1->digit_value < 0){
            p1->digit_value += 10;
            p1->prev->digit_value -= 1;
        }
        p1 = p1->prev;
        while(p1->prev != NULL){
            if(p1->digit_value < 0){
                p1->digit_value += 10;
                p1->prev->digit_value -= 1;
            }
        }
    }
    cut_zeros(a);

    if(p1->digit_value == 0) a->sign = 0;
    else a->sign = sign;

    return a;

}

LongNumber* mult(LongNumber *a, LongNumber *b, char sign){
    LongNumber *result = longnum_init();
    if(a->first_digit->digit_value == 0 || b->first_digit->digit_value == 0){
        digit_add_end(result, 0);
        return result;
    }

    digit *p1, *p2, *point, *rem;
    LongNumber *big, *extra;
    if(compare(a,b)>=0){
        p1 = a->last_digit;
        p2 = b->last_digit;
        big = a;
    }
    else{
        p1 = b->last_digit;
        p2 = a->last_digit;
        big = b;
    }

    while(p1->prev != NULL){
        digit_add_pre(result, p1->digit_value * p2->digit_value);
        p1 = p1->prev;
    }
    digit_add_pre(result, p1->digit_value * p2->digit_value);
    normalize(result);
    //for more digits in multiplicant
    rem = result->last_digit;
    if(p2->prev != NULL){
        while(p2->prev != NULL){
            point = rem->prev;
            p2 = p2->prev;
            p1 = big->last_digit;
            while(point->prev != NULL){
                point->digit_value += p1->digit_value * p2->digit_value;
                point = point->prev;
                p1 = p1->prev;
            }
            point->digit_value += p1->digit_value * p2->digit_value;
            if(p1->prev != NULL){
                digit_add_pre(result, p1->prev->digit_value * p2->digit_value);
            }
            normalize(result);
            rem = rem->prev;
        }

    }


    result->sign = sign;
    //destoying numbers, that are no more needed
    longnum_destroy(a);
    longnum_destroy(b);
    return result;
}

//special addition function for multiplication
void add_extra(LongNumber *result, LongNumber *extra){
    digit *p1 = result->last_digit;
    digit *p2 = extra->last_digit;

    char tempred, redun = 0;
    while (p1->prev != NULL) {
        tempred = redun;
        redun = (p1->digit_value + p2->digit_value + tempred)/10;
        p1->digit_value = (p1->digit_value + p2->digit_value + tempred)%10;
        p1 = p1->prev;
        p2 = p2->prev;
    }
    tempred = redun;
    redun = (p1->digit_value + p2->digit_value + tempred)/10;
    p1->digit_value = (p1->digit_value + p2->digit_value + tempred)%10;

    if(p2->prev != NULL){
        while(p2->prev != NULL){
            p2 = p2->prev;
            tempred = redun;
            redun = (p2->digit_value + tempred)/10;
            digit_add_pre(result, (p2->digit_value+tempred)%10);
        }
    }
    if(redun) digit_add_pre(result, redun);

}


LongNumber* divide(LongNumber *a, LongNumber *b, char sign){
    LongNumber *result = longnum_init();

    digit *p1, *point;
    if(b->first_digit->digit_value == 0){
        printf("Division by zero forbidden\n");
        stack_add(a);
        stack_add(b);
        return NULL;
    }
    if(compare(a,b)<0 || a->first_digit->digit_value == 0){
        digit_add_end(result, 0);
        stack_add(result);
        longnum_destroy(a);
        longnum_destroy(b);
        return result;

    }
    else{
        p1 = a->first_digit;
    }
    result->sign = sign;
    LongNumber *part = longnum_init();
    digit_add_end(part, p1->digit_value);
    char flag = 1;


    while(flag){
        char nol = 1;
        while((compare(b, part)>0)&&(p1->next != NULL)){
            p1 = p1->next;
            if(part->first_digit->digit_value != 0) digit_add_end(part, p1->digit_value);
            else part->first_digit->digit_value += p1->digit_value;
            if(result->last_digit != NULL){
                if(nol) nol = 0;
                else digit_add_end(result, 0);
            }
        }
        if(compare(b, part)>0){
            if(nol == 0) digit_add_end(result, 0);
            flag = 0;
            continue;
        }
        digit_add_end(result, sub_div(part, b));
        part = stack_take();
    }
    if(sign && part->first_digit->digit_value != 0) result->last_digit->digit_value += 1; //check for +1 to pos/neg

    normalize(result);
    //destoying numbers, that are no more needed
    longnum_destroy(a);
    longnum_destroy(b);
    longnum_destroy(part);
    return result;
}

int sub_div(LongNumber *divi, LongNumber *subt){
    digit *p1;
    digit *p2;
    int count = 0;
    LongNumber *result;
    while (compare(divi, subt)>=0) {
        divi = sub(divi, subt, 0, 0);
        count++;
    }
    /*while(compare(divi, subt)>=0){
        p1 = divi->last_digit;
        p2 = subt->last_digit;
        result = longnum_init();
        while(p2->prev != NULL){
            digit_add_pre(result, p1->digit_value - p2->digit_value);
            p1 = p1->prev;
            p2 = p2->prev;
        }
         digit_add_pre(result, p1->digit_value - p2->digit_value);
        if(p1->prev != NULL){
            p1 = p1->prev;
            while(p1->prev != NULL){
                digit_add_pre(result, p1->digit_value);
                p1 = p1->prev;
            }
            digit_add_pre(result, p1->digit_value);
        }
        p1 = result->last_digit;
        while(p1->prev != NULL){
            if(p1->digit_value < 0){
                p1->digit_value += 10;
                p1->prev->digit_value -= 1;
            }
            p1 = p1->prev;
        }
        cut_zeros(result);
        divi = result;
        result = NULL;
        count++;
    }*/
    stack_add(divi);
    return count;

}

int result_comprehension(char operand){
        switch (operand){
            case '+':{
                LongNumber *b = stack_take();
                if (b == NULL){
                    getchar();
                    return 0;
                }
                else{
                    LongNumber *a = stack_take();
                    if(a == NULL){
                        printf("Unable to do operation\n");
                        stack_add(b);
                        getchar();
                        return 0;
                    }
                    else{
                        if(compare(a, b)>=0){
                            if((a->sign+b->sign)%2 == 1) stack_add(sub(a, b, a->sign, 1));
                            else stack_add(add(a, b, a->sign));
                        }
                        else{
                            if((a->sign+b->sign)%2 == 1) stack_add(sub(b, a, b->sign, 1));
                            else stack_add(add(b, a, a->sign));
                        }
                        getchar();
                        return 0;
                    }
                }
            }

            case '-':{
                LongNumber *b = stack_take();
                if (b == NULL){
                    return 0;
                }
                else{
                    LongNumber *a = stack_take();
                    if(a == NULL){
                        printf("Unable to do operation\n");
                        stack_add(b);
                        return 0;
                    }
                    else{
                        if(compare(a, b)>=0){
                            if((a->sign+b->sign)%2 == 1) stack_add(add(a, b, a->sign));
                            else stack_add(sub(a, b, a->sign, 1));
                        }
                        else{
                            if((a->sign+b->sign)%2 == 1) stack_add(add(b, a, a->sign));
                            else stack_add(sub(b, a, (b->sign+1)%2, 1));
                        }
                        return 0;
                    }
                }
            }

            case '*':{
                LongNumber *b = stack_take();
                if (b == NULL){
                    getchar();
                    return 0;
                }
                else{
                    LongNumber *a = stack_take();
                    if(a == NULL){
                        printf("Unable to do operation\n");
                        stack_add(b);
                        getchar();
                        return 0;
                    }
                    else{
                        if((a->sign+b->sign)%2 == 1) stack_add(mult(a, b, 1));
                        else stack_add(mult(a, b, 0));
                        getchar();
                        return 0;
                    }
                }
            }

            case '/':{

                LongNumber *b = stack_take();
                if (b == NULL){
                    getchar();
                    return 0;
                }
                else{
                    LongNumber *a = stack_take();
                    if(a == NULL){
                        printf("Unable to do operation\n");
                        stack_add(b);
                        getchar();
                        return 0;
                    }
                    else{
                        LongNumber *result;
                        if((a->sign+b->sign)%2 == 1){
                            result = divide(a, b, 1);
                            if(result != NULL) stack_add(result);
                        }
                        else{
                            result = divide(a, b, 0);
                            if(result != NULL) stack_add(result);
                        }
                        getchar();
                        return 0;
                    }
                }
            }

            case '=':{
                print_number(stack_show());
                getchar();
                return 0;
            }

            case 'u':{
                return 1;
            }
        }
}
