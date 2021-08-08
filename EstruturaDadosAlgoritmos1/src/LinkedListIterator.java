public class LinkedListIterator<E> implements  java.util.Iterator<E> {

    SingleNode<E> current;



    public LinkedListIterator(SingleNode<E> c){

        current = c;
    }
    public boolean hasNext() {
        return current!=null;
    }

    public E next() {
        if (hasNext()) {

            E x = current.element();
            current = current.getNext();
            return x;
        }
        throw new InvalidNodeException("Deu erro");
    }
    public void remove() {

        throw new UnsupportedOperationException("deu Erro!");
    }
}
