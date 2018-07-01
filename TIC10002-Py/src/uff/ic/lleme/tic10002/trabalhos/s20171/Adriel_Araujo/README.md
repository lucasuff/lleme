# ESTRUTURAS UTILIZADAS #

### Principais estruturas utilizadas ###

Sabendo que, de acordo com o enunciado do trabalho, buscas em intervalos são requeridas, optou-se pela implementação de
Arvores Binárias (Balanceadas - AVL) para as buscas solicitadas nas questões 1 e 3. Este tipo de estrutura foi escolhida
pois minimiza a busca convencional para **O(log(N))**.

Quando se faz necessário uma busca com 2 intervalos distintos (datas e filiais) deve-se incrementar estas implementações ou
criar implementações adicionais. Partindo do princípio onde esta situação é resolvida com uma operação de interseção (*"a conjunção
de condições sobre filiais e datas devem ser realizadas por meio de operação de interseção de conjuntos"* - Enunciado do trabalho),
essa implementação utiliza uma estrutura de Hash e realiza a interseção através de um Join (*Hash Join*). Esta estrutura minimiza a
complexidade de uma interseção convencional com custo O(N x M) para **O(N + M)**, onde N e M são quantidade de vendas por Filias e
Datas, respectivamente

### Demais estruturas ###

Como mencionado, optou-se pela implementação de Arvores AVL e HASHs como principais estruturas para este trabalho.
Para melhor organização e acesso dos dados, outras estruturas  e classes foram implementadas. São elas:

* Sale: Encapsula os valores lidos do arquivo em uma instancia no programa. Cada linha se torna um objeto Sale
* ListNode: Encapsula um objeto Sale com dois ponteiros (proximo e anterior) a serem usados por uma lista.
* List: Encapsula varios objetos do tipo ListNode, isto é, cria uma lista dinâmica das vendas
* Tree: Cria uma árvore binária balanceada (AVL) onde cada nó contem uma List de objetos vendas.
Ex. Nó da filial 10 contem uma List com as vendas desta filial
* Hash: Cria um Hash com tratamento de colisões e implementa a operação de join.
* Main: Programa que inicia a leitura do arquivos e instanciação das classes. Além disso implementa o método search para
abstrair a busca e torná-la genérica


### Obsservações adicionais ###

Detalhes de entrada dos dados para a busca

* O padrão de entrada adotado nesta implementação para a busca de vendas por data é um conjunto de duas strings no seguinte
formato: YYYY_MM. Ex.: self.search(min_date='2016_02', max_date='2017_08')

* O padrão de entrada adotado nesta implementação para a busca de vendas por filial é um conjunto de dois inteiros
Ex.: self.search(min_filial=10, max_filial=15)

* O padrão de entrada adotado nesta implementação para a busca de vendas por filial e data simultaneamente é um conjunto de
dois inteiros e duas strings. Ex.: self.search(min_filial=10, max_filial=15, min_date='2016_02', max_date='2017_08')