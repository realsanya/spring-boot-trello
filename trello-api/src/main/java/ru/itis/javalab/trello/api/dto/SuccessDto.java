package ru.itis.javalab.trello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessDto  {

    private String message;

    public Long getCode() {
        return 0L;
    }

    public Boolean getIsError() {
        return false;
    }
}
