#include "calculator_resources.h"
#include "LinkedList.h"
#include "stack.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>


int main()
{
    stack_init();
    char inp;

    inp = input_comprehension();
    while(1){
        while(inp == 'n'){
            inp = input_comprehension();
        }
        if(result_comprehension(inp)){
            stack_clear();
            exit(0);
        }
        inp = input_comprehension();
    }
    return 0;
}
