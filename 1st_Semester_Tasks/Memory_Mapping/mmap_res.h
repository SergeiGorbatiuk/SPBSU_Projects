#pragma once

char** mmap_parse(char* MapStart, int lines_count, long int fsize);

void print_strings(char** strings,int lines_count);
