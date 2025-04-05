package com.example.urbify.models;


import  jakarta.persistence.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "vehicle", uniqueConstraints = {
        @UniqueConstraint(columnNames = "plate"),
        @UniqueConstraint(columnNames = "identification")
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "identification", unique = true, nullable = false, length = 20)
    private String identification;

    @Column(name = "apartment", nullable = false, length = 50)
    private String apartment;

    @Column(name = "plate",unique = true, nullable = false, length = 120)
    private String plate;

    @Column(name = "vehicle_type", nullable = false, length = 120)
    private String vehicletype;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "departure_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    // Constructor vacío requerido por JPA
    public Vehicle() {
    }

    //Relacion con el vigilant
    @ManyToOne
    @JoinColumn(name = "vigilant_id", nullable= false) // Ajusta el nombre de la columna
    private Vigilant vigilant;

    // Getter y Setter
    public Vigilant getVigilant() {
        return vigilant;
    }

    public void setVigilant(Vigilant vigilant) {
        this.vigilant = vigilant;
    }

    // Método para registrar la salida del vehículo
    public void registerDeparture() {
        this.departureTime = new Date();
        this.updatedAt = new Date();
        this.active = false;
    }

    // Método para calcular el tiempo
    public String calculateStayDuration() {
        if (this.createdAt == null) {
            return "No se ha registrado la entrada del vehículo.";
        }

        Date endTime;
        if (this.departureTime != null) {
            endTime = this.departureTime;
        } else {
            endTime = new Date();
        }

        long durationInMillis = endTime.getTime() - this.createdAt.getTime();

        long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis) -
                TimeUnit.HOURS.toMinutes(hours);

        return String.format("%d horas, %d minutos", hours, minutes);
    }


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", identification='" + identification + '\'' +
                ", plate='" + plate + '\'' +
                ", apartment='" + apartment + '\'' +
                ", vehicletype='" + vehicletype + '\'' +
                ", active=" + active +
                ", stayDuration=" + (active ? "En estacionamiento (" + calculateStayDuration() + ")" : calculateStayDuration()) +
                '}';
    }
}