// Evan Senter, CS123

#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("Provide only one argument, the file name.\n");
    return -1;
  }
  FILE *file_read = fopen(argv[1], "r");
  FILE *file_write = fopen(strcat(argv[1], ".out"), "w");
  char line[12];
  int chars_read, i, j;
  if (file_read == NULL || file_write == NULL) {
    printf("Was not able to properly prepare files.\n");
    return -2;
  }
  while ((chars_read = fread(line, sizeof(char), 12, file_read)) > 0) {
    char new_line[16];
    // For the first 4 chars, copy directly over
    for (i = 0; i < 4; i++) { new_line[i] = line[i]; }
    // Initialize the new line for 2 32 bit ints
    for (i = 4; i < 11; i++) { new_line[i] = 0; }
    // Copy over from old line treating as unsigned, switch endian-ness
    new_line[4 + 3] = line[4];
    new_line[4 + 2] = line[5];
    new_line[8 + 3] = line[6];
    new_line[8 + 2] = line[7];
    // Copy the 32 bit float over
    for (i = 8; i < 12; i++) { new_line[i + 4] = line[i]; }
    fwrite(new_line, sizeof(char), 16, file_write);
  }
  fclose(file_read);
  fclose(file_write);
  return 0;
}  
