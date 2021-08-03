estado_inicial(e(1, 2, 2, 5)).

maximo(7).

terminal(e(0, 0, 0, 0)).  


valor(E, -1, P):- terminal(E),
				  R is P mod 2,
				  R = 1.

valor(E, 1, P):- terminal(E),
				 R is P mod 2,
				 R=0.

  
op1(e(N1, N2, N3, N4), ret(1, N), e(N11, N2, N3, N4)):- numero(1, N),
														N11 is N1 - N,
														N11 >= 0.

op1(e(N1, N2, N3, N4), ret(2, N), e(N1, N22, N3, N4)):- numero(1, N),
														N22 is N2 - N,
														N22 >= 0.

op1(e(N1, N2, N3, N4), ret(3, N), e(N1, N2, N33, N4)):- numero(1, N),
														N33 is N3 - N,
														N33 >= 0.

op1(e(N1, N2, N3, N4), ret(4, N), e(N1, N2, N3, N44)):- numero(1, N),
														N44 is N4 - N,
														N44 >= 0.


 numero(N, N).
 numero(L, N1):- maximo(M),
 				 L < M,
 				 L1 is L + 1,
 				 numero(L1, N1).
  
