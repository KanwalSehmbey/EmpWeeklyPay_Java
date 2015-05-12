import java.util.*;

public class EmpWeeklyPay
{
	public static void main(String[] args)
	{
		EmployeeData emp = new EmployeeData();

		emp.getInput();
		emp.sortData();
		emp.print();
	}
}