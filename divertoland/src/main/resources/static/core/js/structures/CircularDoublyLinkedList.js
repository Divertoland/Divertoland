class Node {
    constructor(value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

class CircularDoublyLinkedList {
    constructor() {
      this.current = null;
      this.size = 0;
    }

    empty(){
      return this.current == null;
    }
  
    insert(value) {
      const newNode = new Node(value);
      
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

      return this;
    }
  
    next() {
      if (this.current) this.current = this.current.next;
      return this.current;
    }
  
    prev() {
      if (this.current) this.current = this.current.prev;
      return this.current;
    }

    log(){
      let start = this.current;
      do{
          console.log(this.current.value);
          this.next();
      }
      while(this.current != start);
    }
  }