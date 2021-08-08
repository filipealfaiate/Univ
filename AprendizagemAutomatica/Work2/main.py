import pandas as pd

def tratamento_dados (file_train, file_teste):
	# let ficheiro
	x_train = pd.read_csv(file_train)
	
	#retira o Id
	del x_train['Id']
	
	#alterar variaveis para numeros e colocalas no ficheiro
	enconde = {"Program": {"Informatics": 0, "Nursing": 1, "Management": 2, "Biology": 3}}
	x_train = x_train.replace(enconde)

	#colocar apenas a ultima coluna
	y_train = x_train.iloc[:, x_train.columns == 'Failure']

	#apagar a ultima coluna
	del x_train['Failure']

	# let ficheiro
	x_test = pd.read_csv(file_teste)

	#retira o Id
	identificacao_teste = x_test['Id']
	
	#alterar variaveis para numeros e colocalas no ficheiro
	del x_test['Id']

	#colocar apenas a ultima coluna
	x_test = x_test.replace(enconde)

	return x_train, y_train, x_test, identificacao_teste


x_train, y_train, x_test, y_teste = tratamento_dados("train.csv", "test.csv")

#------------------------------------------------------------------------

# # VotingClassifier
# from sklearn.ensemble import BaggingClassifier

# #clf é a sigla classificador
# clf = BaggingClassifier()

# #criar o esquema com os dados
# clf.fit(x_train, y_train.values.ravel())

# # faz predict com teste
# teste = clf.predict(x_test)

# # converte para pandas
# teste = pd.DataFrame(data=teste)

# # coloca o nome da coluna com 'Failure'
# teste.columns = ['Failure']

# # insere uma coluna com os 'Id'
# teste.insert(loc=0,column ='Id',value=y_teste)

# # criar o ficheiro
# teste.to_csv(r'resultados.csv', index = False)

#---------------------------------------------------------------

# # RandomForestClassifier
# from sklearn.ensemble import RandomForestClassifier

# #clf é a sigla classificador
# clf = RandomForestClassifier()

# #criar o esquema com os dados
# clf.fit(x_train, y_train.values.ravel())

# # faz predict com teste
# teste = clf.predict(x_test)

# # converte para pandas
# teste = pd.DataFrame(data=teste)

# # coloca o nome da coluna com 'Failure'
# teste.columns = ['Failure']

# # insere uma coluna com os 'Id'
# teste.insert(loc=0,column ='Id',value=y_teste)

# # criar o ficheiro
# teste.to_csv(r'resultados.csv', index = False)

#--------------------------------------------------------

# # KNeighborsClassifier
# from sklearn.neighbors import KNeighborsClassifier

# #clf é a sigla classificador
# clf = KNeighborsClassifier(n_neighbors=15)

# #criar o esquema com os dados
# clf.fit(x_train, y_train.values.ravel())

# # faz predict com teste
# teste = clf.predict(x_test)

# # converte para pandas
# teste = pd.DataFrame(data=teste)

# # coloca o nome da coluna com 'Failure'
# teste.columns = ['Failure']

# # insere uma coluna com os 'Id'
# teste.insert(loc=0,column ='Id',value=y_teste)

# # criar o ficheiro
# teste.to_csv(r'resultados.csv', index = False)

#----------------------------------------------------------

# BaggingClassifier & KNeighborsClassifier
from sklearn.ensemble import BaggingClassifier
from sklearn.neighbors import KNeighborsClassifier

#clf é a sigla classificador
clf = BaggingClassifier(KNeighborsClassifier(n_neighbors=10))

#criar o esquema com os dados
clf.fit(x_train, y_train.values.ravel())

# faz predict com teste
teste = clf.predict(x_test)

# converte para pandas
teste = pd.DataFrame(data=teste)

# coloca o nome da coluna com 'Failure'
teste.columns = ['Failure']

# insere uma coluna com os 'Id'
teste.insert(loc=0,column ='Id',value=y_teste)

# criar o ficheiro
teste.to_csv(r'resultados.csv', index = False)

#--------------------------------------------------

# #SVC
# from sklearn.svm import LinearSVC

# #clf é a sigla classificador
# clf=LinearSVC()

# #criar o esquema com os dados
# clf.fit(x_train, y_train.values.ravel())

# # faz predict com teste
# teste = clf.predict(x_test)

# # converte para pandas
# teste = pd.DataFrame(data=teste)

# # coloca o nome da coluna com 'Failure'
# teste.columns = ['Failure']

# # insere uma coluna com os 'Id'
# teste.insert(loc=0,column ='Id',value=y_teste)

# # criar o ficheiro
# teste.to_csv(r'resultados.csv', index = False)

#------------------------------------------

