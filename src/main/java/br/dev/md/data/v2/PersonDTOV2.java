package br.dev.md.data.v2;

import br.dev.md.model.Person;
import br.dev.md.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;
//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender" })
@JsonFilter("PersonFilter")
public class PersonDTOV2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    //@JsonProperty("first_name")
    private String firstName;
    //@JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;
    private String address;
    //@JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    public PersonDTOV2() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstNane) {
        this.firstName = firstNane;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() { return birthDate; }

    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getPhoneNumber() { return phoneNumber; }
    
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTOV2 person)) return false;
        return Objects.equals(
            getId(),
            person.getId()) &&
            Objects.equals(getFirstName(), person.getFirstName()) &&
            Objects.equals(getLastName(), person.getLastName()) &&
            Objects.equals(getBirthDate(), person.getBirthDate()) &&
            Objects.equals(getAddress(), person.getAddress()) &&
            Objects.equals(getGender(), person.getGender()) &&
            Objects.equals(getPhoneNumber(), person.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getId(),
            getFirstName(),
            getLastName(),
            getBirthDate(),
            getAddress(),
            getGender(),
            getPhoneNumber()                
        );
    }
}
