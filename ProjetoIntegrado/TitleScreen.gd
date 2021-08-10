extends Control

func _on_SinglePlayerButton_pressed():
	get_tree().change_scene('res://Pasta/SinglePlayer1.tscn')

func _on_PlayervsPlayerButton_pressed():
	get_tree().change_scene('res://Pasta/Player vs Player.tscn')
