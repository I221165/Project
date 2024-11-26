package model;

public class PaymentFactory {
        PaymentStrategy wayOfPayment;
        
        public PaymentStrategy getWay(String nameOfMethod) {
        	if(nameOfMethod.equalsIgnoreCase(CardPayment.class.getSimpleName())) {
        		wayOfPayment = new CardPayment();
        	} else if(nameOfMethod.equalsIgnoreCase(CashPayment.class.getSimpleName())) {
        		wayOfPayment = new CashPayment();
        	}else {
        		wayOfPayment = new OnlineTransfer();
        	}
            return wayOfPayment;	
        }
}
