public class LinkedListQueue{
    LinkedList.Node front;
    LinkedList.Node rear;

    public LinkedListQueue(){
        front = null;
        rear = null;
    }
    public LinkedListQueue(LinkedListQueue[] list) {
        front = null;
        rear = null;
        for (LinkedListQueue queue : list) {
            LinkedList.Node current = queue.front;
            while (current != null) {
                LinkedList.Node newNode = new LinkedList.Node(current.value);
                if (rear != null) {
                    rear.next = newNode;
                } else {
                    front = newNode;
                }
                rear = newNode;
                current = current.next;
            }
        }
    }

    boolean isEmpty(){
        if (front == null)
            return true;
        else
            return false;
    }
    void enqueue(LinkedList.Node node){
        if (!isEmpty())
            rear.next = node;
        else
            front = node;
        rear = node;
    }
    LinkedList.Node dequeue(){
        LinkedList.Node result;
        result = front ;
        if (!isEmpty()){
            front = front.next;
            if (front == null)
                rear = null;
        }
        return result;
    }
    void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        System.out.print("Queue: ");
        LinkedList.Node current = front;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }
    public void moveToRear() {
        LinkedList.Node frontNode = front;
        front = front.next;
        frontNode.next = null;
        rear.next = frontNode;
        rear = frontNode;
    }
    void insertSecond(Element newElement) {
        if (isEmpty()) {
            throw new IllegalStateException("Queue must have at least one element to insert after.");
        }
        LinkedList.Node newNode=new LinkedList.Node(newElement.data);
        newNode.next = front.next;
        front.next = newNode;
        if (rear == front) {
            rear = newNode;
        }
    }
    LinkedList.Node dequeue2nd() {
        if (isEmpty() || front.next == null) {
            throw new IllegalStateException("Queue must have at least two elements.");
        }
        LinkedList.Node secondNode = front.next;
        front.next = secondNode.next;
        secondNode.next = null;
        return secondNode;
    }
    public static void main(String[] args) {
        LinkedListQueue test=new LinkedListQueue();
        test.enqueue(new LinkedList.Node(12));
        test.enqueue(new LinkedList.Node(10));
        test.enqueue(new LinkedList.Node(4));
        test.enqueue(new LinkedList.Node(7));
        test.enqueue(new LinkedList.Node(998));
        test.enqueue(new LinkedList.Node(90));
        test.enqueue(new LinkedList.Node(87));
        test.printQueue();
        test.insertSecond(new Element(12));
        test.printQueue();


    }
}