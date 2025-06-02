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
    public void sendRegistrationEmail(String toEmail, String username, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "Verificació del compte";
            String verificationLink = "http://localhost:5173/auth/verify?token=" + token;

            String body = "<h1>Hola " + username + ",</h1>"
                    + "<p>Gràcies per registrar-te a la nostra plataforma. Si us plau, verifica el teu compte fent clic en el següent enllaç:</p>"
                    + "<a href=\"" + verificationLink + "\">Verificar compte</a>"
                    + "<p>Si no has sol·licitat aquest compte, pots ignorar aquest missatge.</p>";

            helper.setFrom("no-reply@shopapp.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // `true` indica que el contingut és HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error en enviar el correu de verificació del compte", e);
        }
    }

    @Async
    public void sendVerificationEmail(String toEmail, String username) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Crear el cos del missatge
            String body = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333;\">" +
                          "<h1 style=\"color: #4CAF50;\">Hola " + username + "!</h1>" +
                          "<p>El teu compte associat al correu <strong>" + toEmail + "</strong> ha estat verificat correctament.</p>" +
                          "<p>Gràcies per registrar-te a la nostra plataforma. Ara pots gaudir de tots els nostres serveis.</p>" +
                          "<hr style=\"border: none; border-top: 1px solid #ddd;\">" +
                          "<p style=\"font-size: 0.9em; color: #555;\">Si tens alguna pregunta, no dubtis a contactar-nos.</p>" +
                          "<p style=\"font-size: 0.9em; color: #555;\">Gràcies per confiar en nosaltres!</p>" +
                          "</div>";

            // Configurar el correu
            helper.setFrom("no-reply@shopapp.com");
            helper.setTo(toEmail);
            helper.setSubject("Compte verificat correctament");
            helper.setText(body, true); // `true` indica que el contingut és HTML

            // Enviar el correu
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error en enviar el correu de verificació", e);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(String email, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "Confirmació de la comanda - " + order.getOrderId();
            StringBuilder body = new StringBuilder();
            body.append("<h1>Gràcies per la teva compra!</h1>");
            body.append("<p><strong>ID de la comanda:</strong> ").append(order.getOrderId()).append("</p>");
            body.append("<p><strong>Data de la comanda:</strong> ").append(order.getOrderDate()).append("</p>");
            body.append("<h2>Productes:</h2>");
            body.append("<ul>");

            for (OrderProduct product : order.getProducts()) {
                body.append("<li>")
                    .append(product.getProduct().getTranslations().get(0).getName())
                    .append(" (Categoria: ").append(product.getCategory())
                    .append(", Quantitat: ").append(product.getQuantity())
                    .append(")</li>");
            }

            body.append("</ul>");
            body.append("<h2>Adreça d'enviament:</h2>");
            body.append("<p>").append(order.getStreet()).append(", ").append(order.getCity()).append("</p>");
            body.append("<p>").append(order.getState()).append(", ").append(order.getPostalCode()).append("</p>");
            body.append("<p>").append(order.getCountry()).append("</p>");

            helper.setFrom("no-reply@shopapp.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body.toString(), true); // `true` indica que el contingut és HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error en enviar el correu de confirmació de la comanda", e);
        }
    }
}