#pragma once

void sort_bubble(char **strings_pointer,int strings_number);
void sort_insertion(char **strings_pointer,int strings_number);
void merge(char** spoint, int l, int m, int r);
void sort_merge(char** spoint, int l, int r);
void sort_quick(char** spoint, int first, int last);
void swap(char **rswp, int i, int j);
void free_memory(char **ptr, int length);
char** copy_array(char **arr_from, int length);
int compare(char* s1, char* s2);

