package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "blog_category")
public class BlogCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogCategoryId;

    private String blogCategoryName;
    private String blogCategoryDescription;

    @OneToMany(mappedBy = "blogCategory", cascade = CascadeType.ALL)
    private List<Blog> blogs;

    public BlogCategory() {}

    public BlogCategory(String blogCategoryName, String blogCategoryDescription) {
        this.blogCategoryName = blogCategoryName;
        this.blogCategoryDescription = blogCategoryDescription;
    }

    public int getBlogCategoryId() {
        return blogCategoryId;
    }

    public void setBlogCategoryId(int blogCategoryId) {
        this.blogCategoryId = blogCategoryId;
    }

    public String getBlogCategoryName() {
        return blogCategoryName;
    }

    public void setBlogCategoryName(String blogCategoryName) {
        this.blogCategoryName = blogCategoryName;
    }

    public String getBlogCategoryDescription() {
        return blogCategoryDescription;
    }

    public void setBlogCategoryDescription(String blogCategoryDescription) {
        this.blogCategoryDescription = blogCategoryDescription;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "BlogCategory{" +
                "blogCategoryId=" + blogCategoryId +
                ", blogCategoryName='" + blogCategoryName + '\'' +
                ", blogCategoryDescription='" + blogCategoryDescription + '\'' +
                '}';
    }
}
