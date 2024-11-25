package model;

import java.time.LocalDateTime;

public interface PaymentStrategy {
     void recordPayment(Boolean done,LocalDateTime time);
}
