:- dynamic(nos/1).


nos(0).


p :- estado_inicial(E0),
     back(E0, A),
     esc(A).


inc:- retract(nos(N)),
      N1 is N + 1,
      asserta(nos(N1)).


back(e([], A), A).

/*back(E, Sol):- sucessor(E, E1),
               ve_restricoes(E1),
               back(E1, Sol).*/


back(E, Sol):- sucessor(E, E1),
               inc,
               ve_restricoes(E1),
               forCheck(E1, E2),
               back(E2, Sol).


forCheck(e(Lni, [v(N, D, V)|Li]), e(Lnii, [v(N, D, V)|Li])):- corta(V, Lni, Lnii).


corta(_, [], []).

corta(V, [v(N, D, _)|Li], [v(N, D1, _)|Lii]):- delete(D, V, D1),
                                               corta(V, Li, Lii).


sucessor(e([v(N, D, V)|R], E), e(R, [v(N, D, V)|E])):- member(V, D).


pessoas(['Maria',
         'Manuel',
         'Madalena',
         'Joaquim',
         'Ana',
         'Julio',
         'Matilde',
         'Gabriel',
         'Filipe',
         'Miguel',
         'Joao',
         'Inacio']).


estado_inicial(e([v(c(1), L, _),
                  v(c(2), L, _),
                  v(c(3), L, _),
                  v(c(4), L, _),
                  v(c(5), L, _),
                  v(c(6), L, _),
                  v(c(7), L, _),
                  v(c(8), L, _),
                  v(c(9), L, _),
                  v(c(10), L, _),
                  v(c(11), L, _),
                  v(c(12), L, _)], [])):- pessoas(L).


restricoes([esq('Manuel', 'Maria'), 
            frente('Joaquim', 'Maria'),
            lado('Joaquim', 'Matilde'), 
            cabeceira('Gabriel')]).


ve_restricoes(e(_, Afect)):- \+ ((member(v(c(I), _, Vi), Afect),
                                  member(v(c(J), _, Vj), Afect),  
                                  I \= J),
                                 (Vi = Vj;
                                 restric(I, Vi, J, Vj);
                                 restric(I, Vi);
                                 restric(J, Vj))).


/*%4 pessoas
restric(I, X, J, Y):- restricoes(L),
                      member(frente(X, Y), L),
                      \+ ((I = 1, J = 3);
                          (I = 2, J = 4)).

restric(I, X, J, Y):- restricoes(L),
                      member(esq(X, Y), L),
                      \+ ((I is J + 1;
                          (I = 1, J = 4))).

restric(I, X, J, Y):- restricoes(L),
                      member(dir(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 4, J = 1))).

restric(I, X, J, Y):- restricoes(L),
                      member(lado(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 4, J = 1));
                          (I is J + 1;
                          (I = 1, J = 4))).

restric(I, X):- restricoes(L),
                member(cabeceira_esq(X), L),
                \+ ((I = 1)).

restric(I, X):- restricoes(L),
                member(cabeceira_dir(X), L),
                \+ ((I = 3)). 

restric(I, X):- restricoes(L),
                member(cabeceira(X), L),
                \+ ((I = 1;
                     I = 3)).


%6 pessoas
restric(I, X, J, Y):- restricoes(L),
                      member(frente(X, Y), L),
                      \+ ((I = 1, J = 4);
                          (I = 2, J = 6);
                          (I = 3, J = 5)).

restric(I, X, J, Y):- restricoes(L),
                      member(esq(X, Y), L),
                      \+ ((I is J + 1;
                          (I = 1, J = 6))).

restric(I, X, J, Y):- restricoes(L),
                      member(dir(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 6, J = 1))).

restric(I, X, J, Y):- restricoes(L),
                      member(lado(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 6, J = 1));
                          (I is J + 1;
                          (I = 1, J = 6))).

restric(I, X):- restricoes(L),
                member(cabeceira_esq(X), L),
                \+ ((I = 1)).

restric(I, X):- restricoes(L),
                member(cabeceira_dir(X), L),
                \+ ((I = 4)). 

restric(I, X):- restricoes(L),
                member(cabeceira(X), L),
                \+ ((I = 1;
                     I = 4)).


%8 pessoas
restric(I, X, J, Y):- restricoes(L),
                      member(frente(X, Y), L),
                      \+ ((I = 1, J = 5);
                          (I = 2, J = 8);
                          (I = 3, J = 7);
                          (I = 4, J = 6)).

restric(I, X, J, Y):- restricoes(L),
                      member(esq(X, Y), L),
                      \+ ((I is J + 1;
                          (I = 1, J = 8))).

restric(I, X, J, Y):- restricoes(L),
                      member(dir(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 8, J = 1))).

restric(I, X, J, Y):- restricoes(L),
                      member(lado(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 8, J = 1));
                          (I is J + 1;
                          (I = 1, J = 8))).

restric(I, X):- restricoes(L),
                member(cabeceira_esq(X), L),
                \+ ((I = 1)).

restric(I, X):- restricoes(L),
                member(cabeceira_dir(X), L),
                \+ ((I = 5)). 

restric(I, X):- restricoes(L),
                member(cabeceira(X), L),
                \+ ((I = 1;
                     I = 5)).*/


%12 pessoas
restric(I, X, J, Y):- restricoes(L),
                      member(frente(X, Y), L),
                      \+ ((I = 1, J = 7);
                          (I = 2, J = 12);
                          (I = 3, J = 11);
                          (I = 4, J = 10);
                          (I = 5, J = 9);
                          (I = 6, J = 8)).

restric(I, X, J, Y):- restricoes(L),
                      member(esq(X, Y), L),
                      \+ ((I is J + 1;
                          (I = 1, J = 12))).

restric(I, X, J, Y):- restricoes(L),
                      member(dir(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 12, J = 1))).

restric(I, X, J, Y):- restricoes(L),
                      member(lado(X, Y), L),
                      \+ ((I is J - 1;
                          (I = 12, J = 1));
                          (I is J + 1;
                          (I = 1, J = 12))).

restric(I, X):- restricoes(L),
                member(cabeceira_esq(X), L),
                \+ ((I = 1)).

restric(I, X):- restricoes(L),
                member(cabeceira_dir(X), L),
                \+ ((I = 7)). 

restric(I, X):- restricoes(L),
                member(cabeceira(X), L),
                \+ ((I = 1;
                     I = 7)).


esc(L):- sort(L, L1),
         nl,
         esc1(L1).


esc1([]).

esc1([v(c(C), _, V)|R]):- esc(C, V),
                          esc1(R).


esc(C, V):- write(C), write(' - '), write(V), nl.