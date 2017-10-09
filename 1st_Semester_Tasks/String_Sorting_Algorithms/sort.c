#include <stdlib.h>
#include <stdio.h>
#include "sort_resources.h"

int main(int argc, char** argv)
{
    int strings_number=atoi(argv[1]);
    
    printf("Reading file...");
    char** string_pointers = read_file(argv[2], strings_number);
    printf("done\n");
    printf("Choose a manner of sort (number):\n");
    printf("(1) Bubble sort\n");
    printf("(2) Insertion sort\n");
    printf("(3) Merge sort\n");
    printf("(4) Quick sort\n");
    int i;
    scanf("%d", &i);
    switch (i){
        case 1:{sort_bubble(string_pointers, strings_number); break;}
        case 2:{sort_insertion(string_pointers, strings_number); break;}
        case 3:{
        sort_merge(string_pointers, 0, strings_number-1);
        break;
        }
        case 4:{sort_quick(string_pointers, 0, strings_number-1); break;}
        default:{sort_quick(string_pointers, 0, strings_number-1);}
    }
    
    for (i=0; i<strings_number; i++){
        printf("%s", string_pointers[i]);
    }
    free_memory(string_pointers, strings_number);
    
    return 0;
}
