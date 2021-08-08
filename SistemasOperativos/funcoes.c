#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "funcoes.h"
#define SIZE 10
#define QUATUM_FCFS 1000
#define QUATUM_RR 3

void printInstante(int instante, queue_t *ready, queue_t *run, queue_t *blocked)
{
	if (instante < 10)
	{
		printf(" %d   ready: ", instante); printAll(ready);

		if ((ready->posicao_final) == 3)
		{
			printf("   run: "); printAll(run);
		}

		else if ((ready->posicao_final) == 2)
		{
			printf("       run: "); printAll(run); 
		}

		else if ((ready->posicao_final) == 1)
		{
			printf("           run: "); printAll(run); 
		}

		else if(empty(ready))
		{
			printf("               run: "); printAll(run);
		}

		if (empty(run))
		{
			printf("       blocked: "); printAll(blocked);
		}

		else
		{
			printf("   blocked: "); printAll(blocked);
		}

		printf("\n");
	}

	else
	{
		printf("%d   ready: ", instante); printAll(ready);

		if ((ready->posicao_final) == 3)
		{
			printf("   run: "); printAll(run); 
		}
			
		else if ((ready->posicao_final) == 2)
		{
			printf("       run: "); printAll(run); 
		}

		else if ((ready->posicao_final) == 1)
		{
			printf("           run: "); printAll(run);
		}

		else if(empty(ready))
		{
			printf("               run: "); printAll(run); 
		}

		if (empty(run))
		{
			printf("       blocked: "); printAll(blocked);
		}

		else
		{
			printf("   blocked: "); printAll(blocked);
		}

		printf("\n");
	}
}

void fcfs(int n_processo, processo_t *arr_processos[])
{
	queue_t *run = queue_new(SIZE);
	queue_t *blocked = queue_new(SIZE);
	queue_t *ready = queue_new(SIZE);
	int quatum = -1;

	for (int instante = 0; !empty(run) || !empty(ready) || !empty(blocked) || instante == 0; instante++)
	{
		if (!empty(run) || !empty(blocked) || !empty(ready))
		{
			for (int pos = 0; pos < blocked->posicao_final; pos++)
			{
				if (arr_processos[posicao_PID(blocked->arr[pos], arr_processos, n_processo)]->blocked[0] == 0)
				{
					enqueue(ready, posicao(blocked, pos));
					atualizar_processo_blocked(posicao_PID(top(ready), arr_processos, n_processo), arr_processos, (ready->posicao_final - ready->posicao_inicial));
					pos--;
				}
			}

			if (!empty(run) && (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] == 0 || quatum == (QUATUM_FCFS - 1)))
			{
				if (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] != 0 && quatum == (QUATUM_FCFS - 1))
					enqueue(ready, dequeue(run));
				
				else if (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->blocked[0] != 0 && arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] == 0)
				{
					enqueue(blocked, dequeue(run));
					atualizar_processo_run(posicao_PID(top(blocked), arr_processos, n_processo), arr_processos, (blocked->posicao_final - blocked->posicao_inicial));
				}
				else
					dequeue(run);
			}

			for (int i = 0; i < n_processo; i++)
			{
				if (arr_processos[i]->temp_inicio == instante)
					enqueue(ready, arr_processos[i]->PID);
			}

			if (!empty(blocked))
			{
				for (int i = 0; i < blocked->posicao_final; i++)
					arr_processos[posicao_PID(blocked->arr[i], arr_processos, n_processo)]->blocked[0]--;
			}

			if (!empty(ready) && empty(run))
			{
				enqueue(run, dequeue(ready));
				arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;
				quatum = -1;
			}
		}

		else
		{
			for (int i = 0; i < n_processo; i++)
			{
				if (arr_processos[i]->temp_inicio == instante)
					enqueue(ready, arr_processos[i]->PID);
			}

			enqueue(run, dequeue(ready));
			arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;
			quatum = -1;
		}

		if (instante != 0 && quatum >= 0 && quatum < (QUATUM_FCFS - 1) && !empty(run))
			arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;

		if (!empty(run) || !empty(ready) || !empty(blocked))
			printInstante(instante, ready, run, blocked);

		quatum++;
	}
}

void rr(int n_processo, processo_t *arr_processos[])
{
	queue_t *run = queue_new(SIZE);
	queue_t *blocked = queue_new(SIZE);
	queue_t *ready = queue_new(SIZE);
	int quatum = -1;

	for (int instante = 0; !empty(run) || !empty(ready) || !empty(blocked) || instante == 0; instante++)
	{
		if (!empty(run) || !empty(blocked) || !empty(ready))
		{
			for (int pos = 0; pos < blocked->posicao_final; pos++)
			{
				if (arr_processos[posicao_PID(blocked->arr[pos], arr_processos, n_processo)]->blocked[0] == 0)
				{
					enqueue(ready, posicao(blocked, pos));
					atualizar_processo_blocked(posicao_PID(top(ready), arr_processos, n_processo), arr_processos, (ready->posicao_final - ready->posicao_inicial));
					pos--;
				}
			}

			if (!empty(run) && (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] == 0 || quatum == (QUATUM_RR - 1)))
			{
				if (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] != 0 && quatum == (QUATUM_RR - 1))
					enqueue(ready, dequeue(run));
				
				else if (arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->blocked[0] != 0 && arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0] == 0)
				{
					enqueue(blocked, dequeue(run));
					atualizar_processo_run(posicao_PID(top(blocked), arr_processos, n_processo), arr_processos, (blocked->posicao_final - blocked->posicao_inicial));
				}
				else
					dequeue(run);
			}

			for (int i = 0; i < n_processo; i++)
			{
				if (arr_processos[i]->temp_inicio == instante)
					enqueue(ready, arr_processos[i]->PID);
			}

			if (!empty(blocked))
			{
				for (int i = 0; i < blocked->posicao_final; i++)
					arr_processos[posicao_PID(blocked->arr[i], arr_processos, n_processo)]->blocked[0]--;
			}

			if (!empty(ready) && empty(run))
			{
				enqueue(run, dequeue(ready));
				arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;
				quatum = -1;
			}
		}

		else
		{
			for (int i = 0; i < n_processo; i++)
			{
				if (arr_processos[i]->temp_inicio == instante)
					enqueue(ready, arr_processos[i]->PID);
			}

			enqueue(run, dequeue(ready));
			arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;
			quatum = -1;
		}

		if (instante != 0 && quatum >= 0 && quatum < (QUATUM_RR - 1) && !empty(run))
			arr_processos[posicao_PID(top(run), arr_processos, n_processo)]->run[0]--;

		if (!empty(run) || !empty(ready) || !empty(blocked))
			printInstante(instante, ready, run, blocked);

		quatum++;
	}
}