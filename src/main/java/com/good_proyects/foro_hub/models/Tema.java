package com.good_proyects.foro_hub.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.good_proyects.foro_hub.models.dtos.Genero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 150 )
    private String titulo;

    @Column(unique = true, length = 1500)
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
   // @JsonIgnoreProperties("respuestas") // Ignora la lista de respuestas del usuario para evitar la recursión
    //@JsonManagedReference
    private Usuario usuarioId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Boolean activo;

    @OneToMany(mappedBy = "temaId", fetch = FetchType.LAZY)
   //@JsonManagedReference
   // @JsonIgnoreProperties("temaId") // Ignora el campo temaId de cada respuesta para evitar la recursión
    private List<Respuesta> respuestas;

}
