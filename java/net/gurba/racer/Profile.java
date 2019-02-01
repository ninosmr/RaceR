package net.gurba.racer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    Profile(){}

    Profile(String name, String password, String carModel, String power, String location, String imageUrl){
        this.name = name;
        this.password = password;
        this.carModel = carModel;
        this.power = power;
        this.imageUrl = imageUrl;
        this.location = location;
    }
    static Profile personalProfile;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("car")
    @Expose
    private String carModel;

    @SerializedName("image")
    @Expose
    private String imageUrl;

    @SerializedName("power")
    @Expose
    private String power;

    @SerializedName("location")
    @Expose
    private String location;

    public void setPersonalProfile(Profile profile){
        personalProfile = profile;
        System.out.println("works lole");
    }

    public Profile getPersonalProfile(){return personalProfile;}

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarModel() { return carModel; }

    public void setCarModel(String carModel) { this.carModel = carModel; }

    public String getPower() { return power; }

    public void setPower(String power) { this.power = power; }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() { return password; }
}
