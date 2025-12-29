package com.genz.socio.dto.response;

public record EditPostRequest(String title, Long id) {

    @Override
    public Long id() {
        return id;
    }
}
