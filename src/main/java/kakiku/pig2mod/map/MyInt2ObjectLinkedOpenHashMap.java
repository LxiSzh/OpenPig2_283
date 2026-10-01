package kakiku.pig2mod.map;

import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap.BasicEntry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap.FastSortedEntrySet;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import kakiku.pig2mod.MyHelper;
import kakiku.pig2mod.entity.Pig2;
import kakiku.pig2mod.xform.MyLib2;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MyInt2ObjectLinkedOpenHashMap<V> extends Int2ObjectLinkedOpenHashMap<V> {
    private Class<?> mOwnerClass = null;
    private final String OWNER_EntityLookup = "EntityLookup";
    private final String OWNER_EntityTickList = "EntityTickList";
    private boolean mDerivedConstructed = false;

    public MyInt2ObjectLinkedOpenHashMap(Int2ObjectMap<V> map, Class<?> ownerClass) {
        this.mOwnerClass = ownerClass;
        this.defRetValue = map.defaultReturnValue();
        super.putAll(map);
    }

    protected void setDerivedConstructed() {
        this.mDerivedConstructed = true;
        this.mOwnerClass = this.getClass();
    }

    protected boolean isDerivedConstructed() {
        return this.mDerivedConstructed;
    }

    public MyInt2ObjectLinkedOpenHashMap() {
    }

    public MyInt2ObjectLinkedOpenHashMap(int expected, float f) {
        super(expected, f);
    }

    public MyInt2ObjectLinkedOpenHashMap(int expected) {
        super(expected);
    }

    public MyInt2ObjectLinkedOpenHashMap(Map<? extends Integer, ? extends V> m, float f) {
        super(m, f);
    }

    public MyInt2ObjectLinkedOpenHashMap(Map<? extends Integer, ? extends V> m) {
        super(m);
    }

    public MyInt2ObjectLinkedOpenHashMap(Int2ObjectMap<V> m, float f) {
        super(m, f);
    }

    public MyInt2ObjectLinkedOpenHashMap(Int2ObjectMap<V> m) {
        super(m);
    }

    public MyInt2ObjectLinkedOpenHashMap(int[] k, V[] v, float f) {
        super(k, v, f);
    }

    public MyInt2ObjectLinkedOpenHashMap(int[] k, V[] v) {
        super(k, v);
    }

    public V put(int k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)(this.get(k) != this.defRetValue && MyLib2.getCallerClass1() != this.mOwnerClass ? this.defRetValue : super.put(k, v));
        }
    }

    public V put(Integer k, V v) {
        if (this.isDerivedConstructed()) {
            return (V)super.put(k, v);
        } else {
            return (V)((MyLib2.getCallerClass1() == this.mOwnerClass || !(this.get(k) instanceof Pig2))
                    && this.mOwnerClass.getSimpleName().equals("EntityLookup")
                ? super.put(k, v)
                : this.defRetValue);
        }
    }

    public void putAll(Map<? extends Integer, ? extends V> m) {
        if (this.isDerivedConstructed()) {
            super.putAll(m);
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.putAll(m);
        }
    }

    public V putIfAbsent(int key, V value) {
        if (this.isDerivedConstructed()) {
            return (V)super.putIfAbsent(key, value);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.putIfAbsent(key, value) : this.defRetValue);
        }
    }

    public V remove(int k) {
        if (this.isDerivedConstructed()) {
            return (V)super.remove(k);
        } else {
            return (V)(MyLib2.getCallerClass1() != this.mOwnerClass && (this.get(k) instanceof Pig2 || this.get(k) instanceof Player)
                ? this.defRetValue
                : super.remove(k));
        }
    }

    public boolean remove(int k, Object v) {
        if (this.isDerivedConstructed()) {
            return super.remove(k, v);
        } else {
            return MyLib2.getCallerClass1() != this.mOwnerClass && MyLib2.getCallerClass1() != MyHelper.class ? false : super.remove(k, v);
        }
    }

    public V remove(Object key) {
        return (V)(this.isDerivedConstructed() ? super.remove(key) : this.defRetValue);
    }

    public void clear() {
        if (this.isDerivedConstructed()) {
            super.clear();
        } else if (MyLib2.getCallerClass1() == this.mOwnerClass) {
            super.clear();
        }
    }

    public V compute(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.compute(k, remappingFunction) : super.get(k));
    }

    public V computeIfPresent(int k, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfPresent(k, remappingFunction) : super.get(k));
    }

    public V computeIfAbsent(int k, IntFunction<? extends V> mappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.computeIfAbsent(k, mappingFunction) : super.get(k));
    }

    public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
        if (this.isDerivedConstructed()) {
            return (V)super.computeIfAbsent(key, mappingFunction);
        } else {
            return (V)(MyLib2.getCallerClass1() == this.mOwnerClass ? super.computeIfAbsent(key, mappingFunction) : super.get(key));
        }
    }

    public V merge(int k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return (V)(this.isDerivedConstructed() ? super.merge(k, v, remappingFunction) : super.get(k));
    }

    public boolean replace(int k, V oldValue, V v) {
        return this.isDerivedConstructed() ? super.replace(k, oldValue, v) : false;
    }

    public V replace(int k, V v) {
        return (V)(this.isDerivedConstructed() ? super.replace(k, v) : super.get(k));
    }

    public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
        if (this.isDerivedConstructed()) {
            super.replaceAll(function);
        }
    }

    public ObjectCollection<V> values() {
        return (ObjectCollection<V>)(this.isDerivedConstructed() ? super.values() : new ObjectArrayList(super.values()));
    }

    public FastSortedEntrySet<V> int2ObjectEntrySet() {
        return (FastSortedEntrySet<V>)(this.isDerivedConstructed()
            ? super.int2ObjectEntrySet()
            : new MyInt2ObjectLinkedOpenHashMap.MySnapshotEntrySet(super.int2ObjectEntrySet()));
    }

    public ObjectSortedSet<java.util.Map.Entry<Integer, V>> entrySet() {
        return (ObjectSortedSet<java.util.Map.Entry<Integer, V>>)(this.isDerivedConstructed() ? super.entrySet() : this.int2ObjectEntrySet());
    }

    public IntSortedSet keySet() {
        return (IntSortedSet)(this.isDerivedConstructed() ? super.keySet() : new MyInt2ObjectLinkedOpenHashMap.MySnapshotKeySet(super.keySet()));
    }

    private final class MySnapshotEntrySet extends AbstractObjectSortedSet<Entry<V>> implements FastSortedEntrySet<V> {
        private final ObjectArrayList<Entry<V>> snapshot;

        MySnapshotEntrySet(FastSortedEntrySet<V> src) {
            this.snapshot = new ObjectArrayList(src.size());
            ObjectBidirectionalIterator var3 = src.iterator();

            while (var3.hasNext()) {
                Entry<V> e = (Entry<V>)var3.next();
                this.snapshot.add(new BasicEntry<V>(e.getIntKey(), e.getValue()) {
                    public V setValue(V v) {
                        return (V)this.getValue();
                    }
                });
            }
        }

        public int size() {
            return this.snapshot.size();
        }

        public ObjectBidirectionalIterator<Entry<V>> iterator() {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> iterator(Entry<V> fromElement) {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> fastIterator() {
            return this.snapshot.iterator();
        }

        public ObjectBidirectionalIterator<Entry<V>> fastIterator(Entry<V> from) {
            return this.snapshot.iterator();
        }

        public Entry<V> first() {
            return this.snapshot.isEmpty() ? null : (Entry)this.snapshot.get(0);
        }

        public Entry<V> last() {
            return this.snapshot.isEmpty() ? null : (Entry)this.snapshot.get(this.snapshot.size() - 1);
        }

        @Nullable
        public Comparator<? super Entry<V>> comparator() {
            return null;
        }

        public ObjectSortedSet<Entry<V>> subSet(Entry<V> fromElement, Entry<V> toElement) {
            return this;
        }

        public ObjectSortedSet<Entry<V>> headSet(Entry<V> toElement) {
            return this;
        }

        public ObjectSortedSet<Entry<V>> tailSet(Entry<V> fromElement) {
            return this;
        }
    }

    private final class MySnapshotKeySet extends AbstractIntSortedSet {
        private final IntArrayList snapshot;

        MySnapshotKeySet(IntSet src) {
            this.snapshot = new IntArrayList(src.size());
            IntIterator it = src.iterator();

            while (it.hasNext()) {
                this.snapshot.add(it.nextInt());
            }
        }

        public int size() {
            return this.snapshot.size();
        }

        public void clear() {
            this.snapshot.clear();
        }

        public boolean remove(int k) {
            return this.snapshot.rem(k);
        }

        public boolean contains(int k) {
            return this.snapshot.contains(k);
        }

        public IntBidirectionalIterator iterator() {
            return this.snapshot.iterator();
        }

        public IntBidirectionalIterator iterator(int fromElement) {
            return this.snapshot.iterator();
        }

        public IntSortedSet subSet(int fromElement, int toElement) {
            return this;
        }

        public IntSortedSet headSet(int toElement) {
            return this;
        }

        public IntSortedSet tailSet(int fromElement) {
            return this;
        }

        public IntComparator comparator() {
            return null;
        }

        public int firstInt() {
            return 0;
        }

        public int lastInt() {
            return 0;
        }
    }
}
