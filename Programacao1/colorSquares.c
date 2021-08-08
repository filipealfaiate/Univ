#include <stdio.h>
#include <stdlib.h>
#include "colorSquares.h"





void mostrar(int sz, int tabuleiro[][sz]){  //atualiza o tabuleiro a cada jogada
    int i, j;

    printf("\n");

    for(i = sz-1; i >= 0; i--) {
        for(j = 0; j < sz; j++){
            if(tabuleiro[i][j] != -1)               //se na posição em causa não
            {                                       //estiver nenhum número
                printf(" %d ", tabuleiro[i][j]);    //entre 1 e 4, vai "-" para
            } else {                                //essa posição
                printf(" - ");
            }
        }
        printf("\n");
    }

    printf("\n");
}

int marcar(int sz, int tabuleiro[][sz], int x, int y){

	int count;

    // Verifica os limites
    if(x >= 0 && y >= 0 && x < sz && y < sz){
        // Dentro dos limites do tabuleiro de jogo

        //Determina o tipo da peca e torna-a vazia
        int peca = tabuleiro[x][y];                        //x=linhas
        tabuleiro[x][y] = -1;                              //y=colunas
	    	count = 1;

        // Verifica se tem peça igual em baixo
        if(x > 0 && tabuleiro[x-1][y] == peca){
           	count += marcar(sz, tabuleiro, x-1, y);
        }

        // Verifica se tem peça do lado direito
        if(y < (sz-1) && tabuleiro[x][y+1] == peca){
            count += marcar(sz, tabuleiro, x, y+1);
        }

        // Verifica se tem peça no topo
        if(x < (sz-1) && tabuleiro[x+1][y] == peca) {
            count += marcar(sz, tabuleiro, x+1, y);
        }

        // Verifica se tem peça do lado esquerdo
        if(y > 0 && tabuleiro[x][y-1] == peca) {
            count += marcar(sz, tabuleiro, x, y-1);
        }

        return count;

    } else {
        return -1;
    }
}

void gravidade(int sz, int tabuleiro[][sz]){
    int i, j;

    // Percorrer coluna a coluna
    for(j = 0; j < sz; j++) {
        // Procura-se só até à penúltima linha, porque se a ultima linha
        // estiver vazia (-1) não acontece nada porque já estão no topo também
        for(i = 0; i < (sz-1); i++){
            // Verifica se a posição actual é vazia (-1) e se a próxima
            // não é vazia. Para evitar casos de serem as duas vazias e
            // depois ficar-se sempre no mesmo estado
            if(tabuleiro[i][j] == -1 && tabuleiro[i+1][j] != -1) {
                // Troca os valores nas linhas
                tabuleiro[i][j] = tabuleiro[i+1][j];
                tabuleiro[i+1][j] = -1;
                // Reinicializa-se a variável iteradora para começar
                // de novo a percorrer a coluna desde o início
                i = -1;
            }
        }
    }
}

void coluna(int sz, int tabuleiro[][sz]){
	int i, j;

	// Verifica se a primeira posição da coluna é vazia.
	// Uma vez que existe a função gravidade se a primeira posição da coluna
	// está vazia então toda a coluna está vazia
	for(j = 0; j < (sz-1); j++){
        if (tabuleiro[0][j] == -1){
            for(i = 0; i < sz; i++){
				tabuleiro[i][j] = tabuleiro[i][j+1];
				tabuleiro[i][j+1] = -1;
            }
            j=-1;
        }
	}
}

int pontuacao(int num_quadrados) {
	return (num_quadrados*(num_quadrados + 1)) / 2;
}

int jogada(int sz, int tabuleiro[][sz], int x, int y) {

	if(tabuleiro[y][x] == -1) {
		printf("Jogada Invalida\n");
		return 0;
	}

  int num_quadrados = marcar(sz, tabuleiro, y, x);
    gravidade(sz, tabuleiro);
    coluna(sz, tabuleiro);

    return pontuacao(num_quadrados);
}

int fimJogo(int sz, int tabuleiro[][sz]){
	int i, j;
	for(i = 0; i < sz; i++){
		for(j = 0; j < sz; j++){
			if(tabuleiro[i][j] != -1){        //tem de ter uma cor algures
				return 0;
			}
		}
	}
	return 1;
  //quando todo o tabuleiro estiver vazio, a função retorna 1
}

void gerarTabuleiro(int sz, int tabuleiro[][sz]){
  int i, j;
	for(i = 0; i < sz; i++){
		for(j = 0; j < sz; j++){
			tabuleiro[i][j] = (rand() % 4) + 1;
		}
	}
  //esta função gera os números aleatórios para o tabuleiro inicial
}
