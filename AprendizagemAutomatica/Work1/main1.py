import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np 
import pandas as pd 
import random
import math
from pprint import pprint
from DecisionTree import Node

from sklearn.model_selection import train_test_split
from sklearn.datasets import load_breast_cancer
from sklearn.metrics import accuracy_score 
from sklearn.metrics import classification_report 
from sklearn.metrics import confusion_matrix 
from sklearn.tree import DecisionTreeClassifier


def classes(ydata):  #retorna um array com as classes existentes e o número de classes existentes
	array_classes = []
	array_classes.append(ydata[0])
	count = 1;
	
	for index in ydata:
		for i in array_classes:
			if i == index:
				break
			if array_classes[-1] == i:
				array_classes.append(index)
				count+=1
	return array_classes


def fancy_classes(xdata, x): #retorna os elementos nao repetidos da coluna pretendente do xdata
	array = []

	for index in xdata:
		array.append(index[x])

	return classes(array)


def nr_N_nr_P(ydata, array):  #retorna quantas vezes cada classe aparece

	array_N_P = []
	j = 0

	for posicao in array:
		array_N_P.append(0)

		for index in ydata:
			if index == posicao:
				array_N_P[j]+=1

		j+=1
	return array_N_P


def previous_entropy(array): #calcula a parte da probabilidade da entropia
	soma = sum(array)
	index = 0
	for i in array:
		array[index] = (i/soma)
		index+=1
	return array


def entropy(array): #calcula a entropia
    total_entropy = 0
    for i in array:
        if i != 0:
        	total_entropy += -i * math.log(i, 2)
    return total_entropy


def gini(array): #calcula a gini
	total_gini = 0
	for i in array:
		total_gini += i**2
	return 1 - total_gini


def taxa_erro(array): #calcula a Taxa de erro
	taxa_erro = 0
	for i in array:
		taxa_erro += i/sum(array)
	return taxa_erro


def entropia_atributo(entropias, ydata, count): #calcula a impureza de cada atributo
	resultado = 0
	i = 0
	for index in entropias:
		resultado += (sum(count[i]))/(len(ydata)) * index
		i+=1
		
	return resultado
	

def Gain(xdata, ydata, array_classes, entropy_geral): #calcula o gain e coloca todos num array
	nr_atributos = len(xdata[0])
	x = 0
	entropias = []
	gain = []
	data_geral = []
	while x < nr_atributos:
		entropias.append([])
		aux = fancy_classes(xdata, x)
		wright_counts = []
		j = 0
		index1 = ydata[j]
		y = 0
		l = 0

		while y < len(aux):
			count = []
			wright_counts.append([])
			
			for t in array_classes:
				count.append(0)
				wright_counts[y].append(0)

			for index in xdata:
				if index[x] == aux[y] and index1 == ydata[j]:
					for k in array_classes:
						if k == ydata[j]:
							count[l] += 1
							wright_counts[y][l] += 1
						l += 1
					l =0

				if j == len(ydata)-1:
					break
				j+=1
				index1 = ydata[j]

			if len(count) == 2:
				for k in count:
					if k == 0:
						entropia = 0
						break
					elif count[-1] == k:
						entropia = entropy(previous_entropy (count))
			else:
				entropia = entropy(previous_entropy(count))

			entropias[x].append(entropia)
			y+=1
			j=0

		gain.append(entropy_geral - entropia_atributo(entropias[x], ydata, wright_counts))
		
		x+=1

	return gain, entropias


def fit(xdata, ydata, atributos):
	i = 0
	zdata = []
	zdata.append([])
	for zlinha in xdata:
		for zposicao in zlinha:

			zdata[i].append(zposicao)

			if len(zlinha) == len(zdata[i]):
				zdata[i].append(ydata[i])
				if len(xdata) != len(zdata):
					zdata.append([])
		i += 1

	# atributos = data[0,0:-1]
	print("\nescolha um numero: \n1 - entropia \n2 - gini \n3 - taxa de erro")
	arg_impureza = int(input())

	return fit_aux(xdata, ydata, zdata, atributos, arg_impureza)


