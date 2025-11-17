package com.flightapp.request;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Embeddable
public class PassengerInfo implements Serializable {
    private static final long serialVersionUID = 1L;
	@NotBlank
    private String name;
    @NotBlank
    private String gender;
    @Min(0)
    private int age;
    @NotBlank
    private String mealPreference; 

    public PassengerInfo() {}

    public PassengerInfo(String name, String gender, int age, String mealPreference) {
        this.name = name; this.gender = gender; this.age = age; this.mealPreference = mealPreference;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getMealPreference() { return mealPreference; }
    public void setMealPreference(String mealPreference) { this.mealPreference = mealPreference; }
}
