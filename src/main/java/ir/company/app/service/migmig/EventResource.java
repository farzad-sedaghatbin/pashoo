package ir.company.app.service.migmig;


import com.codahale.metrics.annotation.Timed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
