package com.midziklabs.advertisement.responseDto;

import java.util.Set;

import org.springframework.core.io.Resource;

import com.midziklabs.advertisement.model.CategoryModel;
import com.midziklabs.advertisement.model.LocationModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AdvertisementByUserResponse {
    private Long id;
    private String title;
    private String description;
    private Integer userId;
    private Integer reviewer_id;
    private Boolean is_approved;
    private CategoryModel category;
    private Set<LocationModel> location;
    private Resource ad_visual;
    private Integer loops;
}
