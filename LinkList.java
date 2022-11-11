package project3;

public class LinkList<T> {
    /**
     * Node that marks the top of the list.
     */
    private Node<T> top;
    /**
     * Node that marks the end of the list.
     */
    private Node<T> tail;

    /**
     * An object that represents a linked list
     */
    public LinkList(){
        top = null;
        tail = null;
    }

    /**
     * Gets the node at the top of the list.
     * @return The node at the top of the list
     */
    public Node<T> getTop(){
        return top;
    }

    /**
     * Sets what node represents the top of the list.
     * @param top The node that should represent the top of the list
     */
    public void setTop(Node<T> top){
        this.top = top;
    }

    /**
     * Gets the node that represents the end of the list.
     * @return The node at the end of the list
     */
    public Node<T> getTail(){
        return tail;
    }

    /**
     * Sets what node represents the end of the list.
     * @param tail The node that should represent the end of the list
     */
    public void setTail(Node<T> tail){
        this.tail = tail;
    }

    /**
     * A method that will add inputted String to the end of the linked list.
     * @param data The piece of data to add to the node
     */
    public void addEnd(T data){
        if (isEmpty()){
            Node<T> newNode = new Node<>(data, null, null);
            setTop(newNode);
            setTail(newNode);
        }
        else {
            Node<T> current = top;
            while (current.getNext() != null){
                current = current.getNext();
            }
            Node<T> newNode = new Node<>(data, null, current);
            setTail(newNode);
            current.setNext(newNode);
        }
    }

	public void insertAfter (T data, int index) {
		//getNodeAt throws exception if index is invalid
		Node<T> indexNode = getNodeAt(index);

		if (top == null) {
			top = new Node<T>(data, null, null); 
			tail = top;
		}
		else {
			Node<T> afterNode = indexNode.getNext();
			Node<T> newNode = new Node<T>(data, afterNode, indexNode);

			indexNode.setNext(newNode);

			//if afterNode == null, newNode is last node in the list
			if (afterNode == null) {
				tail = newNode;
			}
			else {
				afterNode.setPrev(newNode);
			}
		}
	}
	
    /**
     * A method that will add inputted String to the beginning of the linked list.
     * @param data The piece of data to add to the node
     */
    public void addBeginning(T data){
        if (isEmpty()){
            Node<T> newNode = new Node<>(data, null, null);
            setTop(newNode);
            setTail(newNode);
        }
        else {
            Node<T> current = tail;
            while (current.getPrev() != null){
                current = current.getPrev();
            }
            Node<T> newNode = new Node<>(data, current, null);
            setTop(newNode);
            current.setPrev(newNode);
        }
    }

	public void insertBefore (T data, int index) {
		//getNodeAt throws exception if index is invalid
		Node<T> indexNode = getNodeAt(index);

		if (top == null) {
			top = new Node<T>(data, null, null);
			tail = top;
		}
		else if (index == 0) {
			Node<T> newNode = new Node<T>(data, indexNode, null);
			top = newNode;

			indexNode.setPrev(newNode);
		}
		else {
			Node<T> beforeIndexNode = indexNode.getPrev();
			Node<T> newNode = new Node<T>(data, indexNode, beforeIndexNode);

			beforeIndexNode.setNext(newNode);
			indexNode.setPrev(newNode);
		}
	}

	public T removeTop () {
		if (top == null) {
			throw new IllegalArgumentException();
		}
		else {
			T data = top.getData();
			top = top.getNext();

			if (top == null) {
				tail = top;
			}
			else {
				top.setPrev(null);
			}

			return data;
		}
	}
    
	public T delAt(int index) {
		if (index < 0 || index >= getLen()) {
			throw new IllegalArgumentException();
		}

		Node<T> indexNode = getNodeAt(index);

		if (index == 0) {
			return removeTop();
		}
		else {
			Node<T> beforeIndexNode = indexNode.getPrev();
			Node<T> afterIndexNode = indexNode.getNext();

			T indexNodeData = indexNode.getData();
			beforeIndexNode.setNext(afterIndexNode);

			/*if afterIndexNode == null, beforeIndexNode is now the 
	        	last node in the list*/
			if (afterIndexNode == null) {
				tail = beforeIndexNode;
			}
			else {
				afterIndexNode.setPrev(beforeIndexNode);
			}

			return indexNodeData;
		}
	}

	public int getLen() {
		int count = 0;
		Node<T> current = top;
		while (current != null) {
			count++;
			current = current.getNext();
		}

		int count2 = 0;
		current = tail;
		while (current != null) {
			count2++;
			current = current.getPrev();
		}

		if (count == count2) {
			return count;
		}
		else {
			throw new IllegalArgumentException(); 
		}

	}
    /**
     * Returns the node at the specified index.
     * @param index The index that you want to find the node at
     * @return The node at the specified index
     */
	public Node<T> getNodeAt(int index) {
		int length = getLen();
		if (index < 0 || index > length || (index == length && index != 0))
			throw new IndexOutOfBoundsException();
		else {
			Node<T> current = top;
			int count = 0;
			while(count < index) {
				count++;
				current = current.getNext();
			}

			return current;
		}
	}

    /**
     * A method that will return if the list is empty or not.
     * @return if the list is empty
     */
    public boolean isEmpty(){
        return getTop() == null;
    }
    
    /**
     * Walks forward and backward through list, adding data to the T
     * @return a T of the data in the list separated by commas
     */
    public String toString() {
    	Node<T> current = top;
    	String listContents = "";
    	
    	//Add first piece of data
    	if (current != null) {
    		listContents = (String) current.getData();
    		current = current.getNext();
    	}
    	
    	//Add remaining data
    	while (current != null) {
    		listContents +=  "," + current.getData();
    		current = current.getNext();
    	}
    	
    	//Add same data starting from the tail
    	current = tail;
    	while (current != null) {
    		listContents += "," + current.getData();
    		current = current.getPrev();
    	}
    	
    	return listContents;
    }
    
	public T getDataAt(int index) {
		int length = getLen();
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException();
		else
			return getNodeAt(index).getData();
	}
	
	public void clear() {
		top = null;
		tail = null;
	}
}
