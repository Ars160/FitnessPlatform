package org.example.fitnessplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "image", columnDefinition = "BYTEA")
    private byte[] image;

    private String description;

    private String type;

    private int duration;

    @ManyToOne
    private User trainer;

    // Метод для преобразования изображения в Base64
    public String getBase64Image() {
        if (image != null) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }
}
