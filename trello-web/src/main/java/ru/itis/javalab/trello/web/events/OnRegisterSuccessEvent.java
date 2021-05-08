package ru.itis.javalab.trello.web.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import ru.itis.javalab.trello.api.dto.UserDto;

@Getter
@Setter
public class OnRegisterSuccessEvent extends ApplicationEvent {

    private UserDto userDto;
    private String contextPath;

    public OnRegisterSuccessEvent(UserDto userDto, String contextPath) {
        super(userDto);
        this.userDto = userDto;
        this.contextPath = contextPath;
    }

}
