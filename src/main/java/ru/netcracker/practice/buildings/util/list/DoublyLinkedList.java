package ru.netcracker.practice.buildings.util.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

public class DoublyLinkedList<E> implements MyList<E>, Serializable, Iterable<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> implements Serializable{
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public E getItem() {
            return item;
        }

        public void setItem(E item) {
            this.item = item;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    public DoublyLinkedList() {
    }

    private void linkBefore(E e, Node<E> cur) {
        final Node<E> pred = cur.prev;
        final Node<E> newNode = new Node<>(pred, e, cur);
        cur.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(last, e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            f.prev = newNode;
            last.next = newNode;
        }
        size++;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, first);
        last = newNode;
        if (l == null) {
            first = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            l.next = newNode;
            first.prev = newNode;
        }
        size++;
    }

    private E unlink(int index) {
        checkElementIndex(index);
        final Node<E> delNode = node(index);
        return removeNode(delNode);
    }

    private E unlink(E e) {
        final Node<E> delNode = node(e);
        if (delNode == null) {
            throw new NullPointerException();
        }
        return removeNode(delNode);
    }

    private E removeNode(Node<E> delNode) {
        final E element = delNode.item;
        delNode.prev.next = delNode.next;
        delNode.next.prev = delNode.prev;
        delNode.item = null;
        delNode.next = null;
        delNode.prev = null;
        size--;
        return element;
    }

    private E unlinkFirst(Node<E> f) {
        final E element = f.item;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null;
        f.prev = null;
        first = next;
        if (next == null) {
            last = null;
        } else {
            next.prev = last;
            last.next = next;
        }
        size--;
        return element;
    }

    private E unlinkLast(Node<E> l) {
        final E element = l.item;
        final Node<E> prev = l.prev;
        l.item = null;
        l.prev = null;
        l.next = null;
        last = prev;
        if (prev == null) {
            first = null;
        } else {
            prev.next = first;
            first.prev = prev;
        }
        size--;
        return element;
    }

    private Node<E> node(int index) {
        checkElementIndex(index);
        Node<E> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    private Node<E> node(E e) {
        Node<E> x = first;
        while(x != null) {
            if (x.item.equals(e)) {
                return x;
            }
            x = x.next;
        }
        return null;
    }

    private boolean changeNode(int index, E e) {
        checkElementIndex(index);
        node(index).item = e;
        return true;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean add(int index, E e) {
        if (index == 0) {
            linkFirst(e);
        } else if (index == size) {
            linkLast(e);
        } else {
            linkBefore(e, node(index));
        }
        return true;
    }

    @Override
    public E remove(E e) {
        if (first != null && first.item.equals(e))
            return unlinkFirst(first);
        if (last != null && last.item.equals(e))
            return unlinkLast(last);
        return unlink(e);
    }

    @Override
    public E remove(int index) {
        if (index == 0)
            return unlinkFirst(first);
        if (index == size - 1)
            return unlinkLast(last);
        return unlink(index);
    }

    @Override
    public boolean replace(int index, E e) {
        return changeNode(index, e);
    }

    @Override
    public boolean replace(E oldElem, E newElem) {
        return false;
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    public Node<E> getFirst() {
        return first;
    }

    public Node<E> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("Items: ");
        Node<E> x = first;
        while (x != last) {
            resultString.append(x.item).append(", ");
            x = x.next;
        }
        resultString.append(last.item);
        return resultString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoublyLinkedList)) return false;
        DoublyLinkedList<?> that = (DoublyLinkedList<?>) o;
        if (size != that.size) return false;
        if (!first.item.equals(that.first.item) || !last.item.equals(that.last.item)) return false;
        Node<?> el = first;
        Node<?> thatEl = that.first;
        for (int i = 0; i < size; i++) {
            if (!el.item.equals(thatEl.item)) return false;
            el = el.next;
            thatEl = thatEl.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, first, last);
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedIterator<>(this);
    }

    private static class DoublyLinkedIterator<E> implements Iterator<E> {
        Node<E> current;
        int iterations;
        int listSize;

        public DoublyLinkedIterator(DoublyLinkedList<E> list) {
            this.current = list.getFirst();
            this.iterations = 0;
            this.listSize = list.getSize();
        }

        @Override
        public boolean hasNext() {
            return iterations < listSize;
        }

        @Override
        public E next() {
            E item = current.getItem();
            current = current.getNext();
            iterations++;
            return item;
        }
    }
}
