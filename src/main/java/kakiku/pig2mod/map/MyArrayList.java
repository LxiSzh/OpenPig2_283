package kakiku.pig2mod.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ClassInstanceMultiMap;

public class MyArrayList<E> extends ArrayList<E> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_ClassInstanceMultiMap = "ClassInstanceMultiMap";
    private final String OWNER_MyHashMap = "MyHashMap";
    private final String OWNER_ServerLevel = "ServerLevel";
    private final String OWNER_ClientLevel = "ClientLevel";
    private boolean mDerivedConstructed = false;

    public MyArrayList(List<E> list, Class<?> ownerClass) {
        super(list);
        this.mOwnerClass = ownerClass;
    }

    protected void setDerivedConstructed() {
        this.mDerivedConstructed = true;
        this.mOwnerClass = this.getClass();
    }

    protected boolean isDerivedConstructed() {
        return this.mDerivedConstructed;
    }

    public MyArrayList() {
    }

    public MyArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public MyArrayList(Collection<? extends E> c) {
        super(c);
    }

    @Override
    public E remove(int index) {
        return this.isDerivedConstructed() ? super.remove(index) : null;
    }

    @Override
    public boolean remove(Object object) {
        if (this.isDerivedConstructed()) {
            return super.remove(object);
        } else {
            Class<?> caller1Class = MyLib2.getCallerClass1();
            return caller1Class != this.mOwnerClass
                    && (this.mOwnerClass != MyHashMap.class || caller1Class != ClassInstanceMultiMap.class)
                    && (this.mOwnerClass != ClientLevel.class || !caller1Class.getName().equals("net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks"))
                    && (this.mOwnerClass != ServerLevel.class || !caller1Class.getName().equals("net.minecraft.server.level.ServerLevel$EntityCallbacks"))
                    && (!(object instanceof LocalPlayer) || MyLib2.isThisOtherBadMOD(caller1Class))
                ? false
                : super.remove(object);
        }
    }

    @Override
    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        }
    }

    @Override
    public E set(int index, E element) {
        return this.isDerivedConstructed() ? super.set(index, element) : this.get(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.isDerivedConstructed() ? super.removeAll(c) : false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.isDerivedConstructed() ? super.retainAll(c) : false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return this.isDerivedConstructed() ? super.removeIf(filter) : false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        if (this.isDerivedConstructed()) {
            super.replaceAll(operator);
        }
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (this.isDerivedConstructed()) {
            super.removeRange(fromIndex, toIndex);
        }
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return (List<E>)(this.isDerivedConstructed() ? super.subList(fromIndex, toIndex) : new ArrayList<>(super.subList(fromIndex, toIndex)));
    }

    @Override
    public Iterator<E> iterator() {
        if (this.isDerivedConstructed()) {
            return super.iterator();
        } else {
            final Iterator<E> it = super.iterator();
            return new Iterator<E>() {
                E last;

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public E next() {
                    this.last = it.next();
                    return this.last;
                }

                @Override
                public void remove() {
                }
            };
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        if (this.isDerivedConstructed()) {
            return super.listIterator();
        } else {
            final ListIterator<E> it = super.listIterator();
            return new ListIterator<E>() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public E next() {
                    return it.next();
                }

                @Override
                public boolean hasPrevious() {
                    return it.hasPrevious();
                }

                @Override
                public E previous() {
                    return it.previous();
                }

                @Override
                public int nextIndex() {
                    return it.nextIndex();
                }

                @Override
                public int previousIndex() {
                    return it.previousIndex();
                }

                @Override
                public void remove() {
                    it.remove();
                }

                @Override
                public void set(E e) {
                    it.set(e);
                }

                @Override
                public void add(E e) {
                    it.add(e);
                }
            };
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.isDerivedConstructed() ? super.spliterator() : Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }
}
