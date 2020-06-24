import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size = 0;

    private class CustomIterator implements Iterator<Item> {
        private int pos;
        public CustomIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public Item next() {
            if (pos >= size) {
                throw new NoSuchElementException("Iterator Overflow.");
            }
            return array[pos++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove an element of an array.");
        }
    }

    public RandomizedQueue() {
        array = (Item[]) new Object[10];
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Something went wrong - nothing to add");
        }

        if (size > 0 && size == array.length) {
            resize(array.length * 2);
        }
        array[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue underflow");
        }
        Random rand = new Random();
        int index = rand.nextInt(size);
        size--;
        Item tmp = array[index];

        array[index] = array[size];
        array[size] = null;

        if (size > 0 && size == array.length / 4) resize(array.length / 2);
        return tmp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        Random rand = new Random();
        int index = rand.nextInt(size);
        return array[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new CustomIterator();
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();
        test.enqueue(10);
        test.enqueue(2);
        test.enqueue(3);
        test.enqueue(7);
        test.enqueue(5);
        test.enqueue(6);
        System.out.println("Queue is empty? " + test.isEmpty());
        System.out.println("Sample is " + test.sample());
        System.out.println("In queue: ");
        for (Integer i: test) {
            System.out.print(i + " ");
        }

        System.out.println("\n");

        test.dequeue();

        System.out.println("After dequeue in queue: ");
        for (Integer i: test) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }
}