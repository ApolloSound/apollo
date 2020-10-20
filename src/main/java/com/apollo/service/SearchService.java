package com.apollo.service;

import com.apollo.service.dto.SearchDTO;

public interface SearchService {

    /**
     * Search in the application by keyword
     *
     * @param keyword the string to find
     * @return a searchDTO object containing all the data
     */
    SearchDTO search(String keyword);

}
