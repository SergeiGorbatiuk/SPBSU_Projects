#include <stdio.h>
#include <stdlib.h>
#include "calculator_resources.h"
#include "stack.h"
#include "LinkedList.h"

stack* stack_init(){
    stk = (stack*)malloc(sizeof(stack));
    stk->head = NULL;
}

void stack_clear(){
    node *nxt;
    while (stk->head != NULL) {
        nxt = stk->head->next;
        longnum_destroy(stk->head->number);
        free(stk->head);
        stk->head = nxt;
    }
    free(stk);
}

void stack_add(LongNumber* num){
    node *nadd = (node*)malloc(sizeof(node));
    nadd->number = num;
    if(stk->head == NULL){
        nadd->next = NULL;
        stk->head = nadd;
    }
    else{
        nadd->next = stk->head;
        stk->head = nadd;
    }
}

LongNumber* stack_show(){
    if(stk->head != NULL) return stk->head->number;
    else{
        printf("Stack is empty\n");
        return NULL;
    }
}

LongNumber* stack_take(){
    if(stk->head != NULL){
        LongNumber *tmp = stk->head->number;
        if(stk->head->next != NULL){
            node *nxt = stk->head->next;
            free(stk->head);
            stk->head = nxt;
            return tmp;
        }
        else{
            free(stk->head);
            stk->head = NULL;
            return tmp;
        }


    }
    else{
        printf("Not enough numbers in stack or stack is empty\n");
        return NULL;
    }
}
