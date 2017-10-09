#include <stdlib.h>
#include "LinkedList.h"
#include "calculator_resources.h"


LongNumber* longnum_init(){
    LongNumber *lnum = (LongNumber*)malloc(sizeof(LongNumber));
    lnum->first_digit = NULL;
    lnum->last_digit = NULL;
    lnum->sign = 0;
    lnum->length = 0;
    return lnum;
}

void longnum_destroy(LongNumber *lnum){
    digit *current, *nxt;
    current = lnum->first_digit;
    while(current->next != NULL){
        nxt = current->next;
        free(current);
        current = nxt;
    }
    free(current);
    free(lnum);
    lnum = NULL;
}

void digit_add_end(LongNumber* lnum,  char c){
    digit* dig = (digit*)malloc(sizeof(digit));
    dig->digit_value = c;
    dig->next = NULL;
    if(lnum->length == 0){
        lnum->first_digit = dig;
        lnum->last_digit = dig;
        lnum->length +=1;
        dig->prev = NULL;
    }
    else{
        dig->prev = lnum->last_digit;
        lnum->last_digit = lnum->last_digit->next = dig;
        lnum->length+=1;
    }
}

void digit_add_pre(LongNumber *lnum, char c){
    digit* dig = (digit*)malloc(sizeof(digit));
    dig->digit_value = c;
    dig->prev = NULL;
    if(lnum->length ==0){
        lnum->first_digit = dig;
        lnum->last_digit = dig;
        lnum->length +=1;
        dig->next = NULL;
    }
    else{
        dig->next = lnum->first_digit;
        lnum->first_digit = lnum->first_digit->prev = dig;
        lnum->length +=1;
    }
}
