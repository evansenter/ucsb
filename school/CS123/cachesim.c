// Evan Senter
#include "cache.c"
#include <stdio.h>

int log2(int i) {
  int power = 1;
  while (i > 2) {
    i /= 2;
    power++;
  }
  return power;
}

int main(int argc, char *argv[]) {
  if (argc != 4) {
    printf("# Please provide exactly 3 arguments, the cache size, line size, and input file name.\n");
    return -1;
  } else {
    // Initialization
    int count, i, address, cache_size = atoi(argv[1]), line_size = atoi(argv[2]), touches = 0, misses = 0;
    FILE *file_read = fopen(argv[3], "r");
    struct cache_set cache[cache_size];
    printf("# Executable: %s, Cache size: %d, Block size: %d, Input file: %s\n", argv[0], cache_size, line_size, argv[3]);
    for (i = 0; i < cache_size; i++) cache[i].valid = 0;
    // Loop
    while (fscanf(file_read, "%d\n", &address) != EOF) {
      int address_copy = address, physical_address, tag;
      // Shift right 2 for the byte offset (Assuming 32 bit) plus ceiling(log2(line_size)) for the block offset
      address >>= 2 + log2(line_size);
      // Mask the bits higher than the cache_size and bitwise and for the physical address
      physical_address = address & cache_size - 1;
      // Shift the ceiling(log2(cache_size)) to retrieve the tag
      tag = address >>= log2(cache_size);
      // Comparison
      if (physical_address < cache_size) {
	touches++;
	if (!(cache[physical_address].valid && cache[physical_address].tag == tag)) {
	  ++misses;
	  cache[physical_address].valid = 1;
	  cache[physical_address].tag = tag;
	}
      } else {
	printf("# The physical address %d obtained for address %d is greater than the cache size, %d.\n", physical_address, address_copy, tag);
      }
    }
    printf("# %d touches and %d misses, miss rate of %f\n", touches, misses, misses / (double)touches);
  }
}
