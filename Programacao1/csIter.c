#include <stdio.h>
#include <stdlib.h>
#include "colorSquares.h"

int main(){



	int x, y, sz, pontuacao = 0;
    /*int tabuleiro[4][4] = {
        {1,2,3,2},
        {2,4,4,1},     \\serviu para testar o jogo com o exemplo do enunciado
        {1,3,4,2},
        {1,2,3,2}
    };*/

	printf("============= COLOR SQUARES =============\n\n\n");
	printf("Indique o tamanho do tabuleiro(Max=20): ");
	scanf("%d", &sz);
	printf("\n");
	printf("\n");
	printf("A dimensao do tabuleiro sera %d x %d.\n\n", sz, sz);
	printf("Em cada jogada tera que escolher um quadrado do tabuleiro.\n");
	printf("Quanto maior for o numero de quadrados do grupo do quadrado selecionado, maior sera a pontuacao(em cada jogada).\n\n\n");

	int tabuleiro[sz][sz];
	//cria um tabuleiro com a dimensão inserida pelo jogador, "sz"

	gerarTabuleiro(sz, tabuleiro);
	//chamada da função "void gerarTabuleiro(sz, tabuleiro)" que escolhe números aleatórios entre 1 e 4 para ocupar as posições do tabuleiro

	while(fimJogo(sz, tabuleiro) != 1) {
		mostrar(sz, tabuleiro);
		printf("(0,0) Ponto Inferior Esquerdo\n\n");
		printf("Indique a cordenada X: ");
		scanf("%d", &x);
		printf("Indique a cordenada Y: ");
		scanf("%d", &y);
		pontuacao += jogada(sz, tabuleiro, x, y);
	}

	printf("\n--= FIM DE JOGO =--\n\n");
	printf("Obteve uma pontuacao de %d pontos\n\n", pontuacao);

    return 0;
}
