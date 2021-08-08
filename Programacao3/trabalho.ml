(*dividir os tuplos oara obter o que quero*)
let dividir_tuplo_sequencia (caracter, sequencia) = sequencia;;
let dividir_tuplo_caracter (caracter, sequencia) = caracter;;

let percorrer lista =
	let rec rec_percorrer = function
	| [] -> []
	| head :: resto -> print_string head; rec_percorrer resto 
	in rec_percorrer lista;;

(*funções para printar*)
let print_list_int l = print_string "["; List.iter (fun x -> print_int x; print_string ";") l; print_string "]";;
let print_list_string l = print_string "["; List.iter (fun x -> print_string "'"; print_string x; print_string "'"; print_string ";") l; print_string "]";;
let print_lista l = print_string"[\n"; List.iter (fun x ->print_string "("; print_list_string (dividir_tuplo_caracter x); print_string ", "; print_list_int(dividir_tuplo_sequencia x); print_string ")\n") l; print_string "]\n";;
let print_final (a,b,c) = print_string"("; print_list_int a; print_string ", "; print_list_string b; print_string ", "; print_list_string c; print_string ")\n";;

(*colocar os tuplos que sao iguais na forma desejada*)
let final [a;b] = (dividir_tuplo_sequencia a, dividir_tuplo_caracter a, dividir_tuplo_caracter b);;

let retorna_ultimo_lista lista =
	let rec rec_retorna_ultimo_lista = function 
	| [] -> []
	| head :: [] -> head
	| head :: resto_lista -> rec_retorna_ultimo_lista resto_lista
	in rec_retorna_ultimo_lista lista;;

let rec retorna_primeira_lista = function
	| [] -> ([], [])
	| head :: resto_lista -> head;;

(*faz concatenação de dois tuplos, (a,b)(c,d) = (a+c, b+d)*)
(*o @ é a concatenação entre dois arrays*)
let juntar_tuplo (caracter1,sequencia1)(caracter2,sequencia2) = [(caracter1@caracter2), (sequencia1@sequencia2)];;

(*verifica se o head_anterior tem um tuplo igual com algumas condiçoes:
		1) se a sequencia é igual;
		2) se os caracteres sao diferentes;
		3) se cumprir as duas alineas de cima tem de comprir outras condiçoes (nota: tem de cumprir por esta ordem e caso cumpra uma delas entao guarda o novo tuplo):
			3.1) se nao existir ainda nehum tuplo igual entao guardamos
			3.2) se existir algum tuplo igual vamos verificar se o caracter do head_anterior é menor que o caracter do head
			3.3) se existir algum tuplo igual e a condiçao de cima nao se verifique entao vamos verificar se o comprimento dos caracteres sao iguais e se a sequencia é menor
		4) caso nao cumpra nenhuma condiçao a cima continua para o resto do array*)
let ambiguo_interior lista head_anterior valores_guardados =
	let  rec rec_ambiguo_interior head_anterior = function
	| [] -> valores_guardados
	| head :: resto_lista ->if dividir_tuplo_sequencia head_anterior = dividir_tuplo_sequencia head
								&& dividir_tuplo_caracter head_anterior <> dividir_tuplo_caracter head
									then	(if valores_guardados = []
												then [head_anterior; head]
											else if List.length(dividir_tuplo_caracter head_anterior)
														< List.length(dividir_tuplo_caracter(retorna_primeira_lista valores_guardados))
												then [head_anterior; head]
											else if List.length(dividir_tuplo_caracter head_anterior)
														= List.length(dividir_tuplo_caracter(retorna_primeira_lista valores_guardados))
													&& List.length(dividir_tuplo_sequencia head_anterior)
														< List.length(dividir_tuplo_sequencia(retorna_primeira_lista valores_guardados))
												then [head_anterior; head]
											else rec_ambiguo_interior head_anterior resto_lista)

							else rec_ambiguo_interior head_anterior resto_lista
	in rec_ambiguo_interior head_anterior lista;;

(*funcao que vai percorrendo a lista e a cada elemento manda para a funçao ambiguo_interior para ver se existe algum tuplo igual
manda tambem a lista para ser percorrida e um array vazio chamado valores guardados para guardar os valores que sao iguais ao tuplo enviado*)
let ambiguo_exterior lista =
	let rec rec_ambiguo_exterior valores_guardados = function
	| [] -> valores_guardados
	| head :: resto_lista ->rec_ambiguo_exterior (ambiguo_interior lista head valores_guardados) resto_lista					
	in rec_ambiguo_exterior [] lista;;

(*loop que faz a concatnação de um elemento da lista com todos os elementos da lista*)
let loop_interior lista head_anterior lista_valores_guardados =
	let rec rec_loop_interior = function
	| [] -> lista_valores_guardados
	| head :: resto_lista ->rec_loop_interior resto_lista@lista_valores_guardados@(juntar_tuplo head_anterior head)
	in rec_loop_interior lista;;

(*loop que envia o elemento que tem de ser concatnado*)
let loop_exterior lista lista_valores_guardados =
	let rec rec_loop_exterior valores_guardados = function
	| [] -> valores_guardados
	| head :: resto_lista ->rec_loop_exterior (valores_guardados@(loop_interior lista head lista_valores_guardados)) resto_lista
	in rec_loop_exterior lista_valores_guardados lista;;

(*concatena a lista com as concatnaçoes sem repetir tuplo iguais*)
(*List.mem verifica se head é elemento da lista*)
let sem_repetir lista valores_guardados = 
	let rec rec_sem_repetir guardar = function
	| [] -> guardar
	| head :: resto_lista ->if List.mem head lista
								then rec_sem_repetir guardar resto_lista
							else rec_sem_repetir (guardar@[head]) resto_lista 
	in rec_sem_repetir lista valores_guardados;;

(*funçao que verifica em toda a lista se existe ambiguidades e caso nao haja vai produzir novas concatnaçoes para verificar outra vez*)
let rec rec_verificacao lista =
	if ambiguo_exterior lista <> []
		then ambiguo_exterior lista
	else rec_verificacao (sem_repetir lista (loop_exterior lista []));;

(* Converte o caracter de um tuplo para string*)
let converte (l,arr)=([(Char.escaped l)],arr);;

(* Converte uma lista inteira de tuplos *)
let converterLista lt =
  let rec rec_converterLista arr = function
  | []->arr
  | x::xs -> rec_converterLista (arr@[(converte x)]) xs
  in rec_converterLista [] lt;;

(*main*)
let lista_A =
	[('a', [0;1;0;]);
	('c', [0;1]);
	('j', [0;0;1]);
	('l', [1;0]);
	('p', [0]);
	('s', [1]);
	('v', [1;0;1])];;

let lista_B = 
	[('a', [0;1;1;0]);
    ('b', [0;1;1;1;1;1]);
    ('c', [1;1;0;0;1;1;1;1]);
    ('f', [1;0;1;1;1;0]);
    ('j', [0;1;0]);
    ('l', [0;1;0;0]);
    ('r', [0;1;1;1;0])];;

print_string "\nLista de dados: \n";;
print_lista(converterLista lista_A);;
print_string"\n";;
print_string"Ambiguidade encontrada: ";;
print_final(final (rec_verificacao (converterLista lista_A)));;

print_string "\nLista de dados: \n";;
print_lista(converterLista lista_B);;
print_string"\n";;
print_string"Ambiguidade encontrada: ";;
print_final(final (rec_verificacao (converterLista lista_B)));;