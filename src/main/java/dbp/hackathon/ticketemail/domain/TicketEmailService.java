package dbp.hackathon.ticketemail.domain;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class TicketEmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoConQr(String destinatario, String asunto, String contenidoHtml, String dataQR) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        String qrUrl = generarUrlQR(dataQR);

        String contenidoFinal = contenidoHtml + "<br><img src='" + qrUrl + "' alt='QR Code'/>";


        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenidoFinal, true);

        mailSender.send(mensaje);
    }


    private String generarUrlQR(String data) throws UnsupportedEncodingException {
        String baseUrl = "https://api.qrserver.com/v1/create-qr-code/?data=";
        String tamaño = "&size=200x200";
        return baseUrl + URLEncoder.encode(data, "UTF-8") + tamaño;
    }
}
