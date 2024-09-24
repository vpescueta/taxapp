
package com.example.mytaxapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String username;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "role")
    public String role;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "street")
    public String street;

    @ColumnInfo(name = "suite")
    public String suite;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "zipcode")
    public String zipcode;

    @ColumnInfo(name = "geo_lat")
    public Double geoLat;

    @ColumnInfo(name = "geo_lng")
    public Double geoLng;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "website")
    public String website;

    @ColumnInfo(name = "company_name")
    public String companyName;

    @ColumnInfo(name = "company_catch_phrase")
    public String companyCatchPhrase;

    @ColumnInfo(name = "company_bs")
    public String companyBS;

    @ColumnInfo(name = "process_status")
    public String processStatus;

    @ColumnInfo(name = "profile_pic")
    public String profilePic;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int uid, String username, String firstName, String lastName, String password,
                String role, String email, String street, String suite, String city, String zipcode,
                Double geoLat, Double geoLng, String phone, String website, String companyName,
                String companyCatchPhrase, String companyBS, String processStatus) {
        this.uid = uid;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geoLat = geoLat;
        this.geoLng = geoLng;
        this.phone = phone;
        this.website = website;
        this.companyName = companyName;
        this.companyCatchPhrase = companyCatchPhrase;
        this.companyBS = companyBS;
        this.processStatus = processStatus;
    }

    public User(int uid, String username, String firstName, String lastName, String password,
                String role, String email, String street, String suite, String city, String zipcode,
                Double geoLat, Double geoLng, String phone, String website, String companyName,
                String companyCatchPhrase, String companyBS, String processStatus, String profilePic) {
        this.uid = uid;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geoLat = geoLat;
        this.geoLng = geoLng;
        this.phone = phone;
        this.website = website;
        this.companyName = companyName;
        this.companyCatchPhrase = companyCatchPhrase;
        this.companyBS = companyBS;
        this.processStatus = processStatus;
        this.profilePic = profilePic;
    }

    public User(String username, String firstName, String lastName, String password, String role, String email, String street, String suite, String city, String zipcode, Double geoLat, Double geoLng, String phone, String website, String companyName, String companyCatchPhrase, String companyBS, String processStatus) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.email = email;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geoLat = geoLat;
        this.geoLng = geoLng;
        this.phone = phone;
        this.website = website;
        this.companyName = companyName;
        this.companyCatchPhrase = companyCatchPhrase;
        this.companyBS = companyBS;
        this.processStatus = processStatus;
    }

    protected User(Parcel in) {
        uid = in.readInt();
        username = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        role = in.readString();
        email = in.readString();
        street = in.readString();
        suite = in.readString();
        city = in.readString();
        zipcode = in.readString();
        if (in.readByte() == 0) {
            geoLat = null;
        } else {
            geoLat = in.readDouble();
        }
        if (in.readByte() == 0) {
            geoLng = null;
        } else {
            geoLng = in.readDouble();
        }
        phone = in.readString();
        website = in.readString();
        companyName = in.readString();
        companyCatchPhrase = in.readString();
        companyBS = in.readString();
        processStatus = in.readString();
        profilePic = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(username);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
        dest.writeString(role);
        dest.writeString(email);
        dest.writeString(street);
        dest.writeString(suite);
        dest.writeString(city);
        dest.writeString(zipcode);
        if (geoLat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(geoLat);
        }
        if (geoLng == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(geoLng);
        }
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeString(companyName);
        dest.writeString(companyCatchPhrase);
        dest.writeString(companyBS);
        dest.writeString(processStatus);
        dest.writeString(profilePic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(Double geoLat) {
        this.geoLat = geoLat;
    }

    public Double getGeoLng() {
        return geoLng;
    }

    public void setGeoLng(Double geoLng) {
        this.geoLng = geoLng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCatchPhrase() {
        return companyCatchPhrase;
    }

    public void setCompanyCatchPhrase(String companyCatchPhrase) {
        this.companyCatchPhrase = companyCatchPhrase;
    }

    public String getCompanyBS() {
        return companyBS;
    }

    public void setCompanyBS(String companyBS) {
        this.companyBS = companyBS;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
