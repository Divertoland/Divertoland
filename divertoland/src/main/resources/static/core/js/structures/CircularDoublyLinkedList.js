class Node {
    constructor(value) {
        this.value = value;
        this.nextNode = null;
        this.prevNode = null;
    }
}

class CircularDoublyLinkedList {
    constructor(data) {
      this.size = 0;
      if(data === undefined){
        this.currentNode = null;
      }
      else{
        for(let i = 0; i < data.length; i++){
          this.insert(data[i]);
          this.size++;
        };
      }
    }

    empty(){
      return this.currentNode == null;
    }
  
    insert(value) {
      const newNode = new Node(value);
      
      if (this.size === 0) {
        this.currentNode = newNode;
        newNode.nextNode = newNode;
        newNode.prevNode = newNode;
      } else {
        const tail = this.currentNode.prevNode;
        tail.nextNode = newNode;
        newNode.prevNode = tail;
        newNode.nextNode = this.currentNode;
        this.currentNode.prevNode = newNode;
      }
  
      this.size++;

      return this;
    }
  
    next() {
      if (this.currentNode) this.currentNode = this.currentNode.nextNode;
      return this.currentNode;
    }
  
    prev() {
      if (this.currentNode) this.currentNode = this.currentNode.prevNode;
      return this.currentNode;
    }

    log(){
      const start = this.currentNode;
      do{
          console.log(this.currentNode.value);
          this.next();
      }
      while(this.currentNode != start);
    }
  }