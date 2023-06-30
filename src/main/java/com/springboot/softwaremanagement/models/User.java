package com.springboot.softwaremanagement.models;

    public class User {
        private String username;
        private String password;
        // Other user properties and getters/setters

        // Constructor
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername(){
            return this.username;
        }

        public String getPassword(){
            return this.password;
        }
    }

