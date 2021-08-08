public class Hash<T> extends HashTable<T> {

    public Hash(int dim) {

        tabela = new Elemento[getNextPrime(dim)];
        size = tabela.length;
    }

    protected int procPos(T s) {

        int indexH = Math.abs(s.hashCode() % size);

        if (indexH >= tabela.length) {
            indexH = indexH % tabela.length;
        }

        while (tabela[indexH] != null && !tabela[indexH].getElemento().equals(s)) {

            indexH++;   //linear

            if (indexH >= tabela.length) {
                indexH = indexH % tabela.length;
            }
        }

        return indexH;
    }
}
