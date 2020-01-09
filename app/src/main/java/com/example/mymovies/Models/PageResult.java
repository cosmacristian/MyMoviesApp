package com.example.mymovies.Models;

import java.util.ArrayList;
import java.util.List;

public class PageResult {
     public int page;
     public int total_results;
     public int total_pages;
     public List<MovieResults> results;

     public PageResult(int page,int total_results,int total_pages,List<MovieResults> results){
         this.page = page;
         this.total_results = total_results;
         this.total_pages = total_pages;
         this.results = results;
     }
}
