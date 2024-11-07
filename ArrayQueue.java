import java.sql.Array;

public class ArrayQueue{
    Element array[];
    int front; //in half point at first
    int rear; //in last point
    int N; //size
    int size;
    /*
    Insertion: O(1)
    Deletion: O(1)
    sağdan ekleyip soldan çıkarıyor
     */
    public ArrayQueue(int N){
        array = new Element[N];
        this.N = N;
        front = 0;
        rear = 0;
        size=0;
    }
    boolean isFull(){
        if (front == (rear + 1) % N)
            return true;
        else
            return false;
    }
    boolean isEmpty(){
        if (front == rear)
            return true;
        else
            return false;
    }
    void enqueue(Element element){
        if (!isFull ()){
            array[ rear ] = element;
            rear = (rear + 1) % N;
        }
        size++;
    }
    Element dequeue(){
        Element result;
        if (!isEmpty()){
            result = array[front];
            front = (front + 1) % N;
            size--;
            return result;
        }
        return null;

    }
    public void moveToRear() {
        Element frontElement = dequeue();
        enqueue(frontElement);
    }
    public void enlarge(){
        int newCapacity=N*2;
        Element newArray[]= new Element[newCapacity];
        int index=0;
        for(index=front;index != rear;index=(index + 1)% N){
            newArray[index++]=array[index];
        }
        N=newCapacity;
        array=newArray;
        front=0;
        rear=index;
    }
    void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }

        System.out.print("Queue: ");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % N;
            if(this.array[index] != null){
                System.out.print(array[index].data + " ");
            }
        }
        System.out.println();
    }
    void insertSecond(Element newElement) {
        if (size < 1) {
            throw new IllegalStateException("Queue must have at least one element to insert after.");
        }
        if (isFull()) {
            enlarge();
        }
        int secondPosition = (front + 1) % N;
        for (int i = size; i > 1; i--) {
            int currentIndex = (front + i) % N;
            int prevIndex = (front + i - 1) % N;
            array[currentIndex] = array[prevIndex];
        }
        array[secondPosition] = newElement;
        size++;
        rear = (rear + 1) % N;
    }
    void shrink(int M) {
        if (size >= M) {
            throw new IllegalArgumentException("Cannot shrink: queue contains more than M - 1 elements.");
        }
        Element[] newArray = new Element[M];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[(front + i) % N];
        }
        array = newArray;
        N = M;
        front = 0;
        rear = size;
    }
    void insertAfterKth(Element newElement, int K) {
        if (K < 0 || K >= size) {
            throw new IllegalArgumentException("K is out of bounds");
        }
        if (isFull()) {
            enlarge();
        }
        int insertIndex = (front + K + 1) % N;
        for (int i = size; i > K + 1; i--) {
            int currentIndex = (front + i) % N;
            int prevIndex = (front + i - 1) % N;
            array[currentIndex] = array[prevIndex];
        }
        array[insertIndex] = newElement;
        size++;
        rear = (rear + 1) % N; // Adjust rear pointer for the new size
    }
    Element dequeue2nd() {
        if (size < 2) {
            throw new IllegalStateException("Queue must have at least two elements.");
        }
        int secondIndex = (front + 1) % N;
        Element secondElement = array[secondIndex];
        for (int i = secondIndex; i != rear; i = (i + 1) % N) {
            int nextIndex = (i + 1) % N;
            array[i] = array[nextIndex];
        }
        rear = (rear - 1 + N) % N;
        size--;
        return secondElement;
    }
    void deleteKth(int K) {
        int deleteIndex = (front + K - 1) % N;
        for (int i = deleteIndex; i != rear; i = (i + 1) % N) {
            int nextIndex = (i + 1) % N;
            array[i] = array[nextIndex];
        }
        rear = (rear - 1) % N;
        size--;
    }
    ArrayQueue divideQueue() {
        ArrayQueue newQueue = new ArrayQueue(N);
        int newRear = 0;
        for (int i = 0; i < size; i++) {
            int currentIndex = (front + i) % N;
            if (i % 2 == 0) {
                newQueue.array[newQueue.rear] = array[currentIndex];
                newQueue.rear = (newQueue.rear + 1) % newQueue.N;
                newQueue.size++;
            } else {
                array[newRear] = array[currentIndex];
                newRear = (newRear + 1) % N;
            }
        }
        front = 0;
        rear = newRear;
        size = newRear;
        return newQueue;
    }
    public void removeOddIndexed(ArrayQueue queue) {
        ArrayQueue externalQueue = new ArrayQueue(N);
        int index = N;
        while (!queue.isEmpty()) {
            Element element= queue.dequeue();
            if (index % 2 == 0) {
                externalQueue.enqueue(element);
            }
            index--;
        }
        while (!externalQueue.isEmpty()) {
            queue.enqueue(externalQueue.dequeue());
        }
    }
    Element dequeue(int k) {
        if (k < 1 || k > size) {
            throw new IllegalArgumentException("k is out of bounds");
        }
        int targetIndex = (front + k - 1) % N;
        Element removedElement = array[targetIndex];
        for (int i = targetIndex; i != rear; i = (i + 1) % N) {
            int nextIndex = (i + 1) % N;
            array[i] = array[nextIndex];
        }
        rear = (rear - 1 + N) % N;
        size--;
        return removedElement;
    }
    void copyPaste(ArrayQueue src, int index) {
        int srcSize = src.size;
        int insertIndex = (front + index) % N;
        for (int i = size - 1; i >= index; i--) {
            int destIndex = (front + i + srcSize) % N;
            int currentIndex = (front + i) % N;
            array[destIndex] = array[currentIndex];
        }
        for (int i = 0; i < srcSize; i++) {
            int srcIndex = (src.front + i) % src.N;
            array[insertIndex] = src.array[srcIndex];
            insertIndex = (insertIndex + 1) % N;
        }
        rear = (rear + srcSize) % N;
        size += srcSize;
    }
    void cutPaste(ArrayQueue dest, int p, int q) {
        if (p < 0 || q < p || q >= size) {
            throw new IllegalArgumentException("Invalid indices for cutting");
        }
        int cutSize = q - p + 1;
        int startCutIndex = (front + p) % N;
        int endCutIndex = (front + q) % N;
        int destInsertIndex = (dest.rear + 1) % dest.N;
        for (int i = 0; i < cutSize; i++) {
            int currentIndex = (startCutIndex + i) % N;
            dest.array[destInsertIndex] = array[currentIndex];
            destInsertIndex = (destInsertIndex + 1) % dest.N;
        }
        dest.rear = (dest.rear + cutSize) % dest.N;
        dest.size += cutSize;
        int shiftStart = startCutIndex;
        for (int i = endCutIndex + 1; i != (rear + 1) % N; i = (i + 1) % N) {
            array[shiftStart] = array[i];
            shiftStart = (shiftStart + 1) % N;
        }
        rear = (shiftStart - 1 + N) % N;
        size -= cutSize;
    }


    public static void main(String[] args) {
        ArrayQueue test=new ArrayQueue(12);
        test.enqueue(new Element(8));
        test.enqueue(new Element(21));
        test.enqueue(new Element(4));
        test.enqueue(new Element(87));
        test.enqueue(new Element(9));
        test.enqueue(new Element(998));
        test.enqueue(new Element(82));
        test.enqueue(new Element(81));
        test.printQueue();
        test.insertSecond(new Element(12));
        test.printQueue();
        test.deleteKth(4);
        test.printQueue();
        test.dequeue(4);
        test.printQueue();
        ArrayQueue test2=new ArrayQueue(12);
        test2.enqueue(new Element(54));
        test2.enqueue(new Element(46));
        test.copyPaste(test2,2);
        test.printQueue();
        test.cutPaste(test2,5,7);
        test.printQueue();
        test2.printQueue();
    }
}