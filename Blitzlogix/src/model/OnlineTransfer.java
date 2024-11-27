package model;

import databaseOP.PaymentOP;

public class OnlineTransfer implements PaymentStrategy{
	public void recordPayment(Payments p) {
		   //just call the separate database functions from here
		   //update the status in payments table and enter an entry in the specific table too
		PaymentOP.insertOnlineTransfer(p.getParcelID(), p.getBank(), p.getTransactionID());
	}
}


//use transaction id, bank name here