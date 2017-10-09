#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include <assert.h>
#include <sys/stat.h>
#include "mmap_res.h"
#include "sort_resources.h"

int main(int argc, char** argv){
    int descin;
    char *MapStart;
    long int fsize;
    struct stat FileInfo;
    
    assert(argc == 3);
    int lines_count = atoi(argv[1]);
    
    descin = open(argv[2], O_RDONLY);
    assert(descin >=0);
    
    fstat(descin, &FileInfo);
    fsize = FileInfo.st_size;
    
    MapStart = (char*)mmap(0, fsize, PROT_READ, MAP_PRIVATE, descin, 0);
    assert(MapStart != MAP_FAILED);
    
    char **string_pointers = mmap_parse(MapStart, lines_count, fsize);
    
    
    sort_insertion(string_pointers, lines_count);//////!!!!!!!
    
    print_strings(string_pointers, lines_count);
    
    munmap(MapStart, fsize);
    free(string_pointers);
    close(descin);
    
    
    
    return 0;
}


