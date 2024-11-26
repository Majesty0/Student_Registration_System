package com.mycompany.studentregistrationsystem;

public class StudentData {
  private String title;
  private String lastName;
  private String firstName;
  private String middleName;
  private String streetAddress;
  private String city;
  private String postalCode;
  private String cellPhone;
  private String poBox;
  private String county;
  private String country;
  private String email;
  private String dob;
  private String gender;
  private String placeOfBirth;
  private String citizenship;
  private String passportNumber;
  private String maritalStatus;
  private String kinName;
  private String kinAddress;
  private String kinTown;
  private String kinPhone;
  private String kinEmail;
  private String semester;
  private int enrollmentYear;
  private String degree;
  private String sponsorName;
  private String sponsorAddress;
  private String sponsorPhone;
  private String sponsorEmail;
  private String disability;
  private String referralSource;
  private String signature;
  private String date;

  // Full constructor
  public StudentData(String title, String lastName, String firstName, String middleName, String streetAddress, 
                     String city, String postalCode, String cellPhone, String poBox, String county, 
                     String country, String email, String dob, String gender, String placeOfBirth, 
                     String citizenship, String passportNumber, String maritalStatus, String kinName, 
                     String kinAddress, String kinTown, String kinPhone, String kinEmail, String semester, 
                     int enrollmentYear, String degree, String sponsorName, String sponsorAddress, 
                     String sponsorPhone, String sponsorEmail, String disability, String referralSource, 
                     String signature, String date) {
      this.title = title;
      this.lastName = lastName;
      this.firstName = firstName;
      this.middleName = middleName;
      this.streetAddress = streetAddress;
      this.city = city;
      this.postalCode = postalCode;
      this.cellPhone = cellPhone;
      this.poBox = poBox;
      this.county = county;
      this.country = country;
      this.email = email;
      this.dob = dob;
      this.gender = gender;
      this.placeOfBirth = placeOfBirth;
      this.citizenship = citizenship;
      this.passportNumber = passportNumber;
      this.maritalStatus = maritalStatus;
      this.kinName = kinName;
      this.kinAddress = kinAddress;
      this.kinTown = kinTown;
      this.kinPhone = kinPhone;
      this.kinEmail = kinEmail;
      this.semester = semester;
      this.enrollmentYear = enrollmentYear;
      this.degree = degree;
      this.sponsorName = sponsorName;
      this.sponsorAddress = sponsorAddress;
      this.sponsorPhone = sponsorPhone;
      this.sponsorEmail = sponsorEmail;
      this.disability = disability;
      this.referralSource = referralSource;
      this.signature = signature;
      this.date = date;
  }

  // Getters (add setters if needed)
  public String getTitle() { return title; }
  public String getLastName() { return lastName; }
  public String getFirstName() { return firstName; }
  public String getMiddleName() { return middleName; }
  public String getStreetAddress() { return streetAddress; }
  public String getCity() { return city; }
  public String getPostalCode() { return postalCode; }
  public String getCellPhone() { return cellPhone; }
  public String getPoBox() { return poBox; }
  public String getCounty() { return county; }
  public String getCountry() { return country; }
  public String getEmail() { return email; }
  public String getDob() { return dob; }
  public String getGender() { return gender; }
  public String getPlaceOfBirth() { return placeOfBirth; }
  public String getCitizenship() { return citizenship; }
  public String getPassportNumber() { return passportNumber; }
  public String getMaritalStatus() { return maritalStatus; }
  public String getKinName() { return kinName; }
  public String getKinAddress() { return kinAddress; }
  public String getKinTown() { return kinTown; }
  public String getKinPhone() { return kinPhone; }
  public String getKinEmail() { return kinEmail; }
  public String getSemester() { return semester; }
  public int getEnrollmentYear() { return enrollmentYear; }
  public String getDegree() { return degree; }
  public String getSponsorName() { return sponsorName; }
  public String getSponsorAddress() { return sponsorAddress; }
  public String getSponsorPhone() { return sponsorPhone; }
  public String getSponsorEmail() { return sponsorEmail; }
  public String getDisability() { return disability; }
  public String getReferralSource() { return referralSource; }
  public String getSignature() { return signature; }
  public String getDate() { return date; }
}
