package ru.netcracker.practice.buildings.util.list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;

public class SinglyLinkedList<E> implements MyList<E>, Serializable, Iterable<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> implements Serializable{
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }

        public E getItem() {
            return item;
        }

        public Node<E> getNext() {
            return next;
        }
    }

    public SinglyLinkedList() {
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(e, f);
        first = newNode;
        if (f == null) {
            last = newNode;
            newNode.next = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(e, first);
        if (l == null) {
            last = newNode;
            first = newNode;
            newNode.next = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private void linkAfter(E e, Node<E> cur) {
        cur.next = new Node<>(e, cur.next);
        size++;
    }

    private E unlink(int index) {
        checkElementIndex(index);
        final Node<E> prevNode = prevNode(index);
        return removeAfter(prevNode);
    }

    private E unlink(E e) {
        final Node<E> prevNode = prevNode(e);
        if (prevNode == null) {
            throw new NullPointerException();
        }
        return removeAfter(prevNode);
    }

    private E removeAfter(Node<E> prevNode) {
        final Node<E> nextNode = prevNode.next.next;
        final E element = prevNode.next.item;
        prevNode.next.item = null;
        prevNode.next.next = null;
        prevNode.next = nextNode;
        size--;
        return element;
    }

    private E unlinkFirst() {
        final Node<E> nextNode = first.next;
        final E element = first.item;
        first.item = null;
        first.next = null;
        last.next = nextNode;
        first = nextNode;
        size--;
        return element;
    }

    private E unlinkLast() {
        final Node<E> prevNode = prevNode(size - 1);
        final E element = last.item;
        last.item = null;
        last.next = null;
        prevNode.next = first;
        last = prevNode;
        size--;
        return element;
    }

    private boolean changeNode(int index, E e) {
        checkElementIndex(index);
        node(index).item = e;
        return true;
    }

    private Node<E> prevNode(int index) {
        checkElementIndex(index);
        Node<E> x = first;
        for (int i = 0; i < index - 1; i++) {
            x = x.next;
        }
        return x;
    }

    private Node<E> prevNode(E e) {
        Node<E> x = first;
        while (x != null) {
            if (x.next.item.equals(e)) {
                return x;
            }
            x = x.next;
        }
        return null;
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
        while (x != null) {
            if (x.item.equals(e)) {
                return x;
            }
            x = x.next;
        }
        return null;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
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
            linkAfter(e, prevNode(index));
        }
        return true;
    }

    @Override
    public E remove(E e) {
        if (first != null && first.item.equals(e))
            return unlinkFirst();
        if (last != null && last.item.equals(e))
            return unlinkLast();
        return unlink(e);
    }

    @Override
    public E remove(int index) {
        if (index == 0) {
            return unlinkFirst();
        } else if (index == size - 1) {
            return unlinkLast();
        }
        return unlink(index);
    }

    @Override
    public boolean replace(int index, E e) {
        return changeNode(index, e);
    }

    @Override
    public boolean replace(E oldElem, E newElem) {
        Node<E> curNode = node(oldElem);
        if (curNode != null) {
            curNode.item = newElem;
        } else {
            throw new NullPointerException();
        }
        return true;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
        for (int i = 0; i < size; i++) {
            resultString.append(x.item).append(", ");
            x = x.next;
        }
        return resultString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SinglyLinkedList)) return false;
        SinglyLinkedList<?> that = (SinglyLinkedList<?>) o;
        if (size != that.size) return false;
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
    public SinglyLinkedIterator<E> iterator() {
        return new SinglyLinkedIterator<>(this);
    }

    private static class SinglyLinkedIterator<E> implements Iterator<E> {
        Node<E> current;
        int iterations;
        int listSize;

        public SinglyLinkedIterator(SinglyLinkedList<E> list) {
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
