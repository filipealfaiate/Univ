.data

A: .asciiz "Qual o metodo que quer utilizar?\n 1- Media\n 2- Mediana\n 3- Sair\n"
B: .asciiz "Numero invalido, tem de ser uma das opcoes a cima referidas\n"
FILE_IMAGE_GRAY_A: .asciiz "/home/filipe/Desktop/Trabalho_ASC/lenaA.gray"
FILE_IMAGE_GRAY_B: .asciiz "/home/filipe/Desktop/Trabalho_ASC/lenaB.gray"
FINAL_BUFFER: .space 262144 #numero de pixeis
ORIGINAL_BUFFER: .space 262144
AUX_BUFFER: .space 9

.text

#####################################################################################################

main:
	la $a0, A				#guarda o que esta na label A em $a0
	li $v0, 4				#guarda em $v0 o que é para executar (4 = print string)
	syscall					#pede "por favor" ao sistema operativo
	
	li $v0, 5				#guarda em $v0 o que é para executar (5 = read int)
	syscall					#pede "por favor" ao sistema operativo e guarda o valor inserido no $v0
	
	beq $v0, 1, MEDIA			#se o numero inserido for 1 vai para a label MEDIA
	nop
	beq $v0, 2, MEDIANA
	nop
	beq $v0, 3, SAIR			#se o numero inserido for 2 vai para a label SAIR
	nop
						#caso o valor inserido nao for 1/2
	la $a0, B				#guarda o que esta na label B em $a0
	li $v0, 4				#guarda em $v0 o que é para executar (4 = print string)
	syscall					#pede "por favor" ao sistema operativo
	
	j main					# volta ao menu inicial
	nop

#####################################################################################################

#sai do programa
SAIR:
	li $v0, 10				#guarda em $v0 o que é para executar (10 = exit)
	syscall					#pede "por favor" ao sistema operativo
	
#####################################################################################################

#calcula a media
MEDIA:
	jal READ_GRAY_IMAGE			#salta para a funçao que abre a imagem e lê para o Buffer
	nop
	jal MEAN_FILTER				#salta para a funçao que calcula a media
	nop
	jal WRITE_GRAY_IMAGE			#salta para a funçao que cria o ficheiro da imagem sem ruido
	nop
	jal CLOSE_GRAY_IMAGE			#salta para a funçao que fecha as imagens GRAY
	nop
	j main					#volta ao menu
	nop

#####################################################################################################

READ_GRAY_IMAGE:
	#Abrir a imagem GRAY original
	la $a0, FILE_IMAGE_GRAY_A		#indica qual o ficheiro
	li $a1, 0				#indica que é para ler
	li $a2, 0
	li $v0, 13				#indica que é para abrir
	syscall

	#ler a imagem GRAY original para o Buffer
	move $a0, $v0				#indica a posiçao onde o ficheiro esta aberto
	la $a1, ORIGINAL_BUFFER			#indica o endereço onde começa a guardar a imagem
	la $a2, 262144				#indica quantos Bytes que vai ler
	li $v0, 14				#le o ficheiro para o buffer
	syscall
	jr $ra
	nop

#####################################################################################################

WRITE_GRAY_IMAGE:
	#criar o ficheiro GRAY
	la $a0, FILE_IMAGE_GRAY_B		#indica onde quer criar e como se vai chamar
	li $a1, 1				#indica que é para escrever
	li $a2, 0
	li $v0, 13				#abre o ficheiro
	syscall
	
	#escreve no ficheiro a media	
	move $a0, $v0				#indica a posiçao onde o ficheiro esta aberto
	la $a1, FINAL_BUFFER			#indica o endereço onde começa a copiar a imagem
	li $a2, 262144				#indica o numero de Bytes que vai guardar
	li $v0, 15				#copia
	syscall
	jr $ra
	nop
	
#####################################################################################################

