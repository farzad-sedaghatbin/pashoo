package ir.company.app.service.migmig;


import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import ir.company.app.domain.entity.User;
import ir.company.app.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home/")
public class HomeResource {


    @GetMapping(value = "my-event")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MyEventDTO>> myEvents() {
        List<MyEventDTO> myEventDTOS = new ArrayList<>();
        MyEventDTO event1 = new MyEventDTO();
        event1.setCode("123");
        event1.setColor("#aa22bb");
        event1.setTitle("کوه نوردی");
        MyEventDTO event2 = new MyEventDTO();
        MyEventDTO event3 = new MyEventDTO();
        MyEventDTO event4 = new MyEventDTO();
        event2.setCode("567");
        event2.setColor("#aa22bb");
        event2.setTitle("قرار دو نفره");
        event3.setCode("234");
        event3.setColor("#123456");
        event3.setTitle("کلاس نقاشی");
        event4.setCode("456");
        event4.setColor("#88ff44");
        event4.setTitle("فوتبال سالنی");
        myEventDTOS.add(event1);
        myEventDTOS.add(event2);
        myEventDTOS.add(event3);
        myEventDTOS.add(event4);
        return ResponseEntity.ok(myEventDTOS);

    }

    @GetMapping(value = "events")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventDTO>> events(@RequestParam("type") EventType eventType) {
        List<EventDTO> eventDTOS = new ArrayList<>();

        EventDTO event1 = new EventDTO();
        event1.setCode("123");
        event1.setPic("http://pashoo.com/pic.jpg");
        event1.setTitle("کوه نوردی");
        event1.setPricing(PriceType.FREE);
        event1.setScore(4.5f);
        event1.setTime("07:00");
        event1.setDate("1397/11/28");

        EventDTO event2 = new EventDTO();
        EventDTO event3 = new EventDTO();
        EventDTO event4 = new EventDTO();

        event2.setCode("567");
        event2.setPic("http://pashoo.com/pic.jpg");
        event2.setTitle("قرار دو نفره");
        event2.setPricing(PriceType.DUTCH_TREAT);
        event2.setScore(5f);
        event1.setTime("20:00");
        event1.setDate("1397/11/28");


        event3.setCode("234");
        event3.setPic("http://pashoo.com/pic.jpg");
        event3.setTitle("کلاس نقاشی");
        event3.setPricing(PriceType.NON_FREE);
        event3.setScore(3f);
        event1.setTime("12:30");
        event1.setDate("1397/11/28");


        event4.setCode("456");
        event4.setPic("http://pashoo.com/pic.jpg");
        event4.setTitle("فوتبال سالنی");
        event4.setPricing(PriceType.DUTCH_TREAT);
        event4.setScore(4.2f);
        event1.setTime("21:00");
        event1.setDate("1397/11/28");


        eventDTOS.add(event1);
        eventDTOS.add(event2);
        eventDTOS.add(event3);
        eventDTOS.add(event4);

        return ResponseEntity.ok(eventDTOS);

    }
}
