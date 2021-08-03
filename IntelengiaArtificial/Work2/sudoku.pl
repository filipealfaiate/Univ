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
               \+ (forCheck(E1, E2)),
               back(E2, Sol).


forCheck(e(Lni, [v(N, D, V)|Li]), e(Lnii, [v(N, D, V)|Li])):- corta(V, Lni, Lnii).


corta(_, [], []).

corta(V, [v(Ni, D, _)|Li], [v(Nj, D1, _)|Lii]):- restricoes(Ni, Nj),
                                                 delete(D, V, D1),
                                                 corta(V, Li, Lii).


sucessor(e([v(N, D, V)|R], E), e(R, [v(N, D, V)|E])):- member(V, D).


/*estado_inicial(e([v(c(1, 1), D, _),
                  v(c(1, 2), D, _),
                  v(c(1, 3), D, _),
                  v(c(2, 1), D, _),
                  v(c(2, 2), D, _),
                  v(c(2, 3), D, _),
                  v(c(3, 1), D, _),
                  v(c(3, 2), D, _),
                  v(c(3, 3), D, _)], [])):- D = [1, 2, 3, 4, 5, 6, 7, 8, 9].*/

estado_inicial(e([v(c(1, 1), D, _),
                  v(c(1, 3), D, _),
                  v(c(1, 4), D, _),
                  v(c(1, 5), D, _),
                  v(c(1, 7), D, _),
                  v(c(2, 1), D, _),
                  v(c(2, 2), D, _),
                  v(c(2, 3), D, _),
                  v(c(2, 5), D, _),
                  v(c(2, 7), D, _),
                  v(c(2, 8), D, _),
                  v(c(2, 9), D, _),
                  v(c(3, 2), D, _),
                  v(c(3, 3), D, _),
                  v(c(3, 4), D, _),
                  v(c(3, 5), D, _),
                  v(c(3, 6), D, _),
                  v(c(3, 8), D, _),
                  v(c(4, 1), D, _),
                  v(c(4, 2), D, _),
                  v(c(4, 3), D, _),
                  v(c(4, 4), D, _),
                  v(c(4, 5), D, _),
                  v(c(4, 7), D, _),
                  v(c(4, 8), D, _),
                  v(c(4, 9), D, _),
                  v(c(5, 1), D, _),
                  v(c(5, 2), D, _),
                  v(c(5, 3), D, _),
                  v(c(5, 4), D, _),
                  v(c(5, 7), D, _),
                  v(c(6, 2), D, _),
                  v(c(6, 3), D, _),
                  v(c(6, 5), D, _),
                  v(c(6, 6), D, _),
                  v(c(6, 7), D, _),
                  v(c(6, 8), D, _),
                  v(c(6, 9), D, _),
                  v(c(7, 1), D, _),
                  v(c(7, 2), D, _),
                  v(c(7, 3), D, _),
                  v(c(7, 5), D, _),
                  v(c(7, 6), D, _),
                  v(c(7, 7), D, _),
                  v(c(7, 8), D, _),
                  v(c(7, 9), D, _),
                  v(c(8, 3), D, _),
                  v(c(8, 4), D, _),
                  v(c(8, 6), D, _),
                  v(c(8, 7), D, _),
                  v(c(8, 9), D, _),
                  v(c(9, 1), D, _),
                  v(c(9, 2), D, _),
                  v(c(9, 4), D, _),
                  v(c(9, 5), D, _),
                  v(c(9, 7), D, _),
                  v(c(9, 8), D, _),
                  v(c(9, 9), D, _)],
                  
                 [v(c(1, 2), D, 1),
                  v(c(1, 6), D, 8),
                  v(c(1, 8), D, 7),
                  v(c(1, 9), D, 3),
                  v(c(2, 4), D, 5),
                  v(c(2, 6), D, 9),
                  v(c(3, 1), D, 7),
                  v(c(3, 7), D, 9),
                  v(c(3, 9), D, 4),
                  v(c(4, 6), D, 4),
                  v(c(5, 5), D, 3),
                  v(c(5, 6), D, 5),
                  v(c(5, 8), D, 1),
                  v(c(5, 9), D, 8),
                  v(c(6, 1), D, 8),
                  v(c(6, 4), D, 9),
                  v(c(7, 4), D, 7),
                  v(c(8, 1), D, 2),
                  v(c(8, 2), D, 6),
                  v(c(8, 5), D, 4),
                  v(c(8, 8), D, 3),
                  v(c(9, 3), D, 5),
                  v(c(9, 6), D, 3)])):- D = [1, 2, 3, 4, 5, 6, 7, 8, 9].


ve_restricoes(e(NAfect, Afect)):- \+ (member(v(c(I, J), _, Vi), Afect),
                                      member(v(c(X, Y), _, Vj), Afect),
                                      (I \= X;
                                       J \= Y),
                                      Vi = Vj,
                                      restricoes((I, J), (X, Y))).
            

restricoes((L, C), (X, Y)):- L = X;
                             C = Y;
                             (quadrante((L, C), (Qxi, Qyi)),
                              quadrante((X, Y), (Qxj, Qyj)),
                              Qxi = Qxj,
                              Qyi = Qyj).


quadrante((X, Y), (Qx, Qy)):- Qx is (X - 1) div 3,
                              Qy is (Y - 1) div 3.


esc(L):- sort(L, L1),
         nl,
         esc1(L1).


esc1([]).

esc1([v(c(_, Y), _, V)|R]):- esc(Y, V),
                             esc1(R).


esc(Y, V):- write(V), (Y = 9, nl; write(' ')).