public class SingleNode<T> {

    private T current;
    private SingleNode<T> nextNode;

    public SingleNode(T x){
        this(x,null);

    }
    public SingleNode(){
        this(null);

    }
    public SingleNode(T x, SingleNode<T> n){
        current = x;
        nextNode = n;
    }


    public T element() throws InvalidNodeException{
        if (this == null){
            throw new InvalidNodeException("Nó Inexistente");
        }
        return current;
    }
    public SingleNode<T> getNext(){
        if(nextNode!=null){
            return nextNode;
        }
        return null;
    }
    public void setElement(T x){

        current = x;
    }
    public void setNext(SingleNode<T> n)
    {
        nextNode = n;
    }

    public boolean equals(Object o){
        T teste = (T) o;
        return current.equals(teste);
    }
}
