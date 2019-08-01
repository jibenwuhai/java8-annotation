package java.util.copy;


import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.hash;

public class MyHashMap<K,V> extends MyAbstractMap<K,V>
        implements MyMap<K,V>, Serializable,Cloneable {

    private static final long serialVersionUID = -125322301819821673L;


    static class Node<K,V> implements MyMap.Entry<K,V>{

        final int hash;
        final K key;
        V value;
        MyHashMap.Node<K,V> next;
        Node(int hash,K key,V value,Node<K,V> next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }


        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        public final String toString() { return key + "=" + value; }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if(o==this){
                return true;
            }
            if(o instanceof MyMap.Entry){
                MyMap.Entry<?,?> e = (MyMap.Entry)o;
                return Objects.equals(e.getKey(),key)&&Objects.equals(e.getValue(),value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key)^Objects.hashCode(value);
        }
    }

    static final class TreeNode<K,V>  {
        //TreeNode(int hash, K key, V value, MyHashMap.Node<K, V> next) {
            //super(hash, key, value, next);
        //}
    }

    int threshold;

    transient int size;

    transient Node<K,V>[] table;
    /**
     * 默认初始容量
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    /**
     * 最大生产力
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 负载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 哈希表的加载因子。
     */
    final float loadFactor;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    /**
     *
     * @param hash
     * @param key
     * @param value
     * @param onlyIfAbsent  只有在缺少的
     * @param evict 驱逐
     * @return
     */
    private V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab;
        Node<K,V> p;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0) {  //table为空 初始化table
            n = (tab = resize()).length;
        }
        return null;
    }

    /**
     * 调整大小
     * @return
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;  //入口；门槛；开始；极限；临界值
        int newCap,  //容量
            newThr = 0;  //新的边界值

        if (oldCap > 0) { //旧容量》0
            if (oldCap >= MAXIMUM_CAPACITY) {
                //最大不再扩容
                threshold = Integer.MAX_VALUE;
                return oldTab;
                //新容量=旧容量扩大一倍 还小于最大容量 且旧容量大于初始化容量
            }else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
                newThr = oldThr << 1; // 扩容一倍
            }
        }else if (oldThr > 0) {// 新的容量 = 阈值
            newCap = oldThr;
        }else {               // 使用默认值
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);  //边界值为0.75*初始化容量
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?(int)ft : Integer.MAX_VALUE);
        }

        threshold = newThr;
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) { //扩容后，把旧的数据放入新的表中
            for (int j = 0; j < oldCap; ++j) {  //循环所有的节点
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;  //取出后致空，方便JVM回收。
                    if (e.next == null) {//该节点下只有一个数据
                        newTab[e.hash & (newCap - 1)] = e;
                   // }else if (e instanceof TreeNode){
                        //树形
                        //((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    } else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;

    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(MyMap<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }


    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
}
