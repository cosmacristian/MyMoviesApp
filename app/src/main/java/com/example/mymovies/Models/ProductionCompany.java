package com.example.mymovies.Models;

public class ProductionCompany {
    public int id;
    public String logo_path;
    public String name;
    public String origin_country;

    public ProductionCompany(int id, String logo_path, String name, String origin_country) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
        this.origin_country = origin_country;
    }
}
