package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Page<News> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "newsPostTime";
        }

        Pageable pageable;
        if(sortBy == "newsPostTime"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return newsRepository.findAll(pageable);
    }


    public News findById(int newsId) {
        return newsRepository.findById(newsId).orElseThrow(
                ()->new RuntimeException("News not found!")
               );
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public void deleteById(int newsId) {
        newsRepository.deleteById(newsId);
    }
}
