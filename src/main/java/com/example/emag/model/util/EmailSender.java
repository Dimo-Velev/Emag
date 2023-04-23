package com.example.emag.model.util;

import com.example.emag.model.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(String receiver, Product product) {
        new Thread(() -> {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(receiver);
            email.setSubject("Product in discount!");
            email.setText("Your favourite product " + product.getName() + " is now discounted with " +
                    product.getDiscount().getDiscountPercent() + " percent. Limited offer until "
                    + product.getDiscount().getExpireDate());
            javaMailSender.send(email);
        }).start();
    }
}
