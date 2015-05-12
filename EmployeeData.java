import java.util.*;
import java.text.NumberFormat;

public class EmployeeData
{
	String[][] empData;
	int top;
	private static final int MAXCOL = 6;
	NumberFormat fv = NumberFormat.getCurrencyInstance();	
	
	public EmployeeData()
	{
		empData = new String[1][MAXCOL];
		top = -1;
	}
	public void getInput()
	{
		Scanner in = new Scanner(System.in);
		boolean flag = true;
		int empHours;
		float empWage, sales;
		String empName, empType;
		char rwrd;
		
		while(flag)
		{
			empHours = 0;
			empWage = 0;
			sales = 0;
			rwrd = ' ';
			
			System.out.println();
			System.out.print("Enter employee's name: ");
			empName = in.next();
			System.out.print("Enter employee type(Salaried, Hourly, Commissioned): ");
			empType = in.next();
			if(empType.equalsIgnoreCase("Salaried"))
			{
				System.out.print("Enter weekly salary: ");
				validateInput(in);
				empWage = in.nextFloat();
				System.out.print("Give Reward to this employee(y/n)? ");
				while(!in.hasNext("[ny]"))
				{
					System.out.print("Please enter (y/n): ");
					in.next();
				}
				rwrd = Character.toLowerCase(in.next().charAt(0));
			}
			else if(empType.equalsIgnoreCase("Hourly"))
			{
				empType = "Hourly	";
				System.out.print("Enter hourly rate: ");
				validateInput(in);
				empWage = in.nextFloat();
				System.out.print("Enter number of hours worked: ");
				while (!in.hasNextInt()) 
				{
					System.out.print("Its not valid input, Please enter a number: ");
					in.next();
				}	
				empHours = in.nextInt();
			}
			else if(empType.equalsIgnoreCase("Commissioned"))
			{
				System.out.print("Enter weekly sales: ");
				validateInput(in);
				sales = in.nextFloat();					
				empWage = 500;
			}
			else
			{
				System.out.println("Please enter correct type...");
				flag = false;
			}

			if(top < 0 && flag == true)																//add data into an array
			{
				addData(empName, empType, empHours, sales, empWage, rwrd);		
			}
			else{
				if(!duplicateRec(empName) && flag == true)
				{
					addData(empName, empType, empHours, sales, empWage, rwrd);
				}
				else if(flag)
				{
					System.out.println("Duplicate record!!!!");
				}
			}
			System.out.println();
			System.out.print("Do you want to enter another employee data(y/n): ");
			while(!in.hasNext("[ny]"))
			{
				System.out.print("Please enter (y/n): ");
				in.next();
			}
			String ans = in.next();
			if(ans.equalsIgnoreCase("y"))
			{ 
				flag = true;	
			}
			else
			{ 
				flag = false; 
			}
		}	
	}	
	
	public boolean duplicateRec(String valToComp)
	{
		for(int i = 0; i<=top; i++){
			String s = empData[i][0];
			if(s.equalsIgnoreCase(valToComp))
			{
				return true;
			}
		}
		return false;
	}
	
	void validateInput(Scanner sc)
	{
		while (!sc.hasNextFloat()) 
		{
			System.out.print("Its not valid input, Please enter a number: ");
			sc.next();
		}	
	}
	
	public void addData(String nm, String st, int hrs, float sale, float wage, char r)
	{
		double empPay = 0;
		++top;
		arrayResize(top+1,MAXCOL);
		
		empData[top][0] = toTitleCase(nm);
		empData[top][1] = toTitleCase(st);
		empData[top][2] = ifZero(hrs);
		empData[top][3] = ifZero(sale);
		empData[top][4] = fv.format(wage);
		
		if(st.equalsIgnoreCase("Salaried"))
		{
			empPay = wage * 40;
			if(r == 'y'){
				double bonus = empPay * 10/100;
				empPay = empPay + bonus;
			}
		}
		else if(st.equalsIgnoreCase("Hourly	"))
		{
			if(hrs > 40){
				float extraW = (hrs - 40) * (wage * 2);
				empPay = 40 * wage + extraW;
			}
			else{
				empPay = hrs * wage;
			}
		}
		else if(st.equalsIgnoreCase("Commissioned"))
		{
			if(sale!= 0)
			{
				empData[top][3] = fv.format(sale);
				sale = sale * 10/100;
				empPay = wage + sale;
			}
			else
			{
				empPay = wage;
			}
		}
		if(r == 'y')
		{
			empData[top][5] = fv.format(empPay) + "*";
		}
		else
		{
			empData[top][5] = fv.format(empPay);
		}		
	}
	
	String ifZero(float val){
		if(val == 0.0){ return "-	"; }	
		else{ return Double.toString(val);
		}
	}
	String ifZero(int val){
		if(val == 0){ return "-"; }
		else{ return Integer.toString(val);
		}
	}
	
	public void arrayResize(int rows , int columns)
	{
	    	String[][] holdArray = new String[empData.length][empData[0].length];//copy the current array into temporary array
	    	for (int r=0; r < holdArray.length; r++)
			{
	    		for (int c=0; c < holdArray[0].length; c++) 
					holdArray[r][c] = empData[r][c];
			} 	 
	    	
			empData= new String[rows][columns];									// re-instantiate the array to be the wished size
 
	        if(rows>holdArray.length||columns>holdArray[0].length)				//copy the temporary array into the new array
			{																	
		    	for (int j=0; j < holdArray.length; j++)
				{
		    		for (int k=0; k < holdArray[0].length; k++)
					{
		    			empData[j][k] = holdArray[j][k];
					}
				}
	    	}
			else
			{
	    		for (int j=0; j <rows; j++)
				{
		    		for (int k=0; k <columns; k++) 
					{
		    			empData[j][k] = holdArray[j][k];
					}
				}	
	    	}
    }
	
	public static String toTitleCase(String input)
	{
		StringBuilder titleCase = new StringBuilder();
		boolean nextTitleCase = true;

		for (char c : input.toCharArray()) 
		{
			if (Character.isSpaceChar(c)) 
			{
				nextTitleCase = true;
			} 
			else if (nextTitleCase) 
			{
				c = Character.toTitleCase(c);
				nextTitleCase = false;
			}
			titleCase.append(c);
		}
		return titleCase.toString();
	}

	public void sortData()
	{
		Arrays.sort(empData, new Comparator<String[]>() 
		{
			@Override
			public int compare(String[] e1, String[] e2) 
			{
				int result = e1[1].compareTo(e2[1]);
				if (result != 0) 
				{ 
					return result;
				}
				result = e1[5].compareTo(e2[5]);
				if (result != 0) 
				{ 
					return result;
				}
				return e1[0].compareTo(e2[0]);
			}
		});
	}
	
	public void print()
	{
		System.out.println();
		System.out.println("Name" + "\t" + "Status	" + "\t" + "Hours" + "\t" + "Sales" + "\t\t" + "Wages" + "\t" + "Pay");
		System.out.println("------\t-------------\t-----\t-----\t\t-----\t--------");
		for(int i=0; i<empData.length; i++)
		{
			String[] row = empData[i];
			for(int j=0; j<row.length; j++) 
			{
				System.out.print(row[j] + "\t");
			}
			System.out.print("\n");
		}
		System.out.println("------\t-------------\t-----\t-----\t\t-----\t--------");
		System.out.print("*");
		for(int i=0; i <empData.length; i++)
		{
			if(empData[i][5].contains("*"))
			{
				System.out.print(empData[i][0] + " ");
			}
		}
		System.out.println( " 10% bonus is awarded");
	}
}