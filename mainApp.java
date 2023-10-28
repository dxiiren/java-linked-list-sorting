import java.util.Scanner;

/*
	Programmer        : Muhd Akmal Ahmad Suhaimi, Adam Taufiq Shahrul Affendi, Ayu Najwa Khairul Azwan
	Date              : 01/01/2022
	Description       : This program takes in how bla bla bla bla bla bla bla
*/

public class mainApp
{
    public static void main(String [] args) throws Exception
    {

		//Scanner
		Scanner sc = new Scanner(System.in);

		//INPUT FROM USER
		LinkedList input = new LinkedList();
 		char ch = 'A';
 		int arr,cpu;

		System.out.println("Enter O input to stop\n");
		do{
			char names = ch++;

			System.out.println( "Job "+ (names) + ") ");		//NORMAL ONE USE INPUT DATA GET FROM HERE ( FROM USER )
																//JUST REMOVE THE COMMENT --->   /*
			System.out.print("Enter arrival time = ");
			arr = Integer.parseInt(sc.nextLine());

			System.out.print("Enter cpu time = ");
			cpu = Integer.parseInt(sc.nextLine());

			if(arr>0 && cpu>0)
			{
				Job abc  = new Job(names,cpu,arr);
				input.insertAtBack(abc);
			}
			names++;
			System.out.println();

		} while(arr>0 || cpu>0);

		//To Count The Size of the Linked List
		int size = 0;
		Job A = (Job) input.getFirst();
		while( A != null )
		{
			size++;
			A = (Job)input.getNext();
		}
		//System.out.println("The size is : "+size);

		//Declaration
		LinkedList temp = new LinkedList();	//Use for temporary storage
		LinkedList sort = new LinkedList();	//Use for sorting process
		LinkedList queue = new LinkedList();//Store data that want to be process baed on their turn
		LinkedList done = new LinkedList();	//Store data that have been finised proces

		String name [] = new String[size];	//to store the job name has arrived and for notification purpose
		int clock    = 1;	 				//control clock time
		int register = 1;					//act as a trigger, so data in queue will be taken out to be processed once it finished its job (1-Take out the data, 0- Do nothing)
		int arrival  = 1;	                //to determine if "process taken out data from LinkedList Input" should be done or not ( 1-Yes , 0-No )
		int round    = 0;					//to count how many jobs have been finish
		int trigger	 = 1;					//trigger notification for display data has been arrived at bottom

		Job process = new Job() ;

		/*THE PROCESSING DATA PART BEGIN FROM HERE !*/
		while(round<size)
		{
			/*1-PROCESS TO TAKE OUT DATA FROM INPUT(linkedList) AND PUT IN INTO QUEUE(LinkedList) to process the data */

			Job B = (Job) input.getFirst();
			while(B!=null)
			{
				if(B.getArrivalTime() == clock)
					arrival=1;

				B = (Job)input.getNext();
			}

			if(arrival==1)
			{
				int set=0;							//Posibilities if more than one job arrive when clock ==1

				Job C = (Job)input.removeFromFront();
				while(C != null)
				{
					set++;

					if(C.getArrivalTime() == clock)
						sort.insertAtBack(C);
					else
						temp.insertAtBack(C);

					if(clock !=1 || (clock==1 && set>2) )
						trigger=1;					//trigger notication for arrived data in QUEUE at bottom

					C = (Job)input.removeFromFront();
				}

				//put back data in input(linkedList) from temporary storage
				Job change = (Job)temp.removeFromFront();
				while(change != null)
				{
					input.insertAtBack(change);
					change = (Job)temp.removeFromFront();
				}

				/*2-SORTING PROCESS*/
				int count=0;
				Job D = (Job) sort.removeFromFront();
				while(D!=null)
				{
					if(clock!=1)
						name[count] = String.valueOf(D.getName());	//store the name of Job that will be display as " Job 'X' has arrived"
					queue.insertAtBack(D);

					D = (Job)sort.removeFromFront();
					count++;
				}
				queue = queue.insertionSort(queue);

				//Notification purpose, just in case there are more than 1 data enter in queue at clock timer 1
					if(clock==1 && count>1)
					{
						count=0;
						Job X = (Job)queue.getFirst();
						while(X!=null)
						{
							name[count] = String.valueOf(X.getName());
							X=(Job)queue.getNext();
							count++;
						}
					}

				arrival=0;

			}//end of if arrival && FINISH SORTING

			//START THE JOB PROCESSING STARTING FROM HERE

			/*3- BRING OUT THE DATA THAT HAVE BEEN SORTED OUT BASED ON THEIR CPU TIME INTU QUEUE AND PROCESS IT OUT AFTER THEN*/
			if(register==1)
			{
				process = (Job)queue.removeFromFront();
				done.insertAtBack(process);
				register=0;
			}

			/*4- "SERVICING" THE CPU TIME*/
			System.out.println("\nTime: "+clock);
			System.out.println("Job " + process.getName() + " is executing ...");
			process.setCpuTime(process.getCpuTime()-1);


			/*5- DISPLAY THE DATA THAT HAVE BEEN WAIT IN QUEUE WITH THEIR WAIT TIME*/
			Job E = (Job) queue.getFirst();
			while ( E!=null)
			{
				E.setWaitingTime(E.getWaitingTime()+1);		//WaitingTime +1

				if(E.getWaitingTime()>1)
					System.out.println("Job " + E.getName() + " is in hold for " + E.getWaitingTime() + "ms");

				E = (Job)queue.getNext();
			}

			/*6-DISPLAY THE NOTIFICATION FOR NEW DATA TO BE PUT IN QUEUE*/
			if(trigger==1)
			{
				int x;

				if(clock==1)
					x=1;
				else
					x=0;

				while(x<size)
				{

					if(name[x]!=null)
						System.out.println("Job " + name[x]+ " has arrived... ");

					name[x]=null;
					x++;
				}
				trigger=0;
			}

			//Job will be categorized as complete once all its CpuTime has been "service" (Cpu Time =0)
			//Once CpuTime for the currennt job equall to 0 then, Trigger the register to 1 to activate process taken next data in queue into processs
			if(process.getCpuTime()==0 )
			{
				register=1;
				round++;
			}

			clock++;

		}//end of while

		/*7- A SIGN TO INFORM THAT ALL JOB HAVE BEEN FINISHED PROCESS*/
		System.out.println("\nTime: "+clock);
		System.out.println("End");
	    System.out.println();

		/*8- COUNT THE AVERAGE OF TURN AROUND TIME + DISPLAY*/			//TURN AROUND = cpu time + waiting
		double total = 0.0 , size2 =size;

		Job F = (Job)done.getFirst();
		while( F != null)
		{
			int turnAround = F.getWaitingTime() + F.getBurstTime();
			total += turnAround;
			F=(Job)done.getNext();
		}

		double avgTurnTime = total/size2;
		System.out.println("Average turn-around time : " + avgTurnTime +"ms");

		/*9-COUNT THE AVERAGE OF WAITING TIME AND DISPLAY*/
		total=0.0;

		Job G = (Job)done.getFirst();
		while( G != null)
		{
			total += G.getWaitingTime();
			G=(Job)done.getNext();
		}

		double avgWaitTime = total/size2;
		System.out.println("Average waiting time : " + avgWaitTime +"ms");

		System.out.println("\n\n");

		//SYSTEM END HERE
		//THANK YOU ;)
  	}//main

}//class