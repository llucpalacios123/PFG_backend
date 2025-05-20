package com.lluc.backend.shopapp.shopapp.services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.entities.Order;
import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendRegistrationEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("no-reply@shopapp.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // `true` indica que el contenido es HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending registration email", e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(String email, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "Order Confirmation - " + order.getOrderId();
            StringBuilder body = new StringBuilder();
            body.append("<h1>Thank you for your purchase!</h1>");
            body.append("<p><strong>Order ID:</strong> ").append(order.getOrderId()).append("</p>");
            body.append("<p><strong>Order Date:</strong> ").append(order.getOrderDate()).append("</p>");
            body.append("<h2>Products:</h2>");
            body.append("<ul>");

            for (OrderProduct product : order.getProducts()) {
                body.append("<li>")
                    .append(product.getProduct().getTranslations().get(0).getName())
                    .append(" (Category: ").append(product.getCategory())
                    .append(", Quantity: ").append(product.getQuantity())
                    .append(")</li>");
            }

            body.append("</ul>");
            body.append("<h2>Shipping Address:</h2>");
            body.append("<p>").append(order.getStreet()).append(", ").append(order.getCity()).append("</p>");
            body.append("<p>").append(order.getState()).append(", ").append(order.getPostalCode()).append("</p>");
            body.append("<p>").append(order.getCountry()).append("</p>");

            helper.setFrom("no-reply@shopapp.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body.toString(), true); // `true` indica que el contenido es HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending order confirmation email", e);
        }
    }
}