def fit_aux(xdata, ydata, zdata, atributos, arg_impureza): #constroi a arvore
	array = []
	higher = 0
	lower = 1000
	array = classes(ydata)

	switch_impureza = {
		1: entropy( previous_entropy (nr_N_nr_P (ydata, array))),	#funciona para todos mas no ficheiro 4 apenas 7/20
		2: gini(previous_entropy (nr_N_nr_P (ydata, array))),		#so funciona para o ficheiro 1
		3: taxa_erro(previous_entropy (nr_N_nr_P (ydata, array)))	#so nao funciona para o ficheiro 4
	}

	impureza = switch_impureza.get(arg_impureza)
	gain_total, entropia_total = Gain(xdata, ydata, array, impureza)

	i = 0
	auxi = 0
	for high in gain_total:
		if high > higher:
			higher = high
			auxi = i
		i += 1
	
	n = Node(atributos[auxi])

	filhos = fancy_classes(xdata, auxi)

	for x in filhos:
		n.insert(Node(x))
		
	j=0
	for low in entropia_total[auxi]:
		if low == 0:
			for z in zdata:
				if z[auxi] == filhos[j]:
					n.inser(j, Node(filhos[j]), z[-1])
					break

		else:
			new_zdata = []
			new_xdata = []
			new_ydata = []
			f = 0
			r = 0

			for dados in zdata:
				if dados[auxi] == filhos[j]:
					new_zdata.append(dados)
					new_ydata.append(dados[-1])

			for g in new_zdata:
				h = 0
				new_xdata.append([])
				
				for v in g:
					if g[-1] != v:
						new_xdata[r].append(g[h])
						h += 1
					else:
						r += 1

					f += 1

			if new_zdata != []:
				n.inserir(j, fit_aux(new_xdata, new_ydata, new_zdata, atributos, arg_impureza))
		
		j+=1

	return n

	
def Percorrer_Arvore(x_test, y_test, atributos, raiz): #indica qual a linha que vai testar na matriz de teste
	
	answer = []
	for teste in x_test:
		answer.append(Percorrer_Arvore_Auxiliar(x_test, y_test, atributos, raiz,teste))

	return answer == y_test #verifica cada posicao do array das respostas obtidas e igual a cada posicao do array das respostas corretas
							#retorna um bool para cada posicao

				
def Percorrer_Arvore_Auxiliar(x_test, y_test, atributos, raiz, teste): #percorre a arvore verificando os nos e os ramos correspondentes ao que recebe da linha teste
	i=0
	for x in atributos:
		if raiz.leaf():
			return raiz.data
		
		elif x == raiz.data:
			for filhos in raiz.kids:
				if filhos.arco == teste[i]:
					return Percorrer_Arvore_Auxiliar(x_test, y_test, atributos, filhos, teste)
		
		elif "" == raiz.arco:
			return Percorrer_Arvore_Auxiliar(x_test, y_test, atributos, raiz.kids[0], teste)
			
		i += 1


def score(respostas, calculo_erro): #calcula a probabilidade de acertar
	i = 0
	fim = 0
	for x in respostas:
		if x == True:
			fim = calculo_erro[i]/sum(calculo_erro)
		i += 1
	return fim


def Data_Split(data): #divide os dados
	
	xdata = data[1:,0:-1] # dados: da segunda à ultima linha, da primeira à penúltima coluna
	ydata = data[1:,-1] # classe: da segunda à ultima linha, só última coluna
	atributos = data[0,0:-1]

	x_train, x_test, y_train, y_test = train_test_split(xdata, ydata, test_size=0.25)

	return xdata, ydata, x_train, x_test, y_train, y_test, atributos

###################################################################  MAIN  ##################################################################################################
switch_ficheiro = {
    1: "weather.nominal.csv",
    2: "vote.csv",
    3: "contact-lenses.csv",
    4: "soybean.csv"  
}

while True:
	print("\nescolha um numero: \n1 - weather.nominal.csv \n2 - vote.csv \n3 - contact-lenses.csv \n4 - oybean.csv \n0 - Exit")
	arg_ficheiro = int(input())
	if arg_ficheiro != 0:
		data= np.genfromtxt(switch_ficheiro.get(arg_ficheiro), delimiter=",", dtype=None, encoding=None)
		xdata, ydata, x_train, x_test, y_train, y_test, atributos = Data_Split(data)
		raiz = Node("")
		raiz.insert(fit(x_train, y_train, atributos)) #cria uma arvore consoante os dados de treino
		#fit(xdata, ydata)
		raiz.printArvore()
		print()
		answer = Percorrer_Arvore(x_test, y_test, atributos, raiz) #precorre a arvore n vezes (consuante o tamanho do teste) para adivinhar a resposta
		respostas = classes(answer) #apenas se acertou ou se errou
		print(answer)
		calculo_erro = nr_N_nr_P(answer, respostas) #numero de vezes que acertou e que errou
		print(score(respostas, calculo_erro))
	else:
		break