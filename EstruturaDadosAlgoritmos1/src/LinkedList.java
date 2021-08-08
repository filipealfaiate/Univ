
public class LinkedList<T> implements Iterable<T>{

    int size=0;
    SingleNode<T> inicio = new SingleNode<>(); //dummy node




    public java.util.Iterator<T> iterator(){
        return new LinkedListIterator<>(inicio.getNext());
    }
    public int size(){

        return size;
    }

    public boolean isEmpty(){

        return size==0;
    }

    public SingleNode<T> header(){
        return inicio;
    }

    public void add(int i, T x){

        SingleNode<T> node = inicio;

        for(int j=-1; j<i-1; j++){
            node = node.getNext();
        }
        SingleNode<T> m = new SingleNode<>(x, node.getNext());
        node.setNext(m);
        size++;
    }

    void add(T x){
        add(size, x);
    }


    //remove o nó seguinte a prev
    void remove(SingleNode<T> prev){

        SingleNode<T> node = inicio;
        while(node!=null){
            if(node==prev){
                node.setNext(node.getNext().getNext());
            }
            node = node.getNext();
        }
        size--;
    }

    public void remove(int ind){
        SingleNode<T> node = inicio;
        for(int j=-1; j<ind-1; j++){
            node = node.getNext();
        }
        remove(node);

    }

    SingleNode<T> findPrevious(T x) {
        SingleNode<T> node = inicio;
        while(node.getNext()!=null){
            if(node.getNext().element().equals(x)){
                return node;
            }
            node = node.getNext();
        }
        return node;
    }

    public void remove(T x){
        SingleNode<T> node = inicio;

        while(node.getNext()!=null){
            if(node.getNext().element().equals(x)){
                remove(node);
            }
            node = node.getNext();
        }

    }

    public String toString(){

        String f = "[";
        int i=0;
        for(T x: this){

            f += (i++<size-1)? x + "--> ": x;
        }
        return f +"]";
    }

    public void set(int indx, T x){
        SingleNode<T> node = inicio;
        for(int j=-1; j<indx-1; j++){
            node = node.getNext();
        }
        node.setElement(x);
    }

    public boolean contains(T o){

        SingleNode<T> node = inicio.getNext();

        while(node!=null){

            if(node.element().equals(o)){
                return true;
            }
            node = node.getNext();
        }
        return false;
    }
}
