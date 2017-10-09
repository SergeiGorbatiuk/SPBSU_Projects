#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "sort_resources.h"


void sort_bubble(char **strings_pointer,int strings_number){
    int i, j;
    for (i=0; i<strings_number; i++){
        for (j=i+1; j<strings_number; j++){
            if(strcmp(strings_pointer[i], strings_pointer[j])>0) swap(strings_pointer, i, j);
        }
    }
}

void sort_insertion(char **strings_pointer,int strings_number){
    int i,j;
    for (i=1; i<strings_number; i++){
        for(j=i;j>0 && strcmp(strings_pointer[j-1], strings_pointer[j])>0 ;j--){
            swap(strings_pointer, j-1, j);
        }
    }
}

void merge(char** spoint, int l, int m, int r)
{
    int i, j, k;
    int n1 = m - l + 1;
    int n2 =  r - m;

    char **half1 = (char**)malloc(sizeof(char*)*n1);
    char **half2 = (char**)malloc(sizeof(char*)*n2);
    
    int len;
    for (i = 0; i < n1; i++){
        len = (strlen(spoint[i+l])+1)*sizeof(char);
        half1[i] = (char*)malloc(sizeof(char)*len);
        memcpy(half1[i], spoint[i+l], len);
    } 
    for (j = 0; j < n2; j++){
        len = (strlen(spoint[m+j+1])+1)*sizeof(char);
        half2[j] = (char*)malloc(sizeof(char)*len);
        memcpy(half2[j], spoint[m+j+1], len);
    }

    i = 0; 
    j = 0; 
    k = l; 
    while (i < n1 && j < n2){
        if (strcmp(half1[i], half2[j])<0){
            len = sizeof(char)*(strlen(half1[i])+1);
            spoint[k] = (char*)realloc(spoint[k], len);
            memcpy(spoint[k], half1[i], len);
            i++;
        }
        else{
            len = sizeof(char)*(strlen(half2[j])+1);
            spoint[k] = (char*)realloc(spoint[k], len);
            memcpy(spoint[k], half2[j], len);
            j++;
        }
        k++;
    }
    while (i < n1){
        len = sizeof(char)*(strlen(half1[i])+1);
        spoint[k] = (char*)realloc(spoint[k], len);
        memcpy(spoint[k], half1[i], len);
        i++;
        k++;
    }

    while (j < n2){
        len = sizeof(char)*(strlen(half2[j])+1);
        spoint[k] = (char*)realloc(spoint[k], len);
        memcpy(spoint[k], half2[j], len);
        j++;
        k++;
    }
    
    for (i = 0; i < n1; i++){
        free(half1[i]);
        half1[i] = NULL;
    }
    for (j = 0; j < n2; j++){
        free(half2[j]);
        half2[j] = NULL;
    }
    free(half1);
    half1 = NULL;
    free(half2);
    half2 = NULL;
}

void sort_merge(char** spoint, int l, int r)
{
    if (l < r){
        int m = l+(r-l)/2;
        sort_merge(spoint, l, m);
        sort_merge(spoint, m+1, r);
        merge(spoint, l, m, r);
    }
}

void sort_quick(char** spoint, int first, int last)
{
    int i = first, j = last;
    char* mid = spoint[(first + last) / 2];

    do {
        while (compare(spoint[i], mid)<0){
           i++;
        }
        while (compare(spoint[j], mid)>0){
            j--;
        }
        if(i <= j) {
            if (compare(spoint[i], spoint[j])>0) swap(spoint, i, j);
            i++;
            j--;
        }
    } while (i <= j);

    if (i < last)
        sort_quick(spoint, i, last);
    if (first < j)
        sort_quick(spoint, first, j);
}


void swap(char **rswp, int i, int j){
    char* temp = rswp[i];
    rswp[i]=rswp[j];
    rswp[j]=temp;
}

void free_memory(char** ptr, int length){
    int i;
    for (i=0; i<length; i++){
        free(ptr[i]);
        ptr[i] = NULL;
    }
    free(ptr);
    ptr = NULL;
}

char** copy_array(char **arr_from, int length){
    char **arr_to= (char**)malloc(sizeof(char*)*length);
    int i;
    for(i=0; i<length; i++){
        int len = strlen(arr_from[i])+1;
        arr_to[i] = (char*)malloc(sizeof(char)*len);
        memcpy(arr_to[i], arr_from[i], len);
    }
    return arr_to;
}

int compare(char* s1, char* s2){
    int i=0;
    while(s1[i]!='\n' && s2[i]!='\n'){
        if(s1[i] < s2[i]) return -1;
        if(s1[i] > s2[i]) return 1;
        i++;
    }
    if (s1[i] == '\n') return -1;
    if (s2[i] == '\n') return 1;
    else return 0;
}

































