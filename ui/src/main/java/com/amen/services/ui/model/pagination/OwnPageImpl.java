package com.amen.services.ui.model.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author amen
 * @project configlut-ms
 * @created 17.07.19
 */
public class OwnPageImpl<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OwnPageImpl(@JsonProperty("content") List<T> content,
                       @JsonProperty("number") int number,
                       @JsonProperty("size") int size,
                       @JsonProperty("totalElements") Long totalElements,
                       @JsonProperty("pageable") JsonNode pageable,
                       @JsonProperty("last") boolean last,
                       @JsonProperty("totalPages") int totalPages,
                       @JsonProperty("sort") JsonNode sort,
                       @JsonProperty("first") boolean first,
                       @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(
                pageable.get("pageNumber").asInt(),
                pageable.get("pageSize").asInt()), totalElements);
    }

    public OwnPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public OwnPageImpl(List<T> content) {
        super(content);
    }

    public OwnPageImpl() {
        super(new ArrayList());
    }
}
