package ir.company.app.service.migmig;


import com.codahale.metrics.annotation.Timed;
import ir.company.app.service.dto.Gender;
import ir.company.app.service.dto.ProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events/")
public class EventResource {

    public static  int i=0;

    @GetMapping(value = "{code}/detail")
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
        eventDTO.setParticipantNumber(20);
        eventDTO.setView(200);
        eventDTO.setJoinStatus(JoinStatus.values()[i++%3]);
        eventDTO.setLatitude(35.714558);
        eventDTO.setLongitude(51.414440);
        return ResponseEntity.ok(eventDTO);

    }
    @GetMapping(value = "search")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventDTO>> events(@RequestParam("key") String key) {
        List<EventDTO> eventDTOS = new ArrayList<>();

        EventDTO event1 = new EventDTO();
        event1.setCode("123");
        event1.setPic("https://media.glassdoor.com/l/00/05/01/26/mhw-mt-shasta-climbing-event.jpg");
        event1.setTitle("کوه نوردی");
        event1.setPricing(PriceType.FREE);
        event1.setScore(4.5f);
        event1.setTime("07:00");
        event1.setDate("1397/11/28");
        event1.setCategoryId(2);
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
        event2.setCategoryId(3);


        event3.setCode("234");
        event3.setPic("https://d1zpvjny0s6omk.cloudfront.net/media/fileupload/2015/10/12/lombardi_stanzione-3521.jpg");
        event3.setTitle("کلاس نقاشی");
        event3.setPricing(PriceType.NON_FREE);
        event3.setScore(3f);
        event3.setTime("12:30");
        event3.setDate("1397/11/28");
        event3.setCreator("فرزاد صداقت بین");
        event3.setEditable(false);
        event3.setCategoryId(1);


        event4.setCode("456");
        event4.setPic("https://www.bransoncc.com/wp-content/uploads/2016/04/Soccer2.jpg");
        event4.setTitle("فوتبال سالنی");
        event4.setPricing(PriceType.DUTCH_TREAT);
        event4.setScore(4.2f);
        event4.setTime("21:00");
        event4.setDate("1397/11/28");
        event4.setCreator("فرزاد صداقت بین");
        event4.setEditable(false);
        event4.setCategoryId(7);


        eventDTOS.add(event1);
        eventDTOS.add(event2);
        eventDTOS.add(event3);
        eventDTOS.add(event4);

        return ResponseEntity.ok(eventDTOS);

    }
    @GetMapping(value = "{category}/titles")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<String>> getTitles(@PathVariable("category") String category) {
        List<String> titles = new ArrayList<>();
        titles.add("پایه ای بریم فوتبال");
        titles.add("پایه ای بریم دورهمی");
        titles.add("پایه ای بریم سینما");
        titles.add("پایه ای بریم کوه");
        return ResponseEntity.ok(titles);

    }


    @GetMapping(value = "{code}/participants")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<ProfileDTO>> participants(@PathVariable("code") String code, Pageable pageable) {
        List<ProfileDTO> profileDTOS = new ArrayList<>();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAvatar("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");
        profileDTO.setBirthYear(1368);
        profileDTO.setEmail("farzad.sedaghatbin@gmail.com");
        profileDTO.setFirstName("فرزاد");
        profileDTO.setGender(Gender.MALE);
        profileDTO.setId(1l);
        profileDTO.setTelegram("farzad");
        profileDTO.setInstagram("farzad_sedaghatbin");
        profileDTO.setLastName("صداقت بین");
        profileDTOS.add(profileDTO);
        Page<ProfileDTO> page = new PageImpl(profileDTOS, new PageRequest(0, 1), 1);
        return ResponseEntity.ok(page);

    }

    @GetMapping(value = "{code}/share")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<ShareDTO> share(@PathVariable("code") String code, Pageable pageable) {

        ShareDTO shareDto= new ShareDTO();
        shareDto.setUser("فرزاد صداقت");
        shareDto.setContent("میخواد با شما رویداد پایه ای بریم کوه را به اشتراک بگذارد دریافت پاشو از ");
        shareDto.setAndroidMarketURL("https://cafebazaar.ir");
        shareDto.setIosMarketURL("https://sibapp.com");
        return ResponseEntity.ok(shareDto);

    }


    @PostMapping(value = "{code}/rating")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> rating(@PathVariable("code") String code, @RequestParam("rating")Double rating) {

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PutMapping(value = "{code}/view")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> view(@PathVariable("code") String code) {

        return ResponseEntity.ok(HttpStatus.OK);

    }


    @PostMapping(value = "{code}/join")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> join(@PathVariable("code") String code) {

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/refuse")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> refuse(@PathVariable("code") String code) {

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<CreateEventDTO> createEvent(@RequestBody CreateEventDTO createEventDTO) {

        createEventDTO.setId(10l);
        return ResponseEntity.ok(createEventDTO);
    }

    @PostMapping(value = "/{code}/upload")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> uploadFile(@PathVariable("code") String code,@RequestParam("file") MultipartFile[] multipartFile) {

//        createEventDTO.setId(10l);
//        return ResponseEntity.ok(createEventDTO);
      return ResponseEntity.ok(multipartFile[0].getName());
    }

    @PutMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<CreateEventDTO> updateEvent(@RequestBody CreateEventDTO createEventDTO) {

        return ResponseEntity.ok(createEventDTO);
    }

    @GetMapping(value = "map")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MapEventDTO>> events(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
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
        event2.setCategoryId(2);
        event2.setLatitude(35.714558);
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
