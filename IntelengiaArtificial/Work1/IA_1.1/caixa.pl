%-------------------------estados--------------------------

estado_inicial(s(a(6, 1), c(5, 1))).

estado_final(s(a(_, _), c(0, 4))).

list_Xs([p(0, 2), p(1, 0), p(1, 2), p(1, 6), p(3, 3), p(4, 3), p(5, 3)]).


%-------------------------restriçoes--------------------------

safe_position(a(X, Y)):- list_Xs(L),
						 perimetro(p(X, Y)),
						 \+member(p(X, Y), L).

safe_position(c(X, Y)):- list_Xs(L),
						 perimetro(p(X, Y)),
						 \+ member(p(X, Y), L).


perimetro(p(X, Y)) :- X >= 0, X =< 6,
                      Y >= 0, Y =< 6.


%-------------------------operações--------------------------

%op(Estado_act,operador,Estado_seg,Custo)
op(s(a(Xa, Ya), c(Xc, Yc)) ,emp_esq, s(a(Xa, Za), c(Xc, Zc)) ,1):- Za is Ya-1,
																   Zc is Yc-1,
																   (Xc, Yc) = (Xa, Za),
																   safe_position(a(Xa, Za)),
																   safe_position(c(Xc, Zc)).

op(s(a(Xa, Ya), c(Xc, Yc)) ,move_esq, s(a(Xa, Za), c(Xc, Yc)) ,1):- Za is Ya-1,
																	(Xc, Yc) \= (Xa, Za),
																	safe_position(a(Xa, Za)). 


op(s(a(Xa, Ya), c(Xc, Yc)), emp_dir, s(a(Xa, Za), c(Xc, Zc)), 1):- Za is Ya+1,
																   Zc is Yc+1,
																   (Xc, Yc) = (Xa, Za),
																   safe_position(a(Xa, Za)),
																   safe_position(c(Xc, Zc)).

op(s(a(Xa, Ya), c(Xc, Yc)), move_dir, s(a(Xa, Za), c(Xc, Yc)), 1):- Za is Ya+1,
																    (Xc, Yc) \= (Xa, Za),
																    safe_position(a(Xa, Za)). 


op(s(a(Xa, Ya), c(Xc, Yc)), emp_sobe, s(a(Za, Ya), c(Zc, Yc)), 1):- Za is Xa-1,
																	Zc is Xc-1,
																	(Xc, Yc) = (Za, Ya),
																	safe_position(a(Za, Ya)),
																	safe_position(c(Zc, Yc)).

op(s(a(Xa, Ya), c(Xc, Yc)), move_sobe, s(a(Za, Ya), c(Xc, Yc)), 1):- Za is Xa-1,                               
																	 (Xc, Yc) \= (Za, Ya),
																	 safe_position(c(Za, Ya)).


op(s(a(Xa, Ya), c(Xc, Yc)), emp_desce, s(a(Za, Ya), c(Zc, Yc)), 1):- Za is Xa+1,
																	 Zc is Xc+1,
																	 (Xc, Yc) = (Za, Ya),
																	 safe_position(a(Za, Ya)),
																	 safe_position(c(Zc, Yc)).

op(s(a(Xa, Ya), c(Xc, Yc)), move_desce, s(a(Za, Ya), c(Xc, Yc)), 1):- Za is Xa+1,                               
																	  (Xc, Yc) \= (Za, Ya),
																	  safe_position(c(Za, Ya)).


%-------------------------heuristicas--------------------------

h(s(a(_, _), c(X, Y)), Val):- estado_final(s(a(_, _), c(Xf, Yf))),
				 			  modDif(X, Xf, Vi),
				 			  modDif(Y, Yf, Vj),
							  Val is (Vi+Vj).

/*h(s(a(_, _), c(X, Y)), Val):- estado_final(s(a(_, _), c(Xf, Yf))),
				 			  modDif(X, Xf, Vi),
				 			  modDif(Y, Yf, Vj),
				 			  max(V, Vi, Vj),
				 			  Val is V.*/


%--------------------------AUXILIAR--------------------------

modDif(I, J, D):- I > J, D is I-J.

modDif(I, J, D):- I =< J, D is J-I.


max(Vi, Vi, Vj):- Vi > Vj, !.

max(Vj, _, Vj).
