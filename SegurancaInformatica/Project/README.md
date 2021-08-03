Todos os programas que são apresentados originam Stack Overflow, então, quando executados deverá aparecer uma mensagem de Segmentation fault.

Programa StackOverflowMatrix.c:

- Neste exemplo tentamos alocar um espaço maior que o espaço permitido pela Stack.


Programa StackOverflowRecursive.c:

- Neste exemplo é chamada uma função recursiva sem fim, alocando espaço na stack para guardar informações de todas as funções quando elas são chamadas.

Programa StackOverflowRecursiveWithEnd.c:

- Neste exemplo, o objetivo é mostrar que mesmo que uma função recursiva posso ter uma maneira de terminar, colocando um número muito grande irá originar a mesma situação que os restantes exemplos. Um exemplo de input é 200000.
