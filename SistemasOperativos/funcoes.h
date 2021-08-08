#include "processo.c"
#include "queue.c"
void printInstante(int instante, queue_t *ready, queue_t *run, queue_t *blocked);
void fcfs(int n_processo, processo_t *arr_processos[]);
void rr(int n_processo, processo_t *arr_processos[]);