CLOSE_GRAY_IMAGE:
	#fecha imagem GRAY A	
	la $a0, FILE_IMAGE_GRAY_A		#indica qual o ficheiro
	li $v0, 16				#fecha o ficheiro indicado
	syscall

	#fecha imagem GRAY B	
	la $a0, FILE_IMAGE_GRAY_B		#indica qual o ficheiro
	li $v0, 16				#fecha o ficheiro indicado
	syscall
	jr $ra
	nop
	
#####################################################################################################
	
MEAN_FILTER:
	#criar e guardar na pilha
	addi $sp, $sp, -20			#alocar espaço na pilha
	sw $s0, 0($sp)				#
	sw $s1, 4($sp)				#
	sw $s2, 8($sp)				#coloca os $s's na pilha
	sw $s3, 12($sp)				#
	sw $s4, 16($sp)				#
	
	#valores fixos e contador (i)
	la $s0, ORIGINAL_BUFFER
	la $s1, FINAL_BUFFER
	li $s2, 511				#linha
	move $s3, $0				#i=0
	li $s4, 9				#$s4=9
	
FOR:
	#condiçoes
	beq $s3, 262144, END_MATRIZ		#i=sz ($a2)
	nop
	
	#posicionar na Matriz 3x3
	move $t0, $s3 				#guarda o pixel 2ª linha, 2ª posiçao
	addi $t1, $t0, 1			#guarda o pixel 2ª linha, 3ª posiçao
	addi $t2, $t0, -1			#guarda o pixel 2ª linha, 1ª posiçao
	add $t3, $t0, $s2			#guarda o pixel 3ª linha, 3ª posiçao
	addi $t4, $t3, 1			#guarda o pixel 3ª linha, 2ª posiçao
	addi $t5, $t4, 1			#guarda o pixel 3ª linha, 1ª posiçao
	sub $t6, $t0, $s2			#guarda o pixel 1ª linha, 1ª posiçao
	addi $t7, $t6, -1			#guarda o pixel 1ª linha, 2ª posiçao
	addi $t8, $t7, -1			#guarda o pixel 1ª linha, 2ª posiçao
	
	#guardar o valores da Matriz A
	add $t0, $t0, $s0			#coloca o $t0 na posiçao certa do Buffer original
	lb $t0, 0($t0)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t1, $t1, $s0			#coloca o $t1 na posiçao certa do Buffer original
	lb $t1, 0($t1)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t2, $t2, $s0			#coloca o $t2 na posiçao certa do Buffer original
	lb $t2, 0($t2)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t3, $t3, $s0			#coloca o $t3 na posiçao certa do Buffer original
	lb $t3, 0($t3)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t4, $t4, $s0			#coloca o $t4 na posiçao certa do Buffer original
	lb $t4, 0($t4)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t5, $t5, $s0			#coloca o $t5 na posiçao certa do Buffer original
	lb $t5, 0($t5)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t6, $t6, $s0			#coloca o $t6 na posiçao certa do Buffer original
	lb $t6, 0($t6)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t7, $t7, $s0			#coloca o $t7 na posiçao certa do Buffer original
	lb $t7, 0($t7)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t8, $t8, $s0			#coloca o $t8 na posiçao certa do Buffer original
	lb $t8, 0($t8)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	
	#somatorio de todos os pixeis
	add $t0, $t0, $t1			#
	add $t0, $t0, $t2			#
	add $t0, $t0, $t3			#
	add $t0, $t0, $t4			#somatorio
	add $t0, $t0, $t5			#
	add $t0, $t0, $t6			#
	add $t0, $t0, $t7			#
	add $t0, $t0, $t8			#
	
	#dividir o somatorio por 9
	div $t0, $s4
	mflo $t0				#gurada o quociente do numero
	
	#colocar na Matriz final
	add $t1, $s3, $s1			#posiciona o Buffer final para a posiçao do i
	sb $t0, 0($t1)				#guarda o valor da media no endereço que $t1 contem
	
	addi $s3, $s3, 1			#i++
	
	j FOR
	nop

END_MATRIZ:
	#devolve os valores iniciais dos s's
	lw $s0, 0($sp)				#
	lw $s1, 4($sp)				#
	lw $s2, 8($sp)				#coloca os $s's na pilha
	lw $s3, 12($sp)				#
	lw $s4, 16($sp)				#
	addi $sp, $sp, 20			#desaloca espaço na pilha
	
	#volta a funçao anterior
	jr $ra
	nop
	
#####################################################################################################

MEDIANA:
	jal READ_GRAY_IMAGE			#salta para a funçao que abre a imagem e lê para o Buffer
	nop
	jal MEDEAN_FILTER			#salta para a funçao que calcula a media
	nop
	jal WRITE_GRAY_IMAGE			#salta para a funçao que cria o ficheiro da imagem sem ruido
	nop
	jal CLOSE_GRAY_IMAGE			#salta para a funçao que fecha as imagens GRAY
	nop
	j main					#volta ao menu
	nop

#####################################################################################################

MEDEAN_FILTER:
	#criar e guardar na pilha
	addi $sp, $sp, -24			#alocar espaço na pilha
	sw $s0, 0($sp)				#
	sw $s1, 4($sp)				#
	sw $s2, 8($sp)				#
	sw $s3, 12($sp)				#coloca os $s's na pilha
	sw $s4, 16($sp)				#
	sw $s5, 20($sp)				#
	
	
	#valores fixos e contador (i)
	la $s0, ORIGINAL_BUFFER
	la $s1, FINAL_BUFFER
	li $s2, 511				#linha
	move $s3, $0				#i=0
	li $s4, 9				#$s4=9
	la $s5, AUX_BUFFER			#endereço da matriz 3x3
	
FOR_2:
	#condiçoes
	bgt $s3, $a2, END_MATRIZ_2		#i>sz ($a2)
	nop
	
	#posicionar na Matriz 3x3
	move $t0, $s3 				#guarda o pixel 2ª linha, 2ª posiçao
	addi $t1, $t0, 1			#guarda o pixel 2ª linha, 3ª posiçao
	addi $t2, $t0, -1			#guarda o pixel 2ª linha, 1ª posiçao
	add $t3, $t0, $s2			#guarda o pixel 3ª linha, 1ª posiçao
	addi $t4, $t3, 1			#guarda o pixel 3ª linha, 2ª posiçao
	addi $t5, $t4, 1			#guarda o pixel 3ª linha, 3ª posiçao
	sub $t6, $t0, $s2			#guarda o pixel 1ª linha, 3ª posiçao
	addi $t7, $t6, -1			#guarda o pixel 1ª linha, 2ª posiçao
	addi $t8, $t7, -1			#guarda o pixel 1ª linha, 1ª posiçao
	
	
	#guardar o valores da Matriz A
	add $t0, $t0, $s0			#coloca o $t0 na posiçao certa do Buffer aux
	lbu $t0, 0($t0)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t1, $t1, $s0			#coloca o $t1 na posiçao certa do Buffer aux
	lbu $t1, 0($t1)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t2, $t2, $s0			#coloca o $t2 na posiçao certa do Buffer aux
	lbu $t2, 0($t2)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t3, $t3, $s0			#coloca o $t3 na posiçao certa do Buffer aux
	lbu $t3, 0($t3)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t4, $t4, $s0			#coloca o $t4 na posiçao certa do Buffer aux
	lbu $t4, 0($t4)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t5, $t5, $s0			#coloca o $t5 na posiçao certa do Buffer aux
	lbu $t5, 0($t5)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t6, $t6, $s0			#coloca o $t6 na posiçao certa do Buffer aux
	lbu $t6, 0($t6)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t7, $t7, $s0			#coloca o $t7 na posiçao certa do Buffer aux
	lbu $t7, 0($t7)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	add $t8, $t8, $s0			#coloca o $t8 na posiçao certa do Buffer aux
	lbu $t8, 0($t8)				#guarda o valor que esta nessa posiçao do Buffer no mesmo registo
	
	#guarda o valor dos pixeis na Matriz 3x3
	sb $t8, 0($s5)				#1ª linha, 1ª posiçao
	sb $t7, 1($s5)				#1ª linha, 2ª posiçao
	sb $t6, 2($s5)				#1ª linha, 3ª posiçao
	sb $t2, 3($s5)				#2ª linha, 1ª posiçao
	sb $t0, 4($s5)				#2ª linha, 2ª posiçao
	sb $t1, 5($s5)				#2ª linha, 3ª posiçao
	sb $t3, 6($s5)				#3ª linha, 1ª posiçao
	sb $t4, 7($s5)				#3ª linha, 2ª posiçao
	sb $t5, 8($s5)				#3ª linha, 3ª posiçao

#####################################################################################################
#primeira posiçao
#$t9 = aux	
#verifica se $t7 <= $t8, se for salta para a proxima posiçao
POS_1_0:
	ble $t7, $t8, POS_1_1
	nop
	move $t9, $t8
	move $t8, $t7
	move $t7, $t9
	j POS_1_0
	nop

#verifica se $t6 <= $t8, se for salta para a proxima posiçao
POS_1_1:
	ble $t6, $t8, POS_1_2
	nop
	move $t9, $t8
	move $t8, $t6
	move $t6, $t9
	j POS_1_0
	nop

#verifica se $t2 <= $t8, se for salta para a proxima posiçao
POS_1_2:
	ble $t2, $t8, POS_1_3
	nop
	move $t9, $t8
	move $t8, $t2
	move $t2, $t9
	j POS_1_0
	nop

#verifica se $t0 <= $t8, se for salta para a proxima posiçao
POS_1_3:
	ble $t0, $t8, POS_1_4
	nop
	move $t9, $t8
	move $t8, $t0
	move $t0, $t9
	j POS_1_0
	nop
	
#verifica se $t1 <= $t8, se for salta para a proxima posiçao
POS_1_4:
	ble $t1, $t8, POS_1_5
	nop
	move $t9, $t8
	move $t8, $t1
	move $t1, $t9
	j POS_1_0
	nop

#verifica se $t3 <= $t8, se for salta para a proxima posiçao
POS_1_5:
	ble $t3, $t8, POS_1_6
	nop
	move $t9, $t8
	move $t8, $t3
	move $t3, $t9
	j POS_1_0
	nop
	
#verifica se $t4 <= $t8, se for salta para a proxima posiçao
POS_1_6:
	ble $t4, $t8, POS_1_7
	nop
	move $t9, $t8
	move $t8, $t4
	move $t4, $t9
	j POS_1_0
	nop

#verifica se $t5 <= $t8, se for salta para a proxima posiçao
POS_1_7:
	ble $t5, $t8, POS_2_0
	nop
	move $t9, $t8
	move $t8, $t5
	move $t5, $t9
	j POS_1_0
	nop
	
#####################################################################################################
#segunda posiçao
#$t9 = aux
#verifica se $t6 <= $t7, se for salta para a proxima posiçao
POS_2_0:
	ble $t6, $t7, POS_2_1
	nop
	move $t9, $t7
	move $t7, $t6
	move $t6, $t9
	j POS_2_0
	nop
	
#verifica se $t2 <= $t7, se for salta para a proxima posiçao
POS_2_1:
	ble $t2, $t7, POS_2_2
	nop
	move $t9, $t7
	move $t7, $t2
	move $t2, $t9
	j POS_2_0
	nop
	
#verifica se $t0 <= $t7, se for salta para a proxima posiçao
POS_2_2:
	ble $t0, $t7, POS_2_3
	nop
	move $t9, $t7
	move $t7, $t0
	move $t0, $t9
	j POS_2_0
	nop

