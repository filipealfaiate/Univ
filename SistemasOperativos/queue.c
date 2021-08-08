#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "queue.h"

struct queue
{
	int size;
	int *arr;
	int posicao_inicial;
	int posicao_final;
};

queue_t *queue_new(int size)
{
	queue_t *queue_v = malloc(sizeof(queue_t));

	queue_v->size = size;
	queue_v->arr = malloc(queue_v->size * sizeof(int));
	
	queue_v->posicao_final = 0;
	queue_v->posicao_inicial = 0;

	return queue_v;
}

void enqueue(queue_t *queue_v, int elemento)
{
	if (!full(queue_v))
	{
		queue_v->arr[queue_v->posicao_final] = elemento;
		queue_v->posicao_final++;
	}
}

int dequeue(queue_t *queue_v)
{
	if (!empty(queue_v))
	{
		int aux = queue_v->arr[queue_v->posicao_inicial];

		for (int i = 0; i < queue_v->posicao_final; ++i)
			queue_v->arr[i] = queue_v->arr[i +1];

		queue_v->posicao_final--;
		return aux;
	}
	return -1;
}

bool empty(queue_t *queue_v)
{
	return queue_v->posicao_inicial == queue_v->posicao_final;
}

int top(queue_t *queue_v)
{
	return queue_v->arr[queue_v->posicao_final - 1];
}

bool full(queue_t *queue_v)
{
	return queue_v->size == queue_v->posicao_final;
}

void printAll(queue_t *queue_v)
{
	for (int i = 0; i < queue_v->posicao_final; ++i)
		printf("%d ", queue_v->arr[i]);
}

int posicao(queue_t *queue_v, int pos)
{
	int aux = queue_v->arr[pos];

	for (int i = pos; i < queue_v->posicao_final; ++i)
		queue_v->arr[i] = queue_v->arr[i + 1];

	queue_v->posicao_final--;
	return aux;
}