package com.coolweather.xuexin3.results;

public class ResLogin {

    /**
     * success : true
     * code : 100
     * message : 登录成功
     * data : {"user":{"id":12,"nickName":null,"signature":null,"gender":null,"phone":"18674877242","password":"18674877242","school":null,"major":null,"photo":null}}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : {"id":12,"nickName":null,"signature":null,"gender":null,"phone":"18674877242","password":"18674877242","school":null,"major":null,"photo":null}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 12
             * nickName : null
             * signature : null
             * gender : null
             * phone : 18674877242
             * password : 18674877242
             * school : null
             * major : null
             * photo : null
             */

            private int id;
            private Object nickName;
            private Object signature;
            private Object gender;
            private String phone;
            private String password;
            private Object school;
            private Object major;
            private Object photo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getNickName() {
                return nickName;
            }

            public void setNickName(Object nickName) {
                this.nickName = nickName;
            }

            public Object getSignature() {
                return signature;
            }

            public void setSignature(Object signature) {
                this.signature = signature;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
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

            public void setSchool(Object school) {
                this.school = school;
            }

            public Object getMajor() {
                return major;
            }

            public void setMajor(Object major) {
                this.major = major;
            }

            public Object getPhoto() {
                return photo;
            }

            public void setPhoto(Object photo) {
                this.photo = photo;
            }
        }
    }
}
