package com.kevin.datastructures.list;

public interface List<T> {
    static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 元素的数量
     */
    int size();

    /**
     * 是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含某个元素
     * @param element
     */
    boolean contains(T element);

    /**
     * 添加元素到尾部
     * @param element 元素
     */
    void add(T element);

    /**
     * 获取index位置的元素
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 设置index位置的元素
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    T set(int index, T element);

    /**
     * 在index位置插入一个元素
     * @param index
     * @param element
     */
    void add(int index, T element);

    /**
     * 删除index位置的元素
     * @param index
     * @return
     */
    T remove(int index);

    /**
     * 查看元素的索引
     * @param element
     * @return
     */
    int indexOf(T element);
}
