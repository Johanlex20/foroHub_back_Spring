package com.good_proyects.foro_hub.models;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuesta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "mensaje_respuesta", length = 500)
    private String mensajeRespuesta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tema_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("temaId")
    //@JsonBackReference
    //@JsonIgnoreProperties("respuestas") // Ignora la lista de respuestas del tema para evitar la recursión
    private Tema temaId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("usuarioId")
    //@JsonBackReference
    //@JsonIgnoreProperties("respuestas") // Ignora la lista de respuestas del tema para evitar la recursión
    private Usuario usuarioId;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Boolean activo;

}
