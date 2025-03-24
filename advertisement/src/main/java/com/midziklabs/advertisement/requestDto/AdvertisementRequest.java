package com.midziklabs.advertisement.requestDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.midziklabs.advertisement.model.AdvertisementModel;
import com.midziklabs.advertisement.model.CategoryModel;
import com.midziklabs.advertisement.model.LocationModel;
import com.midziklabs.advertisement.service.CategoryService;
import com.midziklabs.advertisement.service.FileStorageService;
import com.midziklabs.advertisement.service.LocationService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {
    private String title;
    private String description;
    private Long category_id;
    private MultipartFile visuals;
    private String location_ids;
    private Integer loops;

    public AdvertisementModel toAdvertisementModel(String file_path, Optional<CategoryModel> category,Set<LocationModel> location_set_list) {
        AdvertisementModel model = new AdvertisementModel();
        model.setTitle(this.getTitle());
        model.setDescription(this.getDescription());
        model.setCategory(category.get());
        model.setStatus("Pending");
        model.setReviewer_id(1);
        model.setUserId(2);
        model.setFile_path(file_path);
        model.setLocation(location_set_list);
        model.setLoops(loops);
        return model;
    }
}
