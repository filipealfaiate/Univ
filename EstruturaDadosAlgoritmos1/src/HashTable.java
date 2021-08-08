public abstract class HashTable<T> {

    int size;
    Elemento<T>[] tabela;
    int nElementos = 0;

    public int ocupados() {
        return nElementos;
    }

    public float factorCarga() {
        return (float) nElementos / tabela.length;
    }

    protected abstract int procPos(T s);

    public void alocarTabela(int dim) {
        size = getNextPrime(dim);
        tabela = new Elemento[size];

    }

    public void tornarVazia() {

        nElementos = 0;

        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = null;
        }
    }

    public T procurar(T elemento) {

        int index = procPos(elemento);

        if (tabela[index] != null && tabela[index].isActive() && tabela[index].getElemento().equals(elemento)) {
            return tabela[index].getElemento();
        } else {
            return null;
        }
    }

    public void remove(T elemento) {
        int index = procPos(elemento);

        if (tabela[index].getElemento().equals(elemento)) {
            tabela[index].setActive(false);
            nElementos--;
        }

    }

    public void insere(T elemento) {

        if (procurar(elemento) == null) {

            int index = procPos(elemento);

            tabela[index] = new Elemento<>(elemento);

            nElementos++;

            if (factorCarga() >= 0.6) {
                rehash();
            }
        }
    }

    public void rehash() {


        Elemento<T>[] tabelaVelha = tabela;

        alocarTabela(2 * tabela.length);

        tornarVazia();

        for (int i = 0; i < tabelaVelha.length; i++) {

            if (tabelaVelha[i] != null && tabelaVelha[i].isActive()) {
                insere(tabelaVelha[i].getElemento());

            }
        }

    }

    public void print() {
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null && tabela[i].isActive()) {
                System.out.println(tabela[i].getElemento());
            }
        }
    }

    public int getNextPrime(int size) {

        size++;
        while (!isPrime(size)) {
            size++;
        }
        return size;

    }

    public int getPreviousPrime(int size) {
        size--;
        while (!isPrime(size) && size > 0) {
            size--;
        }
        return size;
    }

    boolean isPrime(int M) {
        for (int i = 2; i < M; i++)
            if (M % i == 0)
                return false;
        return true;
    }

}
