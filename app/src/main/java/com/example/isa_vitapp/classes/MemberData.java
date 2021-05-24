package com.example.isa_vitapp.classes;

public class MemberData {

    public static String member_name;
    public static String member_domain1;
    public static String member_domain2;
    public static String member_reg;

    String contact_number;
    String dob;
    String domain1;
    String domain2;
    String github_link;
    String instagram_link;
    String linkedin_link;
    String name;
    String personal_email;
    String photo_link;
    String position;
    String reg_number;
    String room_number;
    String timestamp;
    String vit_email;

    public MemberData() {

        this.contact_number = "contact";
        this.dob = "dob";
        this.domain1 = "domain1";
        this.domain2 = "domain2";
        this.github_link = "github_link";
        this.instagram_link = "instagram_link";
        this.linkedin_link = "linkedin_link";
        this.name = "name";
        this.personal_email = "personal_email";
        this.photo_link = "photo_link";
        this.position = "position";
        this.reg_number = "reg_number";
        this.room_number = "room_number";
        this.timestamp = "timestamp";
        this.vit_email = "vit_email";

        member_domain1 = "domain1";
        member_domain2 = "domain2";
        member_name = "name";
        member_reg = "reg_number";
    }

    public MemberData(String contact, String dob, String domain1, String domain2, String github_link, String instagram_link, String linkedin_link, String name, String personal_email, String photo_link, String position, String reg_number, String room_number, String timestamp, String vit_email) {

        this.contact_number = contact;
        this.domain1 = domain1;
        this.domain2 = domain2;
        this.github_link = github_link;
        this.instagram_link = instagram_link;
        this.linkedin_link = linkedin_link;
        this.name = name;
        this.personal_email = personal_email;
        this.photo_link = photo_link;
        this.position = position;
        this.reg_number = reg_number;
        this.room_number = room_number;
        this.timestamp = timestamp;
        this.vit_email = vit_email;

        if(domain2.equals("Events and Finance")){
            this.domain2 = "E&F";
        }

        if(domain2.equals("Content and Outreach")){
            this.domain2 = "C&O";
        }

        member_domain1 = domain1;
        member_domain2 = domain2;
        member_name = name;
        member_reg = reg_number;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getDomain1() {
        return domain1;
    }

    public void setDomain1(String domain1) {
        this.domain1 = domain1;
    }

    public String getDomain2() {
        return domain2;
    }

    public void setDomain2(String domain2) {
        this.domain2 = domain2;
    }
    public String getGithub_link() {
        return github_link;
    }

    public void setGithub_link(String github_link) {
        this.github_link = github_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    public String getLinkedin_link() {
        return linkedin_link;
    }

    public void setLinkedin_link(String linkedin_link) {
        this.linkedin_link = linkedin_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonal_email() {
        return personal_email;
    }

    public void setPersonal_email(String personal_email) {
        this.personal_email = personal_email;
    }

    public String getPhoto_link() {
        return photo_link;
    }

    public void setPhoto_link(String photo_link) {
        this.photo_link = photo_link;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVit_email() {
        return vit_email;
    }

    public void setVit_email(String vit_email) {
        this.vit_email = vit_email;
    }
}
