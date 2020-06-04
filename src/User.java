import java.time.LocalDate;

public class User {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private String group;
    private String gender;
    private LocalDate birthDay;

    public User(String fullName, String phoneNumber, String address, String email, String group, String gender, LocalDate birthDay) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.group = group;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String[] getArray(){
        String[] arr={getPhoneNumber(),getGroup(),getFullName(),getGender(),getAddress(), String.valueOf(getBirthDay()),getEmail()};
        return arr;
    }

    @Override
    public String toString(){
        String returnString= getPhoneNumber() +" | "+ getGroup() +" | "+getFullName() +" | " + getGender() +" | "+ getAddress() +" | "+getBirthDay()+" | "+getEmail();
        return returnString;
    }

}
