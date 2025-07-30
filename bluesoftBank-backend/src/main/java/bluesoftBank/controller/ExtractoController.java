package bluesoftBank.controller;

import bluesoftBank.dto.ExtractoRequestDTO;
import bluesoftBank.service.ExtractoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extracto")
@RequiredArgsConstructor
public class ExtractoController {

    private final ExtractoService extractoService;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generarPDF(@RequestBody ExtractoRequestDTO request) throws Exception {
        byte[] pdfBytes = extractoService.generarExtracto(request.getNumeroCuenta(), request.getMes(), request.getAnio());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=extracto_mensual.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
