package com.example.gestione_ordini.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdineRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "Nome Cliente", example = "Stefano")
    private String nomeCliente;
    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "Cognome Cliente", example = "Bianchi")
    private String cognomeCliente;
    @NotBlank
    @Email
    @Size(max = 100)
    @Schema(description = "Email di contatto", example = "stefano.bianchi@abc.com")
    private String emailCliente;
    @Size(max = 20)
    @Schema(description = "Telefono", example = "02 80274568")
    private String telefonoCliente;
    @NotBlank
    @Size(min = 5, max = 120)
    @Schema(description = "Indirizzo", example = "Via Dei Tigli, 25")
    private String indirizzoSpedizione;
    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(description = "Città", example = "Milano")
    private String cittaSpedizione;
    @NotBlank
    @Schema(description = "CAP", example = "20100")
    @Pattern(regexp = "([1-9]{5})")
    private String cap;
    @NotBlank
    @Size(min = 2, max = 80)
    @Schema(description = "Nome/descrizione prodotto", example = "Iphone 16")
    private String prodotto;
    @NotNull
    @Min(1)
    @Max(999)
    @Schema(description = "Quantità ordinata", example = "2")
    private int quantita;
    @NotNull
    @Positive
    @Schema(description = "Prezzo unitario", example = "558.00")
    private double prezzoUnitario;
}
