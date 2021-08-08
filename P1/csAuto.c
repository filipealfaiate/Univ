#include <stdio.h>
#include <stdlib.h>
#include "colorSquares.h"

int main(){

	int i, j;
	int sz, pontuacao = 0, num_jogadas, cores;

    FILE *f = fopen("jogadas.txt", "r");

    // Verifica se é possível abrir o ficheiro
    if(f == NULL) {
        printf("Não foi possível abrir o ficheiro das jogadas");
        return 1;
    }

    // Lê do ficheiro o tamanho do tabuleiro
    fscanf(f, "%d", &sz);

    // Define o tabuleiro
    int tabuleiro[sz][sz];

    // Preenche o tabuleiro com as cores lidas do ficheiro
    for(i = (sz-1); i >= 0; i--) {
        fscanf(f, "%d", &cores);
        for(j = (sz-1); j >= 0 ; j--){
            tabuleiro[i][j] = (cores % 10);
            cores /= 10;
        }
    }

    // Lê do ficheiro o numero de jogadas
    fscanf(f, "%d", &num_jogadas);

    // Lê as jogadas do ficheiro
    // percorre o ficheiro e quando encontrar uma virgula passa
    // para a proxima jogada
    i = 0;
    char ch;
    int index = 0;
    int jogadas[num_jogadas][2];

    while(!feof(f)){
        ch = fgetc(f);
        if(ch >= '0' && atoi(&ch) < sz){
            jogadas[i][index] = atoi(&ch);
            index = 1;
        } else if(ch == ','){
            i = i + 1;
            index = 0;
        }
    }

	printf("\n============= COLOR SQUARES =============\n");
	printf("\n");

    for(i = 0; i < num_jogadas; i++){
        pontuacao += jogada(sz, tabuleiro, jogadas[i][0], jogadas[i][1]);
    }

	printf("Obteve uma pontuacao de %d pontos\n\n", pontuacao);

	fclose(f);	
  return 0;
}
