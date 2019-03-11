package ir.company.app.service.migmig;


import com.codahale.metrics.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationResource {


    @PostMapping(value = "{requestId}/approved")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> approved(@PathVariable("requestId") String requestId) {

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{requestId}/cancel")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> cancel(@PathVariable("requestId") String requestId) {

        return ResponseEntity.ok(HttpStatus.OK);

    }


    @GetMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<NotificationDTO>> listNotification(Pageable pageable) {


        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        NotificationDTO notif= new NotificationDTO();
        notif.setRequestId("UBT123");
        notif.setPending(true);
        notif.setText("کاربر فرزاد صداقت بین علاقه دارد به رویداد کوه نوردی ملحق شود");
        notif.setRelatedEventcode("codeEvent");
        notif.setRelatedUserId("userId");
        notificationDTOS.add(notif);
        NotificationDTO notif1= new NotificationDTO();
        notif1.setPending(false);
        notif1.setText("یک متن معمولی");
        notificationDTOS.add(notif1);
        return ResponseEntity.ok(new PageImpl<>(notificationDTOS,new PageRequest(0,2),2));

    }


}
