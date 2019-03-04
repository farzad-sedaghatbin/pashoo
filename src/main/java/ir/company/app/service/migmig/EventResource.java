package ir.company.app.service.migmig;


import com.codahale.metrics.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events/")
public class EventResource {


    @GetMapping(value = "/{code}/detail")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<DetailEventDTO> myEvents(@PathVariable("code") String code) {
        DetailEventDTO eventDTO = new DetailEventDTO();
        eventDTO.setCode("123");
        eventDTO.setPic("https://media.glassdoor.com/l/00/05/01/26/mhw-mt-shasta-climbing-event.jpg");
        eventDTO.setTitle("کوه نوردی");
        eventDTO.setPricing(PriceType.FREE);
        eventDTO.setScore(4.5f);
        eventDTO.setTime("07:00");
        eventDTO.setDate("1397/11/28");
        eventDTO.setAddress("تهران دربند خیابان مژده پلاک 12");
        eventDTO.setCategory("ورزشی");
        eventDTO.setDescription("توضیحات این رویداد");
        eventDTO.setInstagram("http://instagram.com/pashoo");
        eventDTO.setTel("+989128626242");
        eventDTO.setTelegram("http://telegram.me/pashoo");

        return ResponseEntity.ok(eventDTO);

    }


    @GetMapping(value = "map")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MapEventDTO>> events(@RequestParam("latitude") Double latitude,@RequestParam("longitude") Double longitude) {
        List<MapEventDTO> eventDTOS = new ArrayList<>();

        MapEventDTO event1 = new MapEventDTO();
        event1.setCode("123");
        event1.setPic("https://media.glassdoor.com/l/00/05/01/26/mhw-mt-shasta-climbing-event.jpg");
        event1.setTitle("کوه نوردی");
        event1.setPricing(PriceType.FREE);
        event1.setScore(4.5f);
        event1.setTime("07:00");
        event1.setTime("07:00");
        event1.setCategoryId(1);
        event1.setDate("1397/11/28");
        event1.setLatitude(35.713107);
        event1.setLongitude(51.412740);

        MapEventDTO event2 = new MapEventDTO();
        MapEventDTO event3 = new MapEventDTO();
        MapEventDTO event4 = new MapEventDTO();

        event2.setCode("567");
        event2.setPic("https://www.parship.ie/pics/pictures/en_IE/single-life_dating-for-true-love282x172.jpg");
        event2.setTitle("قرار دو نفره");
        event2.setPricing(PriceType.DUTCH_TREAT);
        event2.setScore(5f);
        event2.setTime("20:00");
        event2.setDate("1397/11/28");
        event2.setLatitude(35.714558);
        event2.setCategoryId(2);
        event2.setLongitude(51.414440);

        event3.setCode("234");
        event3.setPic("https://d1zpvjny0s6omk.cloudfront.net/media/fileupload/2015/10/12/lombardi_stanzione-3521.jpg");
        event3.setTitle("کلاس نقاشی");
        event3.setPricing(PriceType.NON_FREE);
        event3.setScore(3f);
        event3.setTime("12:30");
        event3.setDate("1397/11/28");
        event3.setLatitude(35.716020);
        event3.setLongitude(51.425097);
        event3.setCategoryId(3);


        event4.setCode("456");
        event4.setPic("https://www.bransoncc.com/wp-content/uploads/2016/04/Soccer2.jpg");
        event4.setTitle("فوتبال سالنی");
        event4.setPricing(PriceType.DUTCH_TREAT);
        event4.setScore(4.2f);
        event4.setTime("21:00");
        event4.setDate("1397/11/28");
        event4.setLatitude(35.720862);
        event4.setLongitude(51.426506);
        event4.setCategoryId(4);


        eventDTOS.add(event1);
        eventDTOS.add(event2);
        eventDTOS.add(event3);
        eventDTOS.add(event4);

        return ResponseEntity.ok(eventDTOS);

    }
}
