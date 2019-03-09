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
        event1.setPic("https://media.glassdoor.com/l/00/05/01/26/mhw-mt-shasta-climbing-event.jpg");
        event1.setTitle("کوه نوردی");
        event1.setPricing(PriceType.FREE);
        event1.setScore(4.5f);
        event1.setTime("07:00");
        event1.setDate("1397/11/28");
        event1.setCreator("فرزاد صداقت بین");
        event1.setEditable(false);

        EventDTO event2 = new EventDTO();
        EventDTO event3 = new EventDTO();
        EventDTO event4 = new EventDTO();

        event2.setCode("567");
        event2.setPic("https://www.parship.ie/pics/pictures/en_IE/single-life_dating-for-true-love282x172.jpg");
        event2.setTitle("قرار دو نفره");
        event2.setPricing(PriceType.DUTCH_TREAT);
        event2.setScore(5f);
        event2.setTime("20:00");
        event2.setDate("1397/11/28");
        event2.setCreator("فرزاد صداقت بین");
        event2.setEditable(true);


        event3.setCode("234");
        event3.setPic("https://d1zpvjny0s6omk.cloudfront.net/media/fileupload/2015/10/12/lombardi_stanzione-3521.jpg");
        event3.setTitle("کلاس نقاشی");
        event3.setPricing(PriceType.NON_FREE);
        event3.setScore(3f);
        event3.setTime("12:30");
        event3.setDate("1397/11/28");
        event3.setCreator("فرزاد صداقت بین");
        event3.setEditable(false);


        event4.setCode("456");
        event4.setPic("https://www.bransoncc.com/wp-content/uploads/2016/04/Soccer2.jpg");
        event4.setTitle("فوتبال سالنی");
        event4.setPricing(PriceType.DUTCH_TREAT);
        event4.setScore(4.2f);
        event4.setTime("21:00");
        event4.setDate("1397/11/28");
        event4.setCreator("فرزاد صداقت بین");
        event4.setEditable(false);


        eventDTOS.add(event1);
        eventDTOS.add(event2);
        eventDTOS.add(event3);
        eventDTOS.add(event4);

        return ResponseEntity.ok(eventDTOS);

    }
}