#verifica se $t1 <= $t7, se for salta para a proxima posiçao
POS_2_3:
	ble $t1, $t7, POS_2_4
	nop
	move $t9, $t7
	move $t7, $t1
	move $t1, $t9
	j POS_2_0
	nop

#verifica se $t3 <= $t7, se for salta para a proxima posiçao
POS_2_4:
	ble $t3, $t7, POS_2_5
	nop
	move $t9, $t7
	move $t7, $t3
	move $t3, $t9
	j POS_2_0
	nop
	
#verifica se $t4 <= $t7, se for salta para a proxima posiçao
POS_2_5:
	ble $t4, $t7, POS_2_6
	nop
	move $t9, $t7
	move $t7, $t4
	move $t4, $t9
	j POS_2_0
	nop
	
#verifica se $t5 <= $t7, se for salta para a proxima posiçao
POS_2_6:
	ble $t5, $t7, POS_3_0
	nop
	move $t9, $t7
	move $t7, $t5
	move $t5, $t9
	j POS_2_0
	nop
	
####################################################################################################	
#terceira posiçao
#$t9 = aux
#verifica se $t2 <= $t6, se for salta para a proxima posiçao
POS_3_0:
	ble $t2, $t6, POS_3_1
	nop
	move $t9, $t6
	move $t6, $t2
	move $t2, $t9	
	j POS_3_0
	nop
	
#verifica se $t0 <= $t6, se for salta para a proxima posiçao
POS_3_1:
	ble $t0, $t6, POS_3_2
	nop
	move $t9, $t6
	move $t6, $t0
	move $t0, $t9
	j POS_3_0
	nop
	
#verifica se $t1 <= $t6, se for salta para a proxima posiçao
POS_3_2:
	ble $t1, $t6, POS_3_3
	nop
	move $t9, $t6
	move $t6, $t1
	move $t1, $t9
	j POS_3_0
	nop
	
#verifica se $t3 <= $t6, se for salta para a proxima posiçao
POS_3_3:
	ble $t3, $t6, POS_3_4
	nop
	move $t9, $t6
	move $t6, $t3
	move $t3, $t9
	j POS_3_0
	nop
	
#verifica se $t4 <= $t6, se for salta para a proxima posiçao
POS_3_4:
	ble $t4, $t6, POS_3_5
	nop
	move $t9, $t6
	move $t6, $t4
	move $t4, $t9
	j POS_3_0
	nop
	
#verifica se $t5 <= $t6, se for salta para a proxima posiçao
POS_3_5:
	ble $t5, $t6, POS_4_0
	nop
	move $t9, $t6
	move $t6, $t5
	move $t5, $t9
	j POS_3_0
	nop
	
####################################################################################################	
#quarta posiçao
#$t9 = aux	
#verifica se $t0 <= $t2, se for salta para a proxima posiçao
POS_4_0:
	ble $t0, $t2, POS_4_1
	nop
	move $t9, $t2
	move $t2, $t0
	move $t0, $t9
	j POS_4_0
	nop

#verifica se $t1 <= $t2, se for salta para a proxima posiçao
POS_4_1:
	ble $t1, $t2, POS_4_2
	nop
	move $t9, $t2
	move $t2, $t1
	move $t1, $t9
	j POS_4_0
	nop
	
#verifica se $t3 <= $t2, se for salta para a proxima posiçao
POS_4_2:
	ble $t3, $t2, POS_4_3
	nop
	move $t9, $t2
	move $t2, $t3
	move $t3, $t9
	j POS_4_0
	nop
	
#verifica se $t0 <= $t2, se for salta para a proxima posiçao
POS_4_3:
	ble $t4, $t2, POS_4_4
	nop
	move $t9, $t2
	move $t2, $t4
	move $t4, $t9
	j POS_4_0
	nop
	
