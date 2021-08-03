:- dynamic(fechado/1).
:- dynamic(maxNL/1).
:- dynamic(nos/1).


maxNL(0).
nos(0).


inc:- retract(nos(N)),
      N1 is N+1,
      asserta(nos(N1)).


actmax(N):- maxNL(N1), N1 >= N, !.
actmax(N):- retract(maxNL(_N1)),
            asserta(maxNL(N)).


pesquisa(Problema, Alg):- consult(Problema),
                          estado_inicial(S0),
                          pesquisa(Alg, [no(S0, [], [], 0, 0)], Solucao),
                          escreve_seq_solucao(Solucao),
                          retract(nos(Ns)),
                          retract(maxNL(NL)),
                          retractall(fechado(_)),
                          asserta(nos(0)),
                          asserta(maxNL(0)),
                          write(nos(visitados(Ns), lista(NL))).


pesquisa(it, Ln, Sol):- pesquisa_it(Ln, Sol, 1).

pesquisa(largura, Ln, Sol):- pesquisa_largura(Ln, Sol).

pesquisa(profundidade, Ln, Sol):- pesquisa_profundidade(Ln, Sol).


pesquisa_it(Ln, Sol, P):- retractall(fechado(_)), pesquisa_pLim(Ln, Sol, P).

pesquisa_it(Ln, Sol, P):- P1 is P+1,
                          pesquisa_it(Ln, Sol, P1).


pesquisa_pLim([no(E, Pai, Op, C, P)|_], no(E, Pai, Op, C, P), _):- estado_final(E), inc.

pesquisa_pLim([E|R], Sol, Pl):- inc,
                                asserta(fechado(E)),
                                expandePl(E, Lseg, Pl),
                                insere_fim(R, Lseg, Resto),
                                length(Resto, N),
                                actmax(N),
                                pesquisa_pLim(Resto, Sol, Pl).


pesquisa_largura([no(E, Pai, Op, C, P)|_], no(E, Pai, Op, C, P)):- estado_final(E), inc.

pesquisa_largura([E|R], Sol):- inc,
                               asserta(fechado(E)),
                               expande(E, Lseg),
                               insere_fim(Lseg, R, Resto),
                               length(Resto, N),
                               actmax(N),
                               pesquisa_largura(Resto, Sol).


pesquisa_profundidade([no(E, Pai, Op, C, P)|_],no(E, Pai, Op, C, P)):- estado_final(E), inc.

pesquisa_profundidade([E|R], Sol):- inc,
                                    asserta(fechado(E)),
                                    expande(E, Lseg), 
                                    insere_fim(R, Lseg, Resto),
                                    length(Resto, N),
                                    actmax(N),
                                    pesquisa_profundidade(Resto, Sol).


expande(no(E, Pai, Op, C, P), L):- findall(no(En, no(E, Pai, Op, C, P), Opn, Cnn, P1),
                                          (op(E, Opn, En, Cn),
                                              \+fechado(no(En, _, _, _, _)),
                                              P1 is P+1,
                                              Cnn is Cn+C),
                                          L).


expandePl(no(E, Pai, Op, C, P), [], Pl):- Pl =< P, !.

expandePl(no(E, Pai, Op, C, P), L, _):- findall(no(En, no(E, Pai, Op, C, P), Opn, Cnn, P1),
                                               (op(E, Opn, En, Cn),
                                                    \+ fechado(En),
                                                    P1 is P+1,
                                                    Cnn is Cn+C),
                                               L).


insere_fim([], L, L).

insere_fim(L, [], L).

insere_fim(R, [A|S], [A|L]):- insere_fim(R, S, L).


escreve_seq_solucao(no(E, Pai, Op, Custo, Prof)):- write(custo(Custo)),
                                                   nl,
                                                   write(profundidade(Prof)),
                                                   nl,
                                                   escreve_seq_accoes(no(E, Pai, Op, _, _)).


escreve_seq_accoes([]).

escreve_seq_accoes(no(E, Pai, Op, _, _)):- escreve_seq_accoes(Pai),
                                           write(e(Op, E)),
                                           nl.


esc(A):- write(A), nl.