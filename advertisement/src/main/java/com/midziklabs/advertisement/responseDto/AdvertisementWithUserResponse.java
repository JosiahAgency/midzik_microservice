package com.midziklabs.advertisement.responseDto;

import java.util.Set;

import com.midziklabs.advertisement.broker.model.UserModelReplica;
import com.midziklabs.advertisement.model.CategoryModel;
import com.midziklabs.advertisement.model.LocationModel;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class shows shows the Advertisement and User information
 * Shownn in the reviewer dashboard
 */
@Data
@NoArgsConstructor
public class AdvertisementWithUserResponse {
    private Long id;
    private String email;
    private String title;
    private String status;
    private CategoryModel category;
    private Set<LocationModel> location;
    private String ad_visual;
    private String reviewer_id;
}
