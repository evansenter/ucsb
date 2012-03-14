/*
 * This script takes two parameters, the 'from' and 'to' files. It then reads through the 'from'
 * file (Expected to be a file of integers [from map_scores.rb]) and writes the integers as chars
 * in a binary file for compression.
 */

#include <stdio.h>

int main(int argc, char *argv[]) {
	if (argc != 3) {
		printf("Please provide exactly 2 arguments, the file to read from and the file to write to.\n");
		return -1;
	}
	FILE *file_read = fopen(argv[1], "r");
	FILE *file_write = fopen(argv[2], "wb");
	int score;
	while (fscanf(file_read, "%d", &score) != EOF)
		fwrite(&score, sizeof(char), 1, file_write);
	fclose(file_read);
	fclose(file_write);
}
