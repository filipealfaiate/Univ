public class Elemento<T> {
    boolean active;
    T elemento;

    public Elemento(T elemento) {
        active = true;
        this.elemento = elemento;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public T getElemento() {
        return elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }
}
