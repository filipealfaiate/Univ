#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "funcoes.c"
#define SIZE_ARR 100
#define SIZE_PROCESSO 20
#define QUATUM_FCFS 1000
#define QUATUM_RR 3
#define FICHEIRO_1 "input1.txt"
#define FICHEIRO_2 "input2.txt"
#define FICHEIRO_3 "input3.txt"

int main()
{
	while(true)
	{
		int funcao;
		printf("\nQual tipo de escalonamento quer usar?\n 1 - FCFS;\n 2- RR;\nNota: utilize outro numero para sair;\n");
		scanf("%d", &funcao);
		if (funcao != 1 && funcao != 2)
			break;

		int ficheiro;
		FILE *fp;

		printf("\nQual o ficheiro que quer?\n 1 - ficheiro 1;\n 2 - ficheiro 2;\n 3 - ficheiro 3;\n");
		scanf("%d", &ficheiro);
		if (ficheiro == 1)
			fp = fopen(FICHEIRO_1, "r");

		else if (ficheiro == 2)
			fp = fopen(FICHEIRO_2, "r");
		
		else if (ficheiro == 3)
			fp = fopen(FICHEIRO_3, "r");

		else
		{
			printf("escolha nao designada!\n");
			break;
		}
		printf("\n");

		
		int inteiro;
		int arr[SIZE_ARR];
		int comprimento = 0;

		while(fscanf(fp, "%d", &inteiro) != EOF)
		{
			arr[comprimento] = inteiro;
			comprimento++;
		}

		processo_t *arr_processos[SIZE_PROCESSO];
		int ini = 0;
		int n_processo = 0;

		for (int j = 0; j <= comprimento; ++j)
		{
			if(arr[j + 1] >= 100 || j == comprimento)
			{
				arr_processos[n_processo] = input_processo(arr, ini, j);
				ini = j + 1;
				n_processo++;
			}
		}
	
		if (funcao == 1)
			fcfs(n_processo, arr_processos);

		else
			rr(n_processo, arr_processos);

		fclose(fp);
	}

	return 0;	
}