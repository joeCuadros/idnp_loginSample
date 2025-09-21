package com.idnp2025b.loginsample;

import org.json.JSONObject;

public class AccountEntity {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String username;
    private String password;

    public AccountEntity(String firstname, String lastname, String email, String phone, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public static AccountEntity import_txt(String data) {
        String[] parts = data.split("\\|");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Formato inválido para AccountEntity: " + data);
        }
        return new AccountEntity(
                parts[2], // firstname
                parts[3], // lastname
                parts[4], // email
                parts[5], // phone
                parts[0], // username
                parts[1]  // password
        );
    }
    public String export_txt(){
        return this.username+"|"+this.password+"|"+this.firstname+"|"+this.lastname+"|"+this.email+"|"+this.phone;
    }
    public Boolean validate(String user, String pass){
        return this.username.equals(user) && this.password.equals(pass);
    }
    public String toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("firstname", firstname);
            json.put("lastname", lastname);
            json.put("email", email);
            json.put("phone", phone);
            json.put("username", username);
            json.put("password", password);
            return json.toString();
        } catch (Exception e) {
            return null;
        }
    }
    public static AccountEntity fromJson(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            return new AccountEntity(
                    json.getString("firstname"),
                    json.getString("lastname"),
                    json.getString("email"),
                    json.getString("phone"),
                    json.getString("username"),
                    json.getString("password")
            );
        } catch (Exception e) {
            return null;
        }
    }
    // Getters
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
