// Evan Senter, CS123

#include <stdio.h>
#include <string.h>
#include <pthread.h>

int read_data, write_data, live;
char line[12], new_line[16];

struct thread_data {
  FILE *file_read, *file_write;
};

void *put_data(void *thread_data) {
  struct thread_data *my_data = (struct thread_data *)thread_data;
  
  while (live) {
    if (write_data) {
      write_data = 0;
      fwrite(new_line, sizeof(char), 16, my_data -> file_write);
    }
  }
  pthread_exit(NULL);
}

void *transform_data(void *thread_data) {
  int i;
  while (live) {
    if (read_data) {
      read_data = 0;
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
      write_data = 1;
      while (write_data) {}
    }
  }
  pthread_exit(NULL);
}

void *get_data(void *thread_data) {
  struct thread_data *my_data = (struct thread_data *)thread_data;
  
  while (fread(line, sizeof(char), 12, my_data -> file_read) > 0) { 
    read_data = 1; 
    while (read_data) {}
  }
  
  live = 0;
  pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
  if (argc != 2) {
    printf("Provide only one argument, the file name.\n");
    return -1;
  }
  
  FILE *file_read = fopen(argv[1], "r");
  FILE *file_write = fopen(strcat(argv[1], ".out"), "w");
  pthread_t threads[3];
  struct thread_data initial_data;
  live = 1;
  read_data = 0;
  write_data = 0;
  
  if (file_read == NULL || file_write == NULL) {
    printf("Was not able to properly prepare files.\n");
    return -2;
  }
  
  initial_data.file_read = file_read;
  initial_data.file_write = file_write;
  pthread_create(&threads[0], NULL, get_data, (void *)&initial_data);
  pthread_create(&threads[1], NULL, transform_data, (void *)&initial_data);
  pthread_create(&threads[2], NULL, put_data, (void *)&initial_data);
  
  while (live) {}
  
  pthread_exit(NULL);
  fclose(file_read);
  fclose(file_write);
  return 0;
}  
