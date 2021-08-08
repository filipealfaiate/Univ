#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "processo.h"
#define SIZE 10


struct processo
{
	int PID;
	int temp_inicio;
	int *run;
	int *blocked;
};

processo_t *processo_new(int size)
{
	processo_t *processo_v = malloc(sizeof(processo_t));
	processo_v->run = malloc(size * sizeof(int));
	processo_v->blocked = malloc(size * sizeof(int));

	return processo_v;
}

processo_t *input_processo(int arr[], int ini, int fim)
{
	processo_t *processo_v = processo_new(SIZE);

	int p_run = 0, p_blocked = 0, count = 0;

	for (int i = ini; i <= fim; ++i)
	{
		if (arr[i] > 99)
			processo_v->PID = arr[ini];

		else if (count == 1)
			processo_v->temp_inicio = arr[ini + 1];

		else if (count%2 == 0)
		{
			processo_v->run[p_run] = arr[i];
			p_run++;
		}

		else if(count%2 != 0)
		{
			processo_v->blocked[p_blocked] = arr[i];
			p_blocked++;
		}

		count++;
	}
	return processo_v;
}

int posicao_PID(int PID, processo_t *processo_v[], int n_processo)
{
	for (int i = 0; i < n_processo; ++i)
	{
		if (processo_v[i]->PID == PID)
			return i;
	}
	return -1;
}

void atualizar_processo_blocked(int n_processo, processo_t *processo_v[], int size)
{
	for (int i = 0; i <= size; i++)
	{
		if (processo_v[n_processo]->blocked[i] > 1000)
			processo_v[n_processo]->blocked[i] = 0;
	}

	for (int i = 0; i <= size; ++i)
		processo_v[n_processo]->blocked[i] = processo_v[n_processo]->blocked[i + 1];
}

void atualizar_processo_run(int n_processo, processo_t *processo_v[], int size)
{
	for (int i = 0; i <= size; ++i)
	{
		if (processo_v[n_processo]->run[i] > 1000)
			processo_v[n_processo]->run[i] = 0;
	}

	for (int i = 0; i <= size; ++i)
		processo_v[n_processo]->run[i] = processo_v[n_processo]->run[i + 1];
}