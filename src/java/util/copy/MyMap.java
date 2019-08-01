package java.util.copy;

import java.io.Serializable;
import java.util.*;

public interface MyMap<K,V> {

    /**
     * 返回这个Map存多少键值，如果超过Integer.MAX_VALUE 返回 Integer.MAX_VALUE
     * @return
     */
    int size();

    /**
     * 无监制对的时候返回true
     * @return
     */
    boolean isEmpty();

    /**
     * 是否包含某个Key
     * @param key
     * @return
     */
    boolean containsKey(Object key);

    /**
     * 包含某个值
     * @param value
     * @return
     */
    boolean containsValue(Object value);

    /**
     * 根据KEY获取他的值
     * @param key
     * @return
     */
    V get(Object key);

    /**
     * 新增键值对
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * 移除某个（key）的键值对
     * @param key
     * @return
     */
    V remove(Object key);

    /**
     * 批量放入键值对
     * @param m
     */
    void putAll(MyMap<? extends K, ? extends V> m);

    /**
     * 清空所以的键值对
     */
    void clear();

    /**
     * 获取值的Set集合
     * @return
     */
    Set<K> keySet();

    /**
     * 获取值得集合
     * @return
     */
    Collection<V> values();

    /**
     * 获取Map的集合
     * @return
     */
    Set<MyMap.Entry<K, V>> entrySet();

    interface Entry<K,V> {
        /**
         * 获取Key
         * @return
         */
        K getKey();

        /**
         * 获取value
         */
        V getValue();

        /**
         *  设置值
         */
        V setValue(V value);

        /**
         */
        boolean equals(Object o);

        /**
         */
        int hashCode();


        public static <K extends Comparable<? super K>, V> Comparator<MyMap.Entry<K,V>> comparingByKey() {
            return (Comparator<MyMap.Entry<K, V>> & Serializable)(c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        public static <K, V extends Comparable<? super V>> Comparator<MyMap.Entry<K,V>> comparingByValue() {
            return (Comparator<MyMap.Entry<K, V>> & Serializable)(c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        public static <K, V> Comparator<MyMap.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<MyMap.Entry<K, V>> & Serializable)(c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        public static <K, V> Comparator<MyMap.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<MyMap.Entry<K, V>> & Serializable)(c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }


    boolean equals(Object o);

    int hashCode();


}
