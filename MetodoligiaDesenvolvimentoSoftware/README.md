![image](https://gitlab.com/leonelluiscorado/trabalhofinal/-/raw/c438204c22088e7f917c3822aa65f6b04633c1b5/image.png)

# Sistema Automático de Registo de Presenças nas aulas

### Autores : 
* Filipe Alfaiate - número 43315           
* Leonel Corado   - número 42602
* Vasco Barnabé   - número 42819

### 22 de Junho de 2020

### Introdução
    
Foi-nos proposto desenvolver um sistema automático de registo de presenças nas aulas de MDS. O sistema deve permitir registar as presenças dos alunos e dos professores automaticamente, através da leitura do cartão de aluno ou docente, respetivamente. O utilizador deve efetuar o login e deve poder realizar ações como gerir as suas presenças, consultar as suas faltas e justificá-las, verificar o horário, consultar todos os elementos da turma, receber alertas quando as suas faltas atingirem um dado limite, entre outras.

Foram inicialmente estabelecidos alguns requisitos de sistema para o desenvolvimento deste projeto, e de seguida foi realizada uma planificação detalhada sobre outros requisitos. Foram também criados alguns diagramas de modo a que estes pudessem ilustrar diferentes fases e/ou diferentes tarefas do projeto, podendo assim o "cliente" ter uma melhor perceção do modo de funcionamento do seu produto final.

Após esta primeira fase de "especificação" do projeto, seguiu-se a segunda fase, a "implementação".
No decorrer desta, foi decidido por todos os developers do projeto que algumas alterações teriam de ser feitas, alterações essas que:

1. Teriam sempre de cumprir os requisitos inicialmente estabelecidos;
2. Não podiam fugir muito ao que foi apresentado durante a a fase de "especificação" do projeto;
3. Fizessem a diferença do "esta funcionalidade não funciona" para o "esta tarefa funciona.

Veremos então como foi implementado este sistema automático de registo de presenças e como o mesmo funciona.

### Implementação

Esta fase do projeto foi, como já foi referido, tendo como base a todos os requisitos e diagramas criados durante a fase de especificação, incluindo algumas alterações foram realizadas posteriormente. 

Não foi proposto implementar este sistema na totalidade, deixando de parte algumas funcionalidades/restrições como:

1. O envio automático de emails quando são atingidos 25% ou 50% das faltas;
2. Importação de dados do SIIUE (foi usado um conjunto de alunos/cartões para testes);
3. A realização de um gráfico de presenças por aula, sendo apenas apresentado o número de faltas por aula;
4. Autenticação/login do utilizador, sendo admitido que o utilizador que use o sistema já tenha realizado com sucesso o login.

Este sistema foi, assim, planificado como um conjunto de duas aplicações que funcionam da seguinte forma:

* **Aplicação 1 - Leitor de cartões**
    * Consiste numa aplicação(modo terminal) que espera por um input textual(código do cartão). A cada código recebido, identifica o aluno(ou docente) em questão e regista a sua presença no sistema.

* **Aplicação 2 - Gestão do Sistema**
    * Aplicação em modo terminal que apresenta um menu com vários items.

No entanto, durante a implementação do projeto foi decidido conjugar ambas as aplicações, de modo a que a partir do mesmo menu pudesse ser iniciada a aplicação **Leitor de Cartões** e o sistema pudesse ser gerido.

Assim, foi criado um menu em modo terminal com as opções:
1. **Iniciar aplicação 1** 
2. **Importar dados dos utilizadores**
3. **Justificar Falta**
4. **Mostrar relatório de faltas**
5. **Consultar faltas por aluno**
6. **Mostrar turma**
7. **Sair do programa**


De seguida, serão apresentadas quais as classes que foram criadas (uma vez que este foi um projeto desenvolvido na linguagem JAVA), quais os métodos incluídos em cada uma delas e quais as funções que estes ocupam para que o sistema funcione como desejado.

Foram implementadas as seguintes classes:
* GestorPresencasMenu;
* Aula;
* Leitor;
* Presenca;
* Utilizador;

##### GestorPresencasMenu 

Esta é a classe principal, onde foram implementadas todas as ferramentas necessárias para a importação dos dados JSON fornecidos, tendo estes sido utilizados para testes; onde foi implementado todo o menu que surge como output, representando a aplicação final pretendida, e onde foram ainda implementados alguns métodos. 

Admitindo que o utilizador já esteja autenticado no SIIUE, surge o menu com 7 opções, e são elas:

* Ir a aplicação 1 - pressione 1
   * O utilizador, ao pressionar a tecla 1, pode aceder a um horário onde estão expostas todas as aulas de MDS que ainda não foram lecionadas, para que este possa escolher a data da aula em que quer marcar a sua presença, fornecendo o número do seu cartão para que tal se realize. Antes que possam ser marcadas presenças, o utilizador tem de pressionar a tecla 2 para poder importar os dados, caso contrário não existem dados para que o sistema funciona como desejado. Assim, após os dados serem importados devidamente, o utilizador pode então selecionar a data da aula e o número do seu cartão para que a presença seja marcada (se a aula digitada pelo utilizador não existir, surge uma mensagem de erro). Se todo o input estiver correto, é então chamado o método "*initApp1*" para que a presença seja registada.

* Importar dados dos utilizadores - pressione 2
   * O utilizador, ao pressionar a tecla 2, importa todos os dados necessários ao bom funcionamento do sistema, tais como as datas e as horas de cada aula, o nome e número de cartão dos diferentes utilizadores (o docente e todos os discentes). O utilizador tem de digitar corretamente o nome do ficheiro que pretende importar e este tem de estar na diretoria correta. Caso isto aconteça, é feita a chamada ao método "*import_user_data*" que irá verificar se o ficheiro inserido está vazio, se ocorreu algum erro como o ficheiro não ter sido encontrado, se ocorreu alguma falha ao tentar abri-lo, ou até mesmo se os dados deste ficheiro já foram outrora importados. Caso nenhum destes casos ocorra, então este método dá procedimento à importação dos dados.

* Justificar Falta - pressione 3
   * Ao pressionar a tecla 3, o utilizador pode justificar a falta de alguma aula, para que esta falta não seja contabilizada. Assim, o utilizador digita o número do seu cartão e a aula sobre a qual pretende justificar a sua falta, e o sistema encarrega-se de utilizar o método "*alterarPresenca*"(método este pertencente à classe Presenca), onde vai ser verificado se o aluno existe, se a aula foi efetivamente lecionada e se o aluno já tem presença nessa aula. Caso nenhuma destas situações se verifique, então a presença do aluno nessa aula será alterada como era pretendido.

* Mostrar relatório de faltas - pressione 4
   * Quando o utilizador pressiona a tecla 4, é feita a chamada ao método "*mostrarRelatorio*"(da classe Aula), sendo este responsável por apresentar ao utilizador as aulas lecionadas e o número de faltas correspondente.

* Consultar faltas por utilizador - pressione 5
   * O utilizador pode consultar quantas faltas já obteve premindo a tecla 5. Aqui o utilizador necessita inserir o número do seu cartão, para que seja identificado, e de seguida será então apresentado o número de faltas já marcadas.

* Mostrar turma - pressione 6
   * Se o utilizador pretender consultar toda a turma (nome dos alunos e respetivos números de cartões), pode fazê-lo pressionando a tecla 6. Será então invocado o método "*showClass*" que constrói e expõe a informação já referida em modo de tabela, de modo a facilitar a leitura e a extração de informação por parte do utilizador do sistema.

* Sair do programa - pressiona X
   * Quando o utilizador pretender sair do sistema, apenas tem de pressionar a tecla X.

###### Métodos

* **parseUserObject** - este método recebe e analisa um JSONObject, de modo a criar um objeto do tipo *Utilizador* com os atributos designados.

* **parseLectureObject** - este método recebe e analisa um JSONObject, de modo a criar um objeto do tipo *Aula* com os atributos designados.

* **import_user_data** - tem como função importar todos os utilizadores do ficheiro JSON e guarda-os numa lista. Caso os dados já tenham sido importados e ainda assim o método é invocado novamente, este identifica o "erro" e informa o utilizador de que os dados já foram importados.

* **import_classes_data** - tem como função importar todas as aulas do ficheiro JSON e guarda-as numa lista.

* **mostrarHorario** - este método recebe como argumento a lista de todas as aulas da disciplina e expõe para o utilizador todas aquelas que ainda não foram lecionadas.

* **showClass** - este método mostra, em modo de tabela, todos os alunos da turma, com os seus nomes e respetivos números de cartão, verificando previamente se a turma contêm realmente alunos ou não(se os dados já foram ou não importados).

* **initApp1** - este método é invocado quando se pretende marcar uma presença de algum utilizador numa determinada aula. Recebe como argumento um objeto do tipo "*Aula*", e enquanto o utilizador pretender marcar presenças, o sistema pede para que este insira o número do cartão relativo à pessoa que pretende marcar a presença e em que aula. Sempre que se pretende registar uma presença, este método invoca um outro método, "*regista*"(da classe *Leitor*), método esse que vai efetivamente realizar o registo da presença. Caso o utilizador pretenda parar de registar presenças, pode a qualquer momento premir a tecla X, e instantaneamente é invocado o método "*resetLeitor*"(da classe *Leitor*) para que se dê por terminados os registos de presenças naquela aula, entre outras ações realizadas que serão mais à frente especificadas.

##### Aula
Nesta classe foram inicializadas algumas variáveis, de modo a que sempre que fosse criado um novo objeto do tipo *Aula* esse objeto possuí-se tais atributos, como uma data (String), uma hora(String), um número de presenças(int) e um registo de aula lecionada ou não lecionada(boolean).

###### Métodos

* **Setters e Getters** - esclusivos para cada variável que constitui o objeto *Aula*.

* **mostrarRelatorio** - este método, como já foi acima referido, apresenta todas as aulas e respetivo número de faltas dos alunos. 

##### Leitor
Nesta classe, à semelhança do que acontece também na classe *Aula*, por exemplo, são também criadas algumas variáveis para que, quando for criado um novo objeto deste tipo, esse objeto possua estes atributos, atributos esses que são uma aula(do tipo *Aula*) associada e uma memória temporária(ArrayList) onde serão guardados os utilizadores que passaram o cartão antes do docente, para que a sua presença seja registada assim que o docente passar o cartão no leitor, prevenindo assim que a sua presença seja precocemente marcada e depois esta tenha que ser anulada caso o docente falte, pois não se irá marcar uma presença se a aula não for lecionada.

###### Métodos

* **regista** - este método é invocado na classe *GestorPresencasMenu* e tem como objetivo marcar uma presença de um utilizador numa aula. Para isso, recebe como argumentos o utilizador em causa e a aula sobre a qual se pretende marcar presença. Inicialmente é utilizado um outro método *verificaPresenca* que vai verificar se o utilizador já tem presença marcada naquela aula. Caso tal ocorra, o utilizador é avisado de que já está presente naquela aula, caso contrário, é verificado se o utilizador que pretende registar a sua presença é o docente ou não. Se for o docente, então o estado da aula passa a ser "lecionada" e marca-se presença ao docente e a todos os alunos que já passaram o cartão antes da sua chegada, durante aquela aula, caso estes existam. Se for um aluno que pretenda registar a sua presença, verifica-se se o docente já está presente: se estiver, a presença é diretamente marcada ao aluno, caso contrário, os seus dados serão guardados numa memória temporária(ArrayList) para que a sua presença possa ser mais tarde marcada caso o docente venha a estar presente na aula em questão.

* **verificaPresenca** - como já foi acima referido, este método recebe como argumento um utilizador e verifica se o mesmo já está presente na aula em questão.

* **resetLeitor** - Sempre que o utilizador pretenda parar os registos de presenças numa aula e pressionar a tecla X, como já foi acima explicado, este método tem como função dar por encerrada a ação de registo de presenças. Assim, começa por verificar se a aula (que recebe como argumento) foi lecionada (utilizando o método *isLecionada* da classe *Aula*) ou não. Caso tenha sido lecionada, então o número de aulas lecionadas é incrementado, e se a aula não tiver sido lecionada, é porque o docente faltou, logo, é adicionada uma falta ao docente. Por fim, é limpa toda a memória temporária utilizada para guardar como o próprio nome indica, temporariamente, os utilizadores que tenham passado o cartão antes da chegada do docente.

##### Presenca
Na classe Presenca foram criados um utilizador(do tipo *Utilizador*) e uma aula(do tipo *Aula*) para que a cada objeto *Presenca* seja atribuído um utilizador e uma aula.
###### Métodos

* **Setters e Getters** - exclusivos para cada variável que constitui o objeto *Presenca*.

* **marcaPresenca** - este método recebe um utilizador e uma aula como argumentos, e tem como propósito marcar presenças. É aqui que vai ser feita a verificação de se já existe uma lista para a aula em questão para que possam ser introduzidas presenças de utilizadores, ou se essa lista ainda não existe e tem de ser criada, e apenas posteriormente serem lá introduzidas as presenças dos utilizadores. No fim, é incrementado o número de presenças daquela aula e o número de presenças do utilizador.

* **alterarPresenca** - o método *alterarPresenca* tem a função de alterar a presença de um utilizador. Tecnicamente, este método só irá ser invocado quando um utilizador faltou a uma aula e pretende justificar a sua falta, retirando a sua falta e adicionando a sua presença. Recebe como argumento um utilizador e uma aula, e a primeira coisa que verifica é se o utilizador ou a aula existem. Se algum destes dois não existir, é emitida uma mensagem de erro. Caso contrário, é posteriormente verificado se o utilizador pretende justificar a falta a uma aula à qual apenas o docente compareceu, ou seja, ainda não existe nenhuma lista de presenças para essa aula e tem de ser criada, para de seguida ser adicionada a presença do aluno, e consequentemente retirada a sua falta. Além disto, ainda é verificado se o utilizador está a tentar justificar a falta a uma aula na qual já tem presença marcada, e nesse caso surge uma mensagem de erro que avisa o utilizador de que já está presente. Por último, a presença é alterada (marcada) caso não o tenha sido antes, e é adicionada uma presença à aula em questão.

##### Utilizador
Sendo esta a última classe aqui especificada, mas não menos importante, a classe *Utilizador* contêm todas as utilizações referentes a um utilizador do sistema, como o nome, número de cartão, o seu papel(se é docente ou aluno) e o número de presenças. Todo o objeto do tipo *Utilizador* criado terá estes atributos.

###### Métodos
* **Setters e Getters** - exclusivos para cada variável que constitui o objeto *Utilizador*.

#### Estruturas utilizadas
Nesta implementação deste sistema, foram usadas dois tipos de estruturas: **HashMap** e **ArrayList**.

A **HashMap** é uma estrutura que facilita a procura de um value utilizando uma key, o que faz com que o sistema tenha uma maior fluidez.
No value da **HashMap** foi colocado uma **ArraysList** de utilizadores presentes na aula e como key a data dessa aula lecionada.

Foram inicializados 3 **ArrayList**, uma para todos os utilizadores do sistema(do tipo *Utilizador*), uma para todas as aulas existentes(do tipo *Aula*) e outra para as aulas lecionadas(do tipo *Aula*).
A utilidade destas **ArrayList's** é guardar objetos para quando for necessário, pesquisar se existe alguma informação, tendo no fundo uma base de dados.

### Testes
No início da implementação do sistema foi decidido não utilizar TDD como metodologia, uma vez que é uma metodologia que exige naturalmente alguma experiência e porque não houve propriamente uma facilidade na visualização do código de antemão, o que levaria a um processo, digamos, interminável, de refactoring de código. Por estes motivos foi então decidido iniciar a implementação e realizar testes unitários à medida que esta ia sendo feita, realizando no mínimo um teste para cada tarefa. 

Os testes JUnit servem, basicamente, para testar se cada método faz o que é suposto fazer da forma que é suposto fazer.
Foram criadas diversas classes de testes, cada uma para cada classe da implementação. Em cada classe de teste foi testado cada método da classe da implementação correspondente, e apenas se todos os métodos passarem em todos os testes, então aí, e só aí, podemos afirmar que a classe realiza as funções para a qual foi concebida.

### Conclusão
Finalmente, com a implementação concluída, foi possível concluir que o maior adversário no desenvolvimento deste projeto foi trabalhar com uma *HashMap*. Foi decidido utilizar uma *HashMap* por forma a tentar reduzir a complexidade do código e de maneira a que o sistema se tornasse mais funcional. Ficou assim retida informação importante, para que num desenvolvimento de um projeto futuro, este material possa ser usado com mais facilidade e até poder ser obtido maior proveito dele.

Além disso, este foi também um projeto realizado remotamente, o que permitiu a cada um dos developers adquirir maior experiência no trabalho em grupo remoto, utilizando como ferramenta essencial o Git. Em paralelo, foi ainda possível adquirir experiência ao trabalhar com testes unitários, algo que será muito útil no desenvolvimento de novos projetos.

Por fim, com este trabalho foi também possível absorver mais conhecimento e prática na escrita de código e até mesmo na resolução de diferentes problemas que foram surgindo no decorrer do projeto. Podemos assim considerar que foi construído, como pedido, um sistema funcional, bem sintetizado e simples, demonstrando objetivamente os conceitos e conteúdos interiorizados no decorrer das aulas.
