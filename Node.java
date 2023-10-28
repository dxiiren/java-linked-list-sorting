public class Node
{
	//attribute
	Job data;			//Composite object
	Node next;

//constructor
	//Default Constructor
	public Node(){}

	//Normal Constructor
	public Node(Job item)						//Without pointer
	{
		data = item;
		next = null;
	}

	public Node(Job item, Node nextNode)		//With pointer
	{
		data = item;
		next = nextNode;
	}

	//accessor method
	public Job getData(){return data;}
	public Node getNext(){return next;}

}//end of class