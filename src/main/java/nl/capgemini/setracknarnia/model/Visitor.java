package nl.capgemini.setracknarnia.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Visitor implements Serializable {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	private String lastName;
	private int age;
	private String city;
	private boolean inNarnia;

	public Visitor() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isInNarnia() {
		return inNarnia;
	}

	public void setInNarnia(boolean inCloset) {
		this.inNarnia = inCloset;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Visitor visitor = (Visitor) obj;
		return id == visitor.id;
	}
}
