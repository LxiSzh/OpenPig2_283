package kakiku.pig2mod.map;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.TickingTracker;
import net.minecraft.util.SortedArraySet;

public class MySortedArraySet<T> extends SortedArraySet<T> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_MyLong2ObjectOpenHashMap = "MyLong2ObjectOpenHashMap";
    private boolean mDerivedConstructed = false;

    public MySortedArraySet(SortedArraySet<T> sortedArraySet, Class<?> ownerClass) {
        super(4, sortedArraySet.comparator);
        this.mOwnerClass = ownerClass;
        this.addAll(sortedArraySet);
    }

    protected void setDerivedConstructed() {
        this.mDerivedConstructed = true;
        this.mOwnerClass = this.getClass();
    }

    protected boolean isDerivedConstructed() {
        return this.mDerivedConstructed;
    }

    public MySortedArraySet(int pInitialCapacity, Comparator<T> pComparator) {
        super(pInitialCapacity, pComparator);
    }

    public boolean remove(Object pElement) {
        if (this.isDerivedConstructed()) {
            return super.remove(pElement);
        } else {
            Class<?> caller1Class = MyLib2.getCallerClass1();
            return caller1Class != this.mOwnerClass && caller1Class != DistanceManager.class && caller1Class != TickingTracker.class
                ? false
                : super.remove(pElement);
        }
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return this.isDerivedConstructed() ? super.removeIf(filter) : false;
    }

    public boolean removeAll(Collection<?> c) {
        return this.isDerivedConstructed() ? super.removeAll(c) : false;
    }

    public boolean retainAll(Collection<?> c) {
        return this.isDerivedConstructed() ? super.retainAll(c) : false;
    }

    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        }
    }

    public Iterator<T> iterator() {
        if (this.isDerivedConstructed()) {
            return super.iterator();
        } else {
            final Iterator<T> it = super.iterator();
            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public T next() {
                    return it.next();
                }

                @Override
                public void remove() {
                    Class<?> caller1Class = MyLib2.getCallerClass1();
                    if (caller1Class == MySortedArraySet.this.mOwnerClass || caller1Class == DistanceManager.class) {
                        it.remove();
                    }
                }
            };
        }
    }

    public Spliterator<T> spliterator() {
        return this.isDerivedConstructed() ? super.spliterator() : Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }
}
