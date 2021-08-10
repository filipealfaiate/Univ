extends "res://addons/gut/test.gd"

var singlePlayer = preload('res://Pasta/SinglePlayer1.gd')

func test_word_randomizer():
	
	var sp = singlePlayer.new()
	
	assert_eq(sp.selected_word, sp.choose_random_word(), "pass")

func test_hangman_lives_start():
	
	var sp = singlePlayer.new()
	
	assert_eq(0, sp.hangman_lives, "pass")

func test_array():
	
	var sp = singlePlayer.new()
	
	assert_eq(sp.list[0], "carro", "pass")


