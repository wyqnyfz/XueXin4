package com.coolweather.xuexin3.socket;

public class FriendData {

    /**
     * id : 1
     * nickName : 学莘官方
     * signature : null
     * gender : null
     * phone : 12345678900
     * password : 123456
     * school : null
     * major : null
     * photo : http://121.36.5.207/000001.png
     */

    private int id;
    private String nickName;
    private String signature;
    private String gender;
    private String phone;
    private String password;
    private String school;
    private String major;
    private String photo;

    public FriendData(int id, String nickName, String signature, String gender, String phone, String password, String school, String major, String photo) {
        this.id = id;
        this.nickName = nickName;
        this.signature = signature;
        this.gender = gender;
        this.phone = phone;
        this.password = password;
        this.school = school;
        this.major = major;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object obj) {
        FriendData f = (FriendData) obj;
        return id==f.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Object getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
