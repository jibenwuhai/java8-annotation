package java.util.copy;

import java.util.*;

public abstract class MyAbstractMap<K,V> implements MyMap<K,V> {

    protected MyAbstractMap() {
    }

    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return 0==size();
    }

    @Override
    public boolean containsKey(Object key) {
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                MyMap.Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
                MyMap.Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        if(null==value){
            while (i.hasNext()){
                MyMap.Entry<K,V> e= i.next();
                if(e.getValue()==null){
                    return true;
                }
            }
        }else {
            while (i.hasNext()){
                MyMap.Entry<K,V> e= i.next();
                if(e.getValue().equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                MyMap.Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                MyMap.Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {

        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        MyMap.Entry<K,V> correctEntry = null;
        if (key==null) {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    correctEntry = e;
            }
        } else {
            while (correctEntry==null && i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    correctEntry = e;
            }
        }
        V oldValue = null;
        if (correctEntry !=null) {
            oldValue = correctEntry.getValue();
            i.remove();
        }
        return oldValue;
    }

    @Override
    public void putAll(MyMap<? extends K, ? extends V> m) {
        for (MyMap.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        entrySet().clear();
    }

    transient Set<K>        keySet;
    transient Collection<V> values;

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if(ks==null) {
            ks = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<MyAbstractMap.Entry<K, V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return MyAbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return MyAbstractMap.this.isEmpty();
                }

                public void clear() {
                    MyAbstractMap.this.clear();
                }

                public boolean contains(Object k) {
                    return MyAbstractMap.this.containsKey(k);
                }

            };
        }
        return ks;
    }

    @Override
    public Collection<V> values() {
        Collection<V> vals = values;
        if (vals == null) {
            vals = new AbstractCollection<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        private Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V next() {
                            return i.next().getValue();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return MyAbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return MyAbstractMap.this.isEmpty();
                }

                public void clear() {
                    MyAbstractMap.this.clear();
                }

                public boolean contains(Object v) {
                    return MyAbstractMap.this.containsValue(v);
                }
            };
            values = vals;
        }
        return vals;
    }

    public abstract Set<MyMap.Entry<K,V>> entrySet();

    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof MyMap))
            return false;
        MyMap<?,?> m = (MyMap<?,?>) o;
        if (m.size() != size())
            return false;

        try {
            Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
            while (i.hasNext()) {
                MyMap.Entry<K,V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(m.get(key)==null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }

        return true;
    }


    public int hashCode() {
        int h = 0;
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }

    public String toString() {
        Iterator<MyMap.Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            MyMap.Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }


    protected Object clone() throws CloneNotSupportedException {
        MyAbstractMap<?,?> result = (MyAbstractMap<?,?>)super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }


}
