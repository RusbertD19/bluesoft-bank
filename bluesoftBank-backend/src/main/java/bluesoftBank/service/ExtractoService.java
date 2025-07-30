package bluesoftBank.service;

import bluesoftBank.entity.Cuenta;
import bluesoftBank.entity.Transaccion;
import bluesoftBank.repository.CuentaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExtractoService {

    private final CuentaRepository cuentaRepository;

    public byte[] generarExtracto(String numeroCuenta, int mes, int anio) throws Exception {
        Cuenta cuenta = cuentaRepository.findByNumero(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        LocalDateTime inicio = LocalDateTime.of(anio, mes, 1, 0, 0);
        LocalDateTime fin = inicio.plusMonths(1);

        List<Transaccion> transaccionesDelMes = cuenta.getTransacciones().stream()
                .filter(t -> t.getFecha().isAfter(inicio) && t.getFecha().isBefore(fin))
                .toList();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 70, 50);
        PdfWriter.getInstance(document, baos);
        document.open();

        // --- Encabezado con logo y título en una línea ---
        PdfPTable header = new PdfPTable(2);
        header.setWidths(new int[]{1, 4});
        header.setWidthPercentage(100);

        try (InputStream logoStream = new ClassPathResource("img/logo.png").getInputStream()) {
            Image logo = Image.getInstance(logoStream.readAllBytes());
            logo.scaleAbsolute(90f, 50f);
            PdfPCell logoCell = new PdfPCell(logo, false);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            header.addCell(logoCell);
        }

        Font bankTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        PdfPCell titleCell = new PdfPCell(new Phrase("Bluesoft Bank - Extracto Mensual", bankTitleFont));
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setPaddingLeft(10);
        header.addCell(titleCell);

        document.add(header);
        document.add(Chunk.NEWLINE);

        // --- Información de la cuenta ---
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        document.add(new Paragraph("Número de Cuenta: " + cuenta.getNumero(), infoFont));
        document.add(new Paragraph("Mes: " + mes + " / Año: " + anio, infoFont));
        document.add(new Paragraph("Saldo Actual: " + currencyFormat.format(cuenta.getSaldo()), infoFont));
        document.add(Chunk.NEWLINE);

        // --- Tabla de transacciones ---
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2, 3});

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        BaseColor headerColor = new BaseColor(0, 102, 204); // Azul elegante

        Stream.of("Fecha", "Tipo", "Valor", "Ciudad").forEach(title -> {
            PdfPCell headerCell = new PdfPCell(new Phrase(title, headerFont));
            headerCell.setBackgroundColor(headerColor);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setPadding(8);
            table.addCell(headerCell);
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Font rowFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        for (Transaccion t : transaccionesDelMes) {
            table.addCell(new PdfPCell(new Phrase(t.getFecha().format(formatter), rowFont)));
            table.addCell(new PdfPCell(new Phrase(t.getTipo(), rowFont)));
            table.addCell(new PdfPCell(new Phrase(currencyFormat.format(t.getValor()), rowFont)));
            table.addCell(new PdfPCell(new Phrase(t.getCiudadTransaccion(), rowFont)));
        }

        document.add(table);
        document.close();
        return baos.toByteArray();
    }
}
