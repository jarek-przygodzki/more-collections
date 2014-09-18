more-collections
================
Project aims to improve sorting in Java 8 with _Lambda Expressions_


Sorting custom type collections
---------------------------------

```
class Person {
	
	public Integer   id;
	public String    firstName;
	public String    lastName;
	public LocalDate birthDate;

	public Person(Integer id, String firstName, String lastName, LocalDate birthDate) {
		this.id        = id;
		this.firstName = firstName;
		this.lastName  = lastName;
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return String.format(
				"Person {id=%d, firstName=%s, lastName=%s, birthDate=%s}",
				id, firstName, lastName, birthDate);
	}
}


List<Person> people = (...)
// Sort by first name
MoreCollections.sort(people, p -> p.firstName);
// Sort by birth date
MoreCollections.sort(people, p -> p.birthDate);
// Sort by composite key [lastName, firstName]
MoreCollections.sort(people,
    p -> p.lastName, 
    p -> p.firstName);
	

```