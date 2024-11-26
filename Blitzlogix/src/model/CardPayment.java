package model;

import databaseOP.PaymentOP;

public class CardPayment implements PaymentStrategy{
	public void recordPayment(Payments p) {
		//just call the separate database functions from here
		   //update the status in payments table and enter an entry in the specific table too
		PaymentOP.insertCardPayment(p.getParcelID(), p.getBank(), p.getCardNumber());
	}
}
              //////will make these 

          // use card number, bank name here