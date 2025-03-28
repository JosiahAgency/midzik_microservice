package com.midziklabs.advertisement.controller;

import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.midziklabs.advertisement.exceptions.StorageFileNotFoundException;
import com.midziklabs.advertisement.model.AdvertisementModel;
import com.midziklabs.advertisement.requestDto.AdvertisementRejectionRequest;
import com.midziklabs.advertisement.requestDto.AdvertisementRequest;
import com.midziklabs.advertisement.responseDto.AdvertisementByUserResponse;
import com.midziklabs.advertisement.responseDto.AdvertisementWithUserResponse;
import com.midziklabs.advertisement.service.AdvertisementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("/api/v1/advertisement")
@RequiredArgsConstructor
@Slf4j
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomePage() {
        return ResponseEntity.ok().body("Hello World");
    }
    
    @PostMapping("/")
    public ResponseEntity<?> addAdvertisement(
        @RequestParam("title") String title,
        @RequestParam("description") String description, 
        @RequestParam("category_id") String category_id, 
        @RequestParam("location_ids") String location_ids,
        @RequestParam("loops") String loops,
        @RequestPart("visuals") MultipartFile visuals) {
        try {
            log.error("LOcation ids received: "+location_ids);
            AdvertisementRequest request = new AdvertisementRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setCategory_id(Long.valueOf(category_id));
            request.setVisuals(visuals);
            request.setLocation_ids(location_ids);
            request.setLoops(Integer.valueOf(loops));
            AdvertisementModel savedAdverisement = advertisementService.addAdvertisement(request);
            URI location = URI.create(String.format("/api/v1/advertisement/%s", savedAdverisement.getId()));
            return ResponseEntity.created(location).body(savedAdverisement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to save the advertisement: "+e.getMessage());
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<?> getAdvertisements() {
        try {
            List<AdvertisementWithUserResponse> advertisement_list = advertisementService.getAllAdvertisements();
            if (advertisement_list.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok().body(advertisement_list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to get advertisements: "+e.getMessage());
        }
    }
    
    // TODO: Get Advertisement By Location/Screen
    @GetMapping("/location/id/{location_id}")
    public ResponseEntity getAdvertisementByLocationId(@PathVariable("location_id") Integer id) {
        return ResponseEntity.ok().body(advertisementService.getAdvertisementsByLocation(Long.valueOf(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAdvertisementByUser(@RequestHeader("Authorization") String authString){
        try {
            List<AdvertisementModel> ads = advertisementService.getAdvertisementByUser(authString);
            return ResponseEntity.ok().body(ads);
        } catch (Exception e) {
            log.error("ADvertisement by user error: ", e);
            return ResponseEntity.internalServerError().body("An error occurred when try to get users\' adveertisements");
        }   
    }
    
    @GetMapping("/visual")
    public ResponseEntity<?> getAdvertisementVisual(@RequestParam("filename") String filename){
        try {
            Resource file = advertisementService.getAdvertisementVisual(filename);
            String contentType = Files.probeContentType(file.getFile().toPath());
            if (contentType == null) {
                contentType = "application/octet-stream"; // Default content type
            }
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
        } catch (StorageFileNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching file");
        }
    }
    
    @GetMapping("/approve")
    public ResponseEntity<?> approveAdvertisement(@RequestParam("id") String id){
        try {
            AdvertisementModel ad = advertisementService.approveAdvertisement(Long.valueOf(id));
            return ResponseEntity.ok().body(ad);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to approve the advertisement: "+e.getMessage());
        }
    }
    @PostMapping("/reject")
    public ResponseEntity<?> rejectAdvertisement(@RequestBody AdvertisementRejectionRequest rejectionRequest){
        try {
            advertisementService.rejectAdvertisement(rejectionRequest);
            return ResponseEntity.ok().body("Advertisement rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred when trying to reject the advertisement: "+e.getMessage());
        }
    }
}
