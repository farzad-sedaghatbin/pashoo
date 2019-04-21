package ir.company.app.service.migmig;

import org.springframework.web.multipart.MultipartFile;

public class UploadDTO {

    private long eventId;
    private MultipartFile file;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
