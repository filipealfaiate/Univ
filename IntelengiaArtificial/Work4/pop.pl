:- dynamic(n/1).

n(3).


plano_ini(p([s1-op(inicial, [], EstadoIni, []), s2-op(final, EstadoFin, [], [])], [m(s1, s2)], [])):- estado_inicial(EstadoIni),
                                                                                                      estado_final(EstadoFin).


plano(P):- plano_ini(Pi), pop(Pi, P2), lineariza(P2, P).


pop(Pi, P):- escolheCi(Pi, Si, Ci, Pj),
             !,
             escolheSk(Pj, Si, Ci, Pk),
             resolveAmeacas(Pk, Pl),
             consistentep(Pl),
             pop(Pl, P).

pop(P,P).


escolheCi(p(Passos, Ordem, Links), Si, Ci, p([Si-op(Nome, Cond1, Add, Del)|Passos1], Ordem, Links)):- retira(Si-op(Nome, Cond, Add, Del), Passos, Passos1),
                                                                                                      retira(Ci, Cond, Cond1).
                                                                                                      %write(escolheu(Si, Ci)),
                                                                                                      %nl.


escolheSk(p(Passos, Ordem, Links), S, C, p(Passos, [m(Sk, S)|Ordem], [link(Sk, S, C)|Links])):- member(Sk-op(_, _, Add, _), Passos),
                                                                                                member(C, Add).
                                                                                                %write(links([link(Sk, S, C)|Links])),
                                                                                                %nl.


escolheSk(p(Passos, Ordem, Links), S, C, p([Sk-op(N, Cond, Add, Del)|Passos], [m(Sk, S), m(Sk, s2), m(s1, Sk)|Ordem], [link(Sk, S, C)|Links])):- novo(Sk),
                                                                                                                                                 !,
                                                                                                                                                 length(Passos, M),
                                                                                                                                                 M < 12,
                                                                                                                                                 !,
                                                                                                                                                 accao(N, Cond, Add, Del),
                                                                                                                                                 member(C, Add).


resolveAmeacas(p(Passos, Ordem, Links), P):- member(link(S1, S2, C), Links),
                                             member(S3-op(_, _, _, Del), Passos),
                                             \+ member(S3, [S1, S2]),
                                             member(C, Del),
                                             ameaca(S1, S2, S3, Ordem),
                                             !,
                                             resolve(S1, S2, S3, Rest),
                                             write(ameaca(S1, S2, S3, C)), nl,
                                             resolveAmeacas(p(Passos, [Rest|Ordem], Links), P).

resolveAmeacas(P, P).


ameaca(S1, S2, S3, Ord):- consistente([m(S1, S3), m(S3, S2)|Ord]).


resolve(S1, _, S3, m(S3, S1)).

resolve(_, S2, S3, m(S2, S3)).


retira(X, [X|R], R).

retira(X, [Y|R], [Y|S]):- retira(X, R, S).


novo(O):- retract(n(I)),
          J is I + 1,
          asserta(n(J)),
          name(I, L),
          name(O, [115|L]),
          !.


consistentep(p(_, Ordem, _)):- consistente(Ordem).


consistente(A):-renomeia(A, [], B, _), ve_consistente(B).


renomeia([], N, [], N).

renomeia([m(A, B)|R], Nomes, [m(X, Y)|S], Nomesf):- nome(A, X, Nomes, Nomes1),
                                                    nome(B, Y, Nomes1, Nomes2),
                                                    renomeia(R, Nomes2, S, Nomesf).


nome(A, X, L, L):- member(n(X, A), L),
                   !.

nome(A, X, L, [n(X, A)|L]).


ve_consistente([]).

ve_consistente([m(A, B)|R]):- A #>= 0,
                              A #=< 30,
                              B #>=0,
                              B #=<30,
                              A #< B,
                              ve_consistente(R).


lineariza(p(Passos, Ordem, _Links), P):- renomeia(Ordem, [], OrdemVar, Nomes),
                                         length(Nomes, N),
                                         member(n(X, s2), Nomes),
                                         X #>= 0,
                                         X #=< N,
                                         ve_consistente(OrdemVar),
                                         variaveis(Nomes, Vars),
                                         fd_labelingff(Vars),
                                         sort(Nomes, Ord),
                                         plano_ord(Ord, Passos,P).


variaveis([], []).

variaveis([n(X, _)|R], [X|S]):- variaveis(R, S).


plano_ord([], _, []).

plano_ord([n(_, S)|R], Passos, [S-N|Passos2]):- member(S-op(N, _, _, _), Passos),
                                                plano_ord(R, Passos, Passos2).
