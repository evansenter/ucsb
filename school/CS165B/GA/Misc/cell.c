void * age();
void * age=(void * arg);
void * chromosome();
void * column();
void * column=(void * arg);
void * fitness();
void * fitness=(void * arg);
void * generation();
void * generation=(void * arg);
void get_action(void * bitstring);
void insert_genome(void * new_genome);
void mitosis(void * mut_freq);
void print_cell();
void print_symbol();
void * row();
void * row=(void * arg);
void * signal();
void * signal=(void * arg);


void *
age() {
return self->age;
}

void *
age=(void * arg) {
return self->age = arg;
}

void *
chromosome() {
return self->chromosome;
}

void *
column() {
return self->column;
}

void *
column=(void * arg) {
return self->column = arg;
}

void *
fitness() {
return self->fitness;
}

void *
fitness=(void * arg) {
return self->fitness = arg;
}

void *
generation() {
return self->generation;
}

void *
generation=(void * arg) {
return self->generation = arg;
}

void
get_action(void * bitstring) {
get_response(self->chromosome, bitstring);
}

void
insert_genome(void * new_genome) {
insert_genes(self->chromosome, new_genome);
}

void
mitosis(void * mut_freq) {
replicate_genes(self->chromosome, mut_freq);
}

void
print_cell() {
print("Row: ", self->row, ", Column: ", self->column, ", Age: ", self->age, ", Fitness: ", self->fitness, ", Signal: ", self->signal, "\n\n");
print_chromosome(self->chromosome);
}

void
print_symbol() {
print("[", <<("", self->generation % 26 + 65), "] ");
}

void *
row() {
return self->row;
}

void *
row=(void * arg) {
return self->row = arg;
}

void *
signal() {
return self->signal;
}

void *
signal=(void * arg) {
return self->signal = arg;
}
