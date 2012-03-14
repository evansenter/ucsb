// Evan Senter, CS123

#include <stdio.h>
#include <string.h>
#include <pthread.h>

int live;

struct thread_data {
  FILE *file_read, *file_write;
  int number_of_lines, number_of_threads;
  struct input_data *the_input_data;
  struct input_data *the_output_data;
  pthread_t *threads;
};

struct input_data {
  char line[12];
};

struct output_data {
  char line[16];
};

void *transform_data(void *thread_data) {
  struct thread_data *my_data = (struct thread_data *)thread_data;
  int count, i;
 
  for (count = 0; count < my_data -> number_of_lines; count++) {
    // For the first 4 chars, copy directly over
    for (i = 0; i < 4; i++) { my_data -> the_output_data[count].line[i] = my_data -> the_input_data[count].line[i]; }
    // Initialize the new line for 2 32 bit ints
    for (i = 4; i < 11; i++) { my_data -> the_output_data[count].line[i] = 0; }
    // Copy over from old line treating as unsigned, switch endian-ness
    my_data -> the_output_data[count].line[4 + 3] = my_data -> the_input_data[count].line[4];
    my_data -> the_output_data[count].line[4 + 2] = my_data -> the_input_data[count].line[5];
    my_data -> the_output_data[count].line[8 + 3] = my_data -> the_input_data[count].line[6];
    my_data -> the_output_data[count].line[8 + 2] = my_data -> the_input_data[count].line[7];
    // Copy the 32 bit float over
    for (i = 8; i < 12; i++) { my_data -> the_output_data[count].line[i + 4] = my_data -> the_input_data[count].line[i]; }
  }
  
  live = 0;
  pthread_exit(NULL);
}

void *master(void *thread_data) {
  struct thread_data *my_data = (struct thread_data *)thread_data;
  struct output_data the_output_data[my_data -> number_of_lines];
  my_data -> the_output_data = the_output_data;
  int i;

  for (i = 0; i < my_data -> number_of_threads; i++)
    pthread_create(&(my_data -> threads[i]), NULL, transform_data, (void *)&my_data);

  pthread_t backup;
  pthread_create(&backup, NULL, transform_data, (void *)&my_data);

  while (live) {}

  for (i = 0; i < my_data -> number_of_lines; i++) 
    fwrite(my_data -> the_output_data[i].line, sizeof(char), 16, my_data -> file_write);

  pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
  if (argc != 4) {
    printf("Provide three arguments, the file name, number of entries and number of threads.\n");
    return -1;
  }
  
  FILE *file_read = fopen(argv[1], "r");
  FILE *file_write = fopen(strcat(argv[1], ".out"), "w");
  int number_of_lines = atoi(argv[2]), number_of_threads = atoi(argv[3]), i;
  pthread_t master_thread, threads[number_of_threads];
  struct input_data the_input_data[number_of_lines];
  struct thread_data initial_data;
  live = 1;

  if (file_read == NULL || file_write == NULL) {
    printf("Was not able to properly prepare files.\n");
    return -2;
  }
  
  for (i = 0; i < number_of_lines; i++) 
    fread(the_input_data[i].line, sizeof(char), 12, file_read);
  
  initial_data.file_read = file_read;
  initial_data.file_write = file_write;
  initial_data.number_of_lines = number_of_lines;
  initial_data.number_of_threads = number_of_threads;
  initial_data.the_input_data = the_input_data;
  initial_data.threads = threads;

  pthread_create(&master_thread, NULL, master, (void *)&initial_data);
  
  while (live) {}

  pthread_exit(NULL);
  fclose(file_read);
  fclose(file_write);
  return 0;
}  
