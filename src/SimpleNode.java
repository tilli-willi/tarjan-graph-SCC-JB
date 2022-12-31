import java.util.List;

public class SimpleNode<T> implements Node<T> {
    private final T value;

    private final List<Node<T>> neighbors;

    public SimpleNode(T value, List<Node<T>> neighbors) {
        this.value = value;
        this.neighbors = neighbors;
    }

    public void addNeighbor(Node<T> node) {
        this.neighbors.add(node);
    }

    @Override
    public List<Node<T>> adjacents() {
        return neighbors;
    }

    @Override
    public T getPayload() {
        return value;
    }
}
