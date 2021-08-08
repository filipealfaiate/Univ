#ifndef COLORSQUARES_H
#define COLORSQUARES_H

int marcar(int sz, int tabuleiro[][sz], int x, int y);

int pontuacao(int num_quadrados);

void gravidade(int sz, int tabuleiro[][sz]);

void coluna(int sz, int tabuleiro[][sz]);

int jogada(int sz, int tabuleiro[][sz], int x, int y);

void mostrar(int sz, int tabuleiro[][sz]);

int fimJogo(int sz, int tabuleiro[][sz]);

void gerarTabuleiro(int sz, int tabuleiro[][sz]);

#endif