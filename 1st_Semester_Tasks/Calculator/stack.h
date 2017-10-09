#pragma once

#include "calculator_resources.h"


struct stk_node{
    LongNumber *number;
    struct stk_node *next;
};

typedef struct stk_node node;

struct stack_impl{
    node *head;
};

typedef struct stack_impl stack;

stack* stk;
/////////
stack* stack_init();
void stack_clear();
void stack_add(LongNumber* num);
LongNumber* stack_show();
LongNumber* stack_take();
