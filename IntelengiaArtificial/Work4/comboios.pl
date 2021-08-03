estado_inicial([
				objLocal(1, porto),
				objLocal(2, lisboa),
				objLocal(3, lisboa),
				objLocal(4, evora),
				objLocal(5, evora),
				combLocal(1, lisboa),
				combLocal(2, lisboa),
				linha(1, lisboa, porto),
				linha(1, porto, lisboa),
				linha(2, lisboa, evora),
				linha(2, evora, lisboa)
			   ]).

/*estado_inicial([
				objLocal(1, lisboa),
				objLocal(2, lisboa),
				objLocal(3, lisboa),
				objLocal(4, lisboa),
				objLocal(5, lisboa),
				combLocal(1, lisboa),
				combLocal(2, lisboa),
				linha(1, lisboa, porto),
				linha(1, porto, lisboa),
				linha(2, lisboa, evora),
				linha(2, evora, lisboa)
			   ]).*/

estado_final(F):- estado_final3(F).

estado_final1([
				objLocal(1, evora),
				objLocal(2, porto),
				objLocal(3, evora),
				objLocal(4, porto),
				objLocal(5, lisboa)
			  ]).

estado_final2([
				objLocal(1, lisboa),
				objLocal(2, lisboa),
				objLocal(3, lisboa),
				objLocal(4, lisboa),
				objLocal(5, lisboa)
			  ]).

estado_final3([
				objLocal(1, lisboa),
				objLocal(2, lisboa),
				objLocal(3, lisboa),
				objLocal(4, evora),
				objLocal(5, evora)
			  ]).



accao(desloca(C, L), [combLocal(C, L1), linha(C, L1, L)], [combLocal(C, L)], [combLocal(C, L1)]):- member(C, [1, 2]),
																								   member(L, [porto, evora, lisboa]),
																								   member(L1, [porto, evora, lisboa]),
																								   L \= L1.

accao(coloca(O, C), [combLocal(C, L), objLocal(O, L)], [objComb(O, C)], [objLocal(O, L)]):- member(C, [1, 2]),
																							member(L, [porto, evora, lisboa]),
																							member(O, [1, 2, 3, 4, 5]).

accao(tira(O, C), [objComb(O, C), combLocal(C, L)], [objLocal(O, L)], [objComb(O, C)]):- member(C, [1, 2]),
																						 member(L, [porto, evora, lisboa]),
																						 member(O, [1, 2, 3, 4, 5]).
