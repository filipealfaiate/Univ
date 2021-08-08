class Node(object):
	def __init__(self, data):
		self.data = data
		self.arco = data
		self.kids = []


	def insert(self, datakid):
		self.kids.append(datakid)


	def inserir(self, i, datakid):
		aux = self.kids[i].arco
		self.kids[i] = datakid
		self.kids[i].arco = aux	


	def inser(self, i, datakid, data):
		self.kids[i] = datakid
		self.kids[i].data = data


	def leaf(self):
		return len(self.kids) == 0


	def remove(self, data):
		if self.data == data:
			self.data = None
			self.kids = []
			return


	def printArvore(self):
		if self.data != None:
			print(self.data)

			if self.kids:
				self.printFilhos()

			
	def printFilhos(self):
		if self.kids:
			aux_node = []
			aux_data = []
			for x in self.kids:			
				aux_node.append(x)

			if self.data != "":
				for w in aux_node:
					aux_data.append(w.data)
				print("filhos de", self.data, aux_data)

			for y in aux_node:
				if y.kids:
					y.printArvore()

		
	def kid(self):
		if self.kids:
			return self.kids[0].data