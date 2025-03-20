package example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column (name = "last_name", nullable = false)
    private String lastName;

    @Column (name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    private Integer age;

    public Employee(String firstName, String lastName, String middleName, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.age = Period.between(birthDate, LocalDate.now()).getYears();
    }
    public Employee() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName + " " + birthDate + " " + gender.toString() + " " + age;
    }

    @Override
    public boolean equals(Object o) {
        Employee employee = (Employee) o;
        return this.firstName.equals(employee.firstName) && this.lastName.equals(employee.lastName) && this.middleName.equals(employee.middleName) && this.birthDate.equals(employee.birthDate);
    }
}
