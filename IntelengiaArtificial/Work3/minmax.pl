%melhor move
/*
g(Jogo):- [Jogo],
		  estado_inicial(Ei),
		  minimax_decidir(Ei, Op, Ess),
		  write(Op),
		  nl,
		  write(Ess),
		  nl.*/


%joga o jogo

g(Jogo):- [Jogo],
		  estado_inicial(Ei),
		  agente_nim(Ei).


/*agente_amazon(Ei):- terminal(Ei),
					nl,
					write('jogador ganhou').

agente_amazon(Ei):- minimax_decidir(Ei, Op, Ess),
					write(Ess),
					nl,
					write(Op),
					nl,
					jogador(Ess).


jogador(E):- terminal(E),
			 write('pc ganhou').

jogador(E):- read(X),
			 read(Y),
			 read(A),
			 read(B),
			 op1(E, a(move(X, Y), Final)),
			 asserta(bloqueado((A, B))),
			 write(Final),
			 agente_amazon(Final).*/



agente_nim(Ei):- terminal(Ei),
				 write('pc ganhou').

agente_nim(Ei):- minimax_decidir(Ei, Op, Ess),
				 write(Op),
				 nl,
				 write(Ess),
				 nl,
				 jogador(Ess).

jogador(Op):- terminal(Op),
			  write('jogador ganhou').

jogador(Op):- read(X),
			  read(Y),
			  op1(Op, ret(X, Y), Final),
			  write(Final),
			  nl,
			  agente_nim(Final).


minimax_decidir(Ei, terminou, Ei):- terminal(Ei).

minimax_decidir(Ei, Opf, Ess):- findall(Es - Op, op1(Ei, Op, Es), L),
								findall(Vc - Op, (member(E - Op, L), minimax_valor(E, Vc, 1)), L1),
								escolhe_max(L1, Opf),
								member(Ess - Opf, L).


minimax_valor(Ei, Val, P):- terminal(Ei),
							valor(Ei, Val, P).

minimax_valor(Ei, Val, P):- findall(Es, op1(Ei, _, Es), L),
							P1 is P + 1,
							findall(Val1, (member(E, L), minimax_valor(E, Val1, P1)), V),
							seleciona_valor(V, P, Val).


seleciona_valor(V, P, Val):- X is P mod 2,
							 X = 0,
							 !,
							 maximo(V, Val).

seleciona_valor(V, _, Val):- minimo(V, Val).


maximo([A|R], Val):- maximo(R, A, Val).

maximo([], A, A).

maximo([A|R], X, Val):- A < X,
						!,
						maximo(R, X, Val).

maximo([A|R], _, Val):- maximo(R, A, Val).


escolhe_max([A|R], Val):- escolhe_max(R, A, Val).

escolhe_max([], _ - Op, Op).

escolhe_max([A - _|R], X - Op, Val):- A < X,
									  !,
									  escolhe_max(R, X - Op, Val).

escolhe_max([A|R], _, Val):- escolhe_max(R, A, Val).


minimo([A|R], Val):- minimo(R, A, Val).

minimo([], A, A).

minimo([A|R], X, Val):- A > X,
						!,
						minimo(R, X, Val).

minimo([A|R], _, Val):- minimo(R, A, Val).