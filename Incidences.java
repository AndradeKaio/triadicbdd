public class Incidences<T, U, V> {

    private final T object;
    private final U attribute;
    private final V condition;

    public Incidences(T object, U attribute, V condition) {
        this.object = object;
        this.attribute = attribute;
        this.condition = condition;
    }

    public T getObject() { return object; }
    public U getAttribute() { return attribute; }
    public V getCondition() { return condition; }


}