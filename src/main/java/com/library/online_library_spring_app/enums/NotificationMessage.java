package com.library.online_library_spring_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum NotificationMessage {
    REMINDER("Kiraye goturduyunuz ' %s ' kitabinin bitish tarixi yaxinlashir,zehmet olmasa kitabxanaya yaxinlashin!"),
    ADD_NEW_BOOK("Yeni kitab əlavə edildi: - %s . Zəhmət olmasa kitabxanaya baxın və maraqlanın!"),
    ADD_EVENT("Size kitabxanamizda kecirilecek '%s' tedbiri haqqinda melumat vermek isteyirik. %s tarixinde bas tutacaq olan tedbirimize sizi de gozleyirik");

    private String message;
    NotificationMessage(String message) {
        this.message = message;
    }

    public String formatMessageBook(String bookName) {
        return String.format(this.message, bookName);
    }

    public String formatMessageEvent(String eventName, String startTime) {
        return String.format(this.message, eventName, startTime);
    }
}
