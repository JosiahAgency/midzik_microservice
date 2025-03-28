package com.midziklabs.advertisement.requestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvertisementRejectionRequest {
    private String AdId;
    private String user_email;
    private String reviewer_id;
    private String rejectionReason;
}
