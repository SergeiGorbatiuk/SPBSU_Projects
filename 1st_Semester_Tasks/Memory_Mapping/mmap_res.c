#include "mmap_res.h"
#include <stdlib.h>
#include <stdio.h>

char** mmap_parse(char* MapStart, int lines_count, long int fsize){
    char **strings = (char**)malloc(sizeof(char*)*lines_count);
    strings[0] = MapStart;
    long int i;
    int p=1;
    for(i = 0; i<fsize && p<lines_count; i++){
        if(MapStart[i] == '\n'){
            strings[p] = &MapStart[i+1];
            p++;
        }
    }
    //printf("%d\n", p);
    return strings;
}

void print_strings(char** strings,int lines_count){
    int i, j;
    for(i=0; i<lines_count; i++){
        j=0;
        while(strings[i][j]!='\n'){
            printf("%c", strings[i][j]);
            j++;
        }
        printf("%c", strings[i][j]);
    }
}
