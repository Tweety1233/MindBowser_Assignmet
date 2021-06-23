package com.mindbowser.assignmet.repository;

public interface IDataSource {
    interface SharedData {
         void setUpdate(Boolean username);

         Boolean getUpdate();


    }
}
