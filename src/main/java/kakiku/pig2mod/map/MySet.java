package kakiku.pig2mod.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class MySet<E> implements Set<E> {
    private Set<E> mySet;
    private Class<?> mOwnerClass = null;
    private static final String OWNER_PersistentEntitySectionManager = "PersistentEntitySectionManager";
    private static final String OWNER_ServerLevel = "ServerLevel";
    private static final String OWNER_MyInt2ObjectOpenHashMap = "MyInt2ObjectOpenHashMap";
    private static String OWNER_ChunkMap__TrackedEntity = "";

    public MySet(Set<E> set, Class<?> ownerClass) {
        this.mySet = set;
        this.mOwnerClass = ownerClass;
        if (ownerClass.getName().equals("net.minecraft.server.level.ChunkMap$TrackedEntity")) {
            OWNER_ChunkMap__TrackedEntity = ownerClass.getSimpleName();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (MyLib2.getCallerClass1() == this.mOwnerClass
            || this.mOwnerClass.getSimpleName().equals("ServerLevel")
                && MyLib2.getCallerClass1().getName().equals("net.minecraft.server.level.ServerLevel$EntityCallbacks")
            || this.mOwnerClass.getSimpleName().equals("ServerLevel") && !(o instanceof Pig2) && !(o instanceof Player)) {
            return this.mySet.remove(o);
        } else {
            if (this.mOwnerClass.getSimpleName().equals("PersistentEntitySectionManager")
                && o instanceof UUID uuid
                && !Pig2.getAliveServerPigUUIDs(null).contains(uuid)) {
                return this.mySet.remove(o);
            }

            return false;
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
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
    public boolean add(E e) {
        return this.mySet.add(e);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        return this.mySet.addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        final Iterator<E> it = this.mySet.iterator();
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

    @Override
    public boolean equals(Object o) {
        return this.mySet.equals(o instanceof MySet ? ((MySet)o).mySet : o);
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        this.mySet.forEach(action);
    }

    @Override
    public int size() {
        return this.mySet.size();
    }

    @Override
    public boolean isEmpty() {
        return this.mySet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.mySet.contains(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return this.mySet.containsAll(c);
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return this.mySet.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return (T[])this.mySet.toArray(a);
    }

    @Override
    public int hashCode() {
        return this.mySet.hashCode();
    }
}
