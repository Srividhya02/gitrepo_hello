package com.springboot.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="user")
public class User
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotEmpty(message = "Id required")
    @Column(name="id")
	private String id;
	
	@NotEmpty(message = "firstName required")
	@Column(name="fName")
	private String fName;
	
	@NotEmpty(message = "LastName required")
	@Column(name="lName")
	private String lName;
	
	@NotEmpty(message = "Email required") 
	@Email
	@Column(name="email")
	private String email;
	
	@NotNull(message = "Pincode required")
	@Column(name="pinCode")
	private Number pinCode;
	
	@NotNull(message = "DOB required")
	@PastOrPresent(message="Must be a past or present date. Not future Date")
	@Column(name="birth_date")
	private Date birthDate;
	
	@Column(name="is_active")
	private boolean isActive;
	
	public User()
	{
	}
	
	public User(String id, String fName, String lName, String email, Number pinCode, Date birthDate)
	{
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.pinCode = pinCode;
		this.birthDate = birthDate;
	}
	public User(String id) 
	{
		this.id=id;
	}

	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Number getPinCode() {
		return pinCode;
	}
	public void setPinCode(Number pinCode) {
		this.pinCode = pinCode;
	} 
	
	@JsonSerialize(using=JSONSerialize.class) 
	public Date getBirthDate() 
	{
		return birthDate;
	}
	
	@JsonDeserialize(using=JsonDateDeSerializer.class,as=Date.class)
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
    public String toString ()
	{
        return "User{" +
                            "id='" + id + '\'' +
                            ", fname='" + fName + '\'' +
                            ", lname='" + lName + '\'' +
                            ", email='" + email + '\'' +
                            ", pincode='" + pinCode + '\'' +
                            ", date='" + birthDate + '\'' +
                            '}';
    }
}