#verifica se $t5 <= $t2, se for salta para a proxima posiçao
POS_4_4:
	ble $t5, $t2, POS_5_0
	nop
	move $t9, $t2
	move $t2, $t5
	move $t5, $t9
	j POS_4_0
	nop

####################################################################################################	
#quinta posiçao
#$t9 = aux	
#verifica se $t1 <= $t0, se for salta para a proxima posiçao
POS_5_0:
	ble $t1, $t0, POS_5_1
	nop
	move $t9, $t0
	move $t0, $t1
	move $t1, $t9
	j POS_5_0
	nop
	
#verifica se $t3 <= $t0, se for salta para a proxima posiçao
POS_5_1:
	ble $t3, $t0, POS_5_2
	nop
	move $t9, $t0
	move $t0, $t3
	move $t3, $t9
	j POS_5_0
	nop
	
#verifica se $t4 <= $t0, se for salta para a proxima posiçao
POS_5_2:
	ble $t4, $t0, POS_5_3
	nop
	move $t9, $t0
	move $t0, $t4
	move $t4, $t9
	j POS_5_0
	nop
	
#verifica se $t5 <= $t0, se for salta para a proxima posiçao
POS_5_3:
	ble $t5, $t0, POS_6_0
	nop
	move $t9, $t0
	move $t0, $t5
	move $t5, $t9
	j POS_5_0
	nop
	
####################################################################################################	
#sexta posiçao
#$t9 = aux
#verifica se $t3 <= $t1, se for salta para a proxima posiçao
POS_6_0:
	ble $t3, $t1, POS_6_1
	nop
	move $t9, $t1
	move $t1, $t3
	move $t3, $t9
	j POS_6_0
	nop	
	
#verifica se $t4 <= $t1, se for salta para a proxima posiçao
POS_6_1:
	ble $t4, $t1, POS_6_2
	nop
	move $t9, $t1
	move $t1, $t4
	move $t4, $t9
	j POS_6_0
	nop
	
#verifica se $t5 <= $t1, se for salta para a proxima posiçao
POS_6_2:
	ble $t5, $t1, POS_7_0
	nop
	move $t9, $t1
	move $t1, $t5
	move $t5, $t9
	j POS_6_0
	nop
	
####################################################################################################	
#setima posiçao
#$t9 = aux
#verifica se $t4 <= $t3, se for salta para a proxima posiçao	
POS_7_0:
	ble $t4, $t3, POS_7_1
	nop
	move $t9, $t3
	move $t3, $t4
	move $t4, $t9
	j POS_7_0
	nop

#verifica se $t5 <= $t3, se for salta para a proxima posiçao	
POS_7_1:
	ble $t5, $t3, POS_8_0
	nop
	move $t9, $t5
	move $t5, $t3
	move $t3, $t9
	j POS_7_0
	nop
	
####################################################################################################	
#oitava posiçao
#$t9 = aux
#verifica se $t5 <= $t4, se for salta para a proxima posiçao		
POS_8_0:
	ble $t5, $t4, END_COMPARACAO
	nop
	move $t9, $t4
	move $t4, $t5
	move $t5, $t9
	
END_COMPARACAO:
	lbu $t0, 4($s5)				#guarda no $t0 o que esta no centro da Matriz auxiliar
	add $t1, $s3, $s1			#posiciona o Buffer B e guarda o endereço no $t1
	sb $t0, 0($t1)				#guarda no endereço do $t1 o valor de $t0
	addi $s3, $s3, 1			#i++
	j FOR_2
	nop

END_MATRIZ_2:
	lw $s0, 0($sp)				#
	lw $s1, 4($sp)				#
	lw $s2, 8($sp)				#
	lw $s3, 12($sp)				#coloca os $s's na pilha
	lw $s4, 16($sp)				#
	lw $s5, 20($sp)				#
	addi $sp, $sp, 24			#
	
	jr $ra
	nop
	
	
