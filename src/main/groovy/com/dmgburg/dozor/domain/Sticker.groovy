package com.dmgburg.dozor.domain

import org.codehaus.jackson.annotate.JsonProperty

class Sticker {
    @JsonProperty(value = "file_id") String fileId
    int width
    int height
    Object thumb
    @JsonProperty(value = "file_size")int fileSize
}
