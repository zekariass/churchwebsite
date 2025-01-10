package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Video;
import com.churchwebsite.churchwebsite.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Video save(Video video) {
        return videoRepository.save(video);
    }

    public Page<Video> getVideoList(int page, Integer pageSize, Sort uploadTime) {
        Pageable pageable = PageRequest.of(page-1, pageSize, uploadTime);

        return videoRepository.findAll(pageable);
    }

    public Page<Video> findByArchived(Boolean archived, int page, Integer pageSize, Sort uploadTime) {
        Pageable pageable = PageRequest.of(page-1, pageSize, uploadTime);

        return videoRepository.findByArchived(archived, pageable);
    }
}
