package model;
import databaseOP.PaymentOP;

public class CashPayment implements PaymentStrategy{
	public void recordPayment(Payments p) {
		//just call the separate database functions from here
		   //update the status in payments table and enter an entry in the specific table too
		PaymentOP.insertCashPayment(p.getParcelID());
		                    //gets the name itself
	}
}


//use payername only