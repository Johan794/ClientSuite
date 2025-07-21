package com.seek.TalentSuite.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Paginated response containing content and pagination metadata.")
public class PageResponse<T>{
    @Schema(description = "The list of items for the current page.")
    private List<T> content;
    @Schema(description = "The current page number (zero-based).", example = "0")
    private int page;
    @Schema(description = "The size of the page (number of items per page).", example = "10")
    private int size;
    @Schema(description = "The total number of elements across all pages.", example = "100")
    private long totalElements;
    @Schema(description = "The total number of pages available.", example = "10")
    private int totalPages;
    @Schema(description = "Indicates if this is the last page.", example = "false")
    private boolean last;
}
