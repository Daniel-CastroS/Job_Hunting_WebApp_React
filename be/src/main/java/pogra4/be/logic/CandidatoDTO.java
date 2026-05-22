package pogra4.be.logic;

import lombok.Getter;

@Getter
public class CandidatoDTO {
    private Oferente oferente;
    private int cumplidos;
    private int total;
    private double porcentaje;

    public CandidatoDTO(Oferente oferente, int cumplidos, int total, double porcentaje) {
        this.oferente = oferente;
        this.cumplidos = cumplidos;
        this.total = total;
        this.porcentaje = porcentaje;
    }
}