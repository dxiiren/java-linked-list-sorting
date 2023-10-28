public class LinkedList
{
	//attribute
	private Node firstNode;
	private Node lastNode;
	private Node currNode;

	//constructor
		//default constructor
		public LinkedList()
		{
			firstNode	= null;
			lastNode	= null;
			currNode	= null;
		}

		//normal constructor
		public LinkedList(String a)
		{
			firstNode	= null;
			lastNode	= null;
			currNode	= null;
		}

	//check either list empty or not
	public boolean isEmpty()
	{
		if(firstNode == null)
			return true;
		 else
			return false;
	}

	//Inserting data into list
	public void insertAtFront(Job obj)
	{
		if (isEmpty())			//firstNode == null
			firstNode = lastNode = new Node(obj);
		else
			firstNode = new Node(obj,firstNode);
	}

	public void insertAtBack(Job obj)
	{
		if (isEmpty())			//firstNode == null
			firstNode = lastNode = new Node(obj);
		else
		{ 	lastNode.next = new Node(obj);
			lastNode = lastNode.next;
		}
	}

	//Removing data from the list
	public Job removeFromFront() //throws EmptyListException
	{
		Job removeItem = null;

		if(isEmpty())			//firstNode == null
			 return null;//throw new EmptyListException();

		removeItem = firstNode.data;

		if(firstNode.equals(lastNode))
			firstNode = lastNode = null;
		else
			firstNode=firstNode.next;

		 return removeItem;
	}

	public Job removeFromBack() //throws EmptyListException
	{
		Job removeItem = null;

		if(isEmpty())			//firstNode == null
			 return null;//throw new EmptyListException();

		removeItem = lastNode.data;

		if(firstNode.equals(lastNode))
			firstNode = lastNode = null;
		else
		{
			Node current = firstNode; 		//traverse the list to find the second last data
			while(current.next != lastNode)
			{
				current = current.next;
			}

			lastNode     = current;			//then point the last node to the 2nd last data
			current.next = null;
		}
		 return removeItem;
	}

	//Accessor Method
	public Object getFirst()
	{
		if(isEmpty())			//firstNode == null
			return null;
		else
		{
			currNode = firstNode;
			return currNode.data;
		}
	}

	public Node getFirst2()		//return the firstNode only but not the Data of the first Node
	{
		if(isEmpty())
			return null;
		else
			return firstNode;	//return only firstNode
	}

	public Object getLast()
	{
		if(isEmpty())			//firstNode == null
			return null;
		else
		{
			currNode = lastNode;
			return currNode.data;
		}
	}

	public Object getNext()
	{
		if(currNode != lastNode)			//firstNode == null
		{
			currNode = currNode.next;
			return currNode.data;
		}
		else
			return null;
	}

//Insertion Sort Linked List Version
	public LinkedList insertionSort(LinkedList queue)
	{
		LinkedList sortList = new LinkedList();
		Node current = queue.getFirst2();
		Node sorted = null;							//Head node of the node that had been done sorted

		while(current !=null)
		{
			Node nextNode = current.next;			//Store the next node data
			Job X = current.data;
			Node temp = new Node(X);				//Create a pure new node without link to any node
			sorted = sortedInsert(sorted,temp);		//sortedInsert = method that been use to help sort all those node in ascending order
			current = nextNode;						//pass the next node data to variable current to validate the while statement
		}

		//After finish arrange all the data, the data will be in Linked List and will be pass to the caller
		while(sorted!=null)
		{
			sortList.insertAtBack(sorted.data);
			sorted=sorted.next;
		}

		return sortList;
	}

	//Method used to sort the node one by one accroding to its Cpu time (Which one is more lowest)
	private Node sortedInsert(Node sortedHead,Node baru)
	{
		Node current = sortedHead;

		if(current==null || current.data.getCpuTime() > baru.data.getCpuTime() )
		{
			baru.next = current;
			return baru;			// the value of 'baru' will be the head of sorted
		}

		else if(current.data.getCpuTime() == baru.data.getCpuTime())
		{
			baru.next     = current.next;
			current.next  = baru;
		}

		else
		{
			while(current.next != null && current.next.data.getCpuTime() <= baru.data.getCpuTime() )
				current = current.next;

			baru.next      = current.next;
			current.next   = baru;
		}

		return sortedHead;
	}//classInsertedNode

}//end of class