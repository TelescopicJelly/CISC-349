package edu.harrisburgu.cisc349.simplelistpictures;

public class User {

    private String name;
    private String phone;
    private String description;
    private String imageURL;


    public User(String name, String phone, String imageURL, String description) {
        this.name = name;
        this.phone = phone;
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getName() {return name;}

    public String getPhone() {return phone;}

    public String getImageUR() {return imageURL;}

    public String getDescription() {return description;}
}
