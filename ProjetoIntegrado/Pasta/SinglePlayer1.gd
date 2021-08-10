extends Node

func _on_Button_pressed():
	get_tree().change_scene('res://Pasta/TitleScreen.tscn')

#----------------------------------------------------------------------------------------------------------------------------

var list = ["carro","torneio","banana","relogio","caneca","flor","garfo","oculos","laranja","computador","informatica","coelho"]
var selected_word
var selected_word_breakdown = []
var letters_needed
var hidden_word = []
var hangman_lives = 0
var secret_word

func _ready():
	randomize()
	new_game()

func new_game():
	choose_random_word()
	breakdown_random_word()
	prepare_hidden_word()
	convert_to_string()
	
############################################
##escolhe uma palavra random do array list##
############################################
func choose_random_word():
	selected_word = list[randi()%list.size()]
	print(selected_word)

#####################################################################
##cria um novo array com a palavra selecionada separando cada letra##
#####################################################################
func breakdown_random_word():
	for letters in selected_word:
		selected_word_breakdown.append(letters)

func prepare_hidden_word():
	for letters in selected_word:
		hidden_word.append("_ ")

func check_word_for_letter(_single_letter):
	var count = 0
	var letter_found1 = false
	for i in selected_word_breakdown:
		if _single_letter == i:
			hidden_word[count] = i+" "
			letter_found1 = true
		count += 1
		convert_to_string()
	if !letter_found1:
		check_for_hangman()
	check_win_condition()

func convert_to_string():
	secret_word = PoolStringArray(hidden_word).join("")
	$CenterContainer/HBoxContainer2/VBoxContainer/HiddenWordText.text = secret_word

func check_win_condition():
	var word1 = secret_word
	var word2 = selected_word
	var word3 = []
	var word4
	for i in word1:
		if !i == " ":
			word3.append(i)
	word4 = PoolStringArray(word3).join("")
	if word4 == word2:
		end_game()

func end_game():
	$CenterContainer/VBoxContainer/HBoxContainer/RestartButton.show()	
		
func check_for_hangman():
	hangman_lives += 1
	if hangman_lives >= 8:
		end_game()
	else:
		var hangman_image_address = "res://Images/Hangman_Images/hangman"+str(hangman_lives)+".png"
		$CenterContainer/HBoxContainer3/HangManImage.texture = load(hangman_image_address)

#----------------------------------------------------------------------------------------------------------------------------

func _on_A_pressed():
	check_word_for_letter("a")
#	$HBoxContainer/VboxContainer/A.disabled = true

func _on_B_pressed():
	check_word_for_letter("b")
#	$HBoxContainer/VboxContainer2/B.disabled = true

func _on_C_pressed():
	check_word_for_letter("c")
#	$HBoxContainer/VboxContainer3/C.disabled = true

func _on_D_pressed():
	check_word_for_letter("d")
#	$HBoxContainer/VboxContainer4/D.disabled = true

func _on_E_pressed():
	check_word_for_letter("e")
#	$HBoxContainer/VboxContainer5/E.disabled = true

func _on_F_pressed():
	check_word_for_letter("f")
#	$HBoxContainer/VboxContainer6/F.disabled = true

func _on_G_pressed():
	check_word_for_letter("g")
#	$HBoxContainer/VboxContainer7/G.disabled = true

func _on_H_pressed():
	check_word_for_letter("h")
#	$HBoxContainer/VboxContainer8/H.disabled = true

func _on_I_pressed():
	check_word_for_letter("i")
#	$HBoxContainer/VboxContainer9/I.disabled = true

func _on_J_pressed():
	check_word_for_letter("j")
#	$HBoxContainer/VboxContainer10/J.disabled = true

func _on_K_pressed():
	check_word_for_letter("k")
#	$HBoxContainer/VboxContainer11/K.disabled = true

func _on_L_pressed():
	check_word_for_letter("l")
#	$HBoxContainer/VboxContainer12/L.disabled = true

func _on_M_pressed():
	check_word_for_letter("m")
#	$HBoxContainer/VboxContainer13/M.disabled = true

func _on_N_pressed():
	check_word_for_letter("n")
#	$HBoxContainer/VboxContainer/N.disabled = true

func _on_O_pressed():
	check_word_for_letter("o")
#	$HBoxContainer/VboxContainer2/O.disabled = true

func _on_P_pressed():
	check_word_for_letter("p")
#	$HBoxContainer/VboxContainer3/P.disabled = true

func _on_Q_pressed():
	check_word_for_letter("q")
#	$HBoxContainer/VboxContainer4/Q.disabled = true

func _on_R_pressed():
	check_word_for_letter("r")
#	$HBoxContainer/VboxContainer5/R.disabled = true

func _on_S_pressed():
	check_word_for_letter("s")
#	$HBoxContainer/VboxContainer6/S.disabled = true

func _on_T_pressed():
	check_word_for_letter("t")
#	$HBoxContainer/VboxContainer7/T.disabled = true

func _on_U_pressed():
	check_word_for_letter("u")
#	$HBoxContainer/VboxContainer8/U.disabled = true

func _on_V_pressed():
	check_word_for_letter("v")
#	$HBoxContainer/VboxContainer9/V.disabled = true

func _on_W_pressed():
	check_word_for_letter("w")
#	$HBoxContainer/VboxContainer10/W.disabled = true

func _on_X_pressed():
	check_word_for_letter("x")
#	$HBoxContainer/VboxContainer11/X.disabled = true

func _on_Y_pressed():
	check_word_for_letter("y")
#	$HBoxContainer/VboxContainer12/Y.disabled = true

func _on_Z_pressed():
	check_word_for_letter("z")
#	$HBoxContainer/VboxContainer13/Z.disabled =true

#TIMER----------------------------------------------------------------------

var s=0
var m=0

func _process(delta):
	if s>9:
		m+=1
		s=0
		
#set_text(str(m)+":"+str(s))

func _on_Timer_timeout():
	s+=1	
