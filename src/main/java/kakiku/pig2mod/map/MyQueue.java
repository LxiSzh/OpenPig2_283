package kakiku.pig2mod.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import kakiku.pig2mod.xform.MyLib2;
import org.jetbrains.annotations.NotNull;

public class MyQueue<E> implements Queue<E> {
    private final Queue<E> myQueue;
    private Class<?> mOwnerClass = null;
    private final String OWNER_PersistentEntitySectionManager = "PersistentEntitySectionManager";

    public MyQueue(Queue<E> queue, Class<?> ownerClass) {
        this.myQueue = queue;
        this.mOwnerClass = ownerClass;
    }

    @Override
    public E remove() {
        return MyLib2.getCallerClass1() == this.mOwnerClass ? this.myQueue.remove() : null;
    }

    @Override
    public E poll() {
        return MyLib2.getCallerClass1() == this.mOwnerClass ? this.myQueue.poll() : null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeIf(@NotNull Predicate<? super E> filter) {
        return false;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> it = this.myQueue.iterator();
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return it.next();
            }

            @Override
            public void remove() {
            }
        };
    }

    @NotNull
    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.iterator(), (long)this.size(), 0);
    }

    @Override
    public boolean add(E e) {
        return this.myQueue.add(e);
    }

    @Override
    public boolean offer(E e) {
        return this.myQueue.offer(e);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return this.myQueue.addAll(c);
    }

    @Override
    public E element() {
        return this.myQueue.element();
    }

    @Override
    public E peek() {
        return this.myQueue.peek();
    }

    @Override
    public int size() {
        return this.myQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.myQueue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.myQueue.contains(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return this.myQueue.containsAll(c);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return this.myQueue.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return (T[])this.myQueue.toArray(a);
    }
}
