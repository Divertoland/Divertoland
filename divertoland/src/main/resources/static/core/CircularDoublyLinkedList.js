class Node {
    constructor(id) {
        this.value = id;
        this.next = null;
        this.prev = null;
    }
}

class CircularDoublyLinkedList {
    constructor() {
      this.current = null;
      this.size = 0;
    }
  
    insert(id, title, imageUrl, description) {
      const newNode = new Node(id, title, imageUrl, description);
      
      if (this.size === 0) {
        this.current = newNode;
        newNode.next = newNode;
        newNode.prev = newNode;
      } else {
        const tail = this.current.prev;
        tail.next = newNode;
        newNode.prev = tail;
        newNode.next = this.current;
        this.current.prev = newNode;
      }
  
      this.size++;
    }
  
    next() {
      if (this.current) {
        this.current = this.current.next;
      }
    }
  
    prev() {
      if (this.current) {
        this.current = this.current.prev;
      }
    }
  }