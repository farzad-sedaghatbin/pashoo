package ir.company.app.service.migmig;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import ir.company.app.config.Constants;
import ir.company.app.domain.Authority;
import ir.company.app.domain.entity.Avatar;
import ir.company.app.domain.entity.User;
import ir.company.app.repository.AuthorityRepository;
import ir.company.app.repository.ErrorLogRepository;
import ir.company.app.repository.UserRepository;
import ir.company.app.security.AuthoritiesConstants;
import ir.company.app.security.SecurityUtils;
import ir.company.app.security.jwt.JWTConfigurer;
import ir.company.app.security.jwt.TokenProvider;
import ir.company.app.service.UserService;
import ir.company.app.service.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FarzadUserService {

    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ErrorLogRepository errorLogRepository;

    @PersistenceContext
    private EntityManager em;

    @Inject
    public FarzadUserService(TokenProvider tokenProvider, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, ErrorLogRepository errorLogRepository) {
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.errorLogRepository = errorLogRepository;
        Constants.index = new AtomicLong(userRepository.count() + 100);
    }

    @RequestMapping(value = "/1/user_authenticate", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDTO.getUsername().toLowerCase(), loginDTO.getPassword());
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            if (authentication.isAuthenticated()) {
                //todo authenticate

                SecurityContextHolder.getContext().setAuthentication(authentication);
//                boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
                String jwt = tokenProvider.createToken(authentication, true);
                response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
                User user = userRepository.findOneByLogin(loginDTO.getUsername().toLowerCase());
                user.setPushSessionKey(loginDTO.getDeviceToken());
                userRepository.save(user);
            }
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok("401");

    }

    @RequestMapping(value = "/1/signup", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> signUp(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) throws JsonProcessingException {


        User user = userRepository.findOneByLogin(userDTO.getMobile());

        if (user == null) {

            user = new User();
            user.setLogin(userDTO.getMobile());
            user.setActivated(true);
            user.setCreatedBy("system");
            user.setPassword(passwordEncoder.encode("123"));
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authorityRepository.findOne(AuthoritiesConstants.USER));
            user.setMobile(userDTO.getMobile());
            user.setGuest(false);
            userRepository.save(user);

        }
        int START = 1000;
        int END = 9999;
        Random random = new Random();
        long range = END - START + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * random.nextDouble());
        int randomNumber = (int) (fraction + START);
        String s = String.valueOf(randomNumber);

        user.setResetKey(s);
//            user1.setResetDate();
        userRepository.save(user);
        try {
            String tel = user.getMobile();

            KavenegarApi api = new KavenegarApi("6F442B597454543263327452344D3876636C7443735034476B7170577571376F");
//                api.send("10006006606600", tel, "شماره بازیابی :  " + s);

            api.verifyLookup(tel, s, "pashosignup");

        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
            return ResponseEntity.ok("302");
        } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("ApiException : " + ex.getMessage());
            return ResponseEntity.ok("302");
        }
//            MailUtils.sendEmail("farzad.sedaghatbin@gmail.com", s, "ResetPassword");

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/1/changeAvatar", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> changeAvatar(@Valid @RequestBody String data) {

//        String[] s = data.split(",");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        user.setAvatar(data);
        userRepository.save(user);
        return ResponseEntity.ok("200");
    }


//    @RequestMapping(value = "/1/videoLimit", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> videoLimit(@Valid @RequestBody String data) {
//
//        User user = userRepository.findOneByLogin(data.toLowerCase());
//        if (user.getLastVideo() == null || (user.getLastVideo() != null && ((ZonedDateTime.now().toInstant().toEpochMilli() - user.getLastVideo().toInstant().toEpochMilli()) / 360000) >= 1)) {
//            user.setLastVideo(ZonedDateTime.now());
//            userRepository.save(user);
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }

    @RequestMapping(value = "/1/inviteFriend", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> inviteFriend(@Valid @RequestBody String data) {
        String[] s = data.split(",");
        User user = userRepository.findOneByLogin(s[1].toLowerCase());
        String returns;
        if ((s[1] != "null") && user.getInvitedUser1() == null) {
            User invited = userRepository.findOneByLogin(s[0].toLowerCase());
            if (invited != null) {
                userRepository.save(invited);
                user.setInvitedUser1(invited);
                returns = "200";
            } else {
                returns = "404";
            }
        } else if ((s[1] != "null" && s[1] != "") && user.getInvitedUser2() == null) {
            User invited = userRepository.findOneByLogin(s[0].toLowerCase());
            if (invited != null) {
                userRepository.save(invited);
                user.setInvitedUser2(invited);
                returns = "200";
            } else {
                returns = "404";
            }
        } else if ((s[1] != "null" && s[1] != "") && user.getInvitedUser3() == null) {
            User invited = userRepository.findOneByLogin(s[0].toLowerCase());
            if (invited != null) {

                user.setInvitedUser3(invited);
                userRepository.save(invited);
                returns = "200";
            } else {
                returns = "404";
            }
        } else {
            returns = "333";
        }
        userRepository.save(user);


        return ResponseEntity.ok(returns);
    }

//    @RequestMapping(value = "/1/forget", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> forget(@Valid @RequestBody String username) {
//        //todo forget scenario send email or sms
//        User user = userRepository.findOneByLogin(username.toLowerCase());
//        if (user != null) {
//
//            int START = 1000;
//            int END = 9999;
//            Random random = new Random();
//            long range = END - START + 1;
//            // compute a fraction of the range, 0 <= frac < range
//            long fraction = (long) (range * random.nextDouble());
//            int randomNumber = (int) (fraction + START);
//            String s = String.valueOf(randomNumber);
//            User user1 = user;
//            user1.setResetKey(s);
////            user1.setResetDate();
//            userRepository.save(user1);
//            try {
//                String tel = user1.getMobile();
//
//                KavenegarApi api = new KavenegarApi("5635717141617A52534F636F49546D38454E647870773D3D");
////                api.send("10006006606600", tel, "شماره بازیابی :  " + s);
//
//                api.verifyLookup(tel, s, "dagalareset");
//
//            } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
//                System.out.print("HttpException  : " + ex.getMessage());
//                return ResponseEntity.ok("302");
//            } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
//                System.out.print("ApiException : " + ex.getMessage());
//                return ResponseEntity.ok("302");
//            }
////            MailUtils.sendEmail("farzad.sedaghatbin@gmail.com", s, "ResetPassword");
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }


    @RequestMapping(value = "/1/notification", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> notification(@Valid @RequestBody String username) {
        //todo forget scenario send email or sms
//            user1.setResetDate();
        try {
//                String tel = "09397408122,09035470603,09357939303,09137009097,09128385011,09379614885,09132133219,09389213598,09363148990,09187799917,09131403235,09102472447,09179307173,09382084405,09386636862,09384873311,09120563496,09333634218,,09388889552,09122462180,09128626242";

            KavenegarApi api = new KavenegarApi("5635717141617A52534F636F49546D38454E647870773D3D");

//                for (String s : tel.split(",")){

            api.send("10006006606600", "09128626242", "لیگ سکه ای شروع شد اطلاعات بیشتر :\r\n http://t.me/dagala ");

//                    api.se(s, "سکه", "dagalaLeague");
//                }

        } catch (HttpException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("HttpException  : " + ex.getMessage());
            return ResponseEntity.ok("302");
        } catch (ApiException ex) { // در صورتی که خروجی وب سرویس 200 نباشد این خطارخ می دهد.
            System.out.print("ApiException : " + ex.getMessage());
            return ResponseEntity.ok("302");
        }
//            MailUtils.sendEmail("farzad.sedaghatbin@gmail.com", s, "ResetPassword");
        return ResponseEntity.ok("200");
    }

    @RequestMapping(value = "/1/confirmOTP", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> confirmOTP(@Valid @RequestBody ConfirmOTPDTO data, HttpServletResponse response) {
        //todo forget scenario send email or sms

        Optional<User> user = userRepository.findOneByResetKeyAndMobile(data.getCode(), data.getMobile());
        if (user.isPresent()) {
            User user1 = user.get();
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(data.getMobile(), "123");
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication, true);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

            user1.setPassword(passwordEncoder.encode("123"));
            user1.setResetDate(ZonedDateTime.now());
            userRepository.save(user1);
            return ResponseEntity.ok("200");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

/*    @RequestMapping(value = "/1/confirmReset", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> confirmReset(@Valid @RequestBody ConfirmOTPDTO data) {
        //todo forget scenario send email or sms

        Optional<User> user = userRepository.findOneByResetKey(data.getCode());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setPassword(passwordEncoder.encode(data.getMobile()));
            user1.setResetDate(ZonedDateTime.now());
            userRepository.save(user1);
            return ResponseEntity.ok("200");
        } else {
            return ResponseEntity.ok("201");
        }
    }*/


//    @RequestMapping(value = "/1/changePassword", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> changePassword(@Valid @RequestBody String data) {
//        String[] s = data.split(",");
//        User user1 = userRepository.findOneByLogin(s[0].toLowerCase());
//        user1.setMobile(passwordEncoder.encode(s[1]));
//        userRepository.save(user1);
//        return ResponseEntity.ok("200");
//    }
//
//    @RequestMapping(value = "/2/changePassword", method = RequestMethod.POST)
//    @Timed
//    @CrossOrigin(origins = "*")
//
//    public ResponseEntity<?> changePasswordV2(@Valid @RequestBody String data) {
//        String[] s = data.split(",");
//        User user1 = userRepository.findOneByLogin(s[0].toLowerCase());
//        if (user1.getMobile().equalsIgnoreCase(passwordEncoder.encode(s[2]))) {
//            user1.setMobile(passwordEncoder.encode(s[1]));
//            userRepository.save(user1);
//            return ResponseEntity.ok("200");
//        } else {
//            return ResponseEntity.ok("201");
//        }
//    }


    @RequestMapping(value = "/1/profile", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> setProfile(@RequestBody ProfileDTO profileDTO) throws JsonProcessingException {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        return ResponseEntity.ok(profileDTO);

    }


    @RequestMapping(value = "/1/profile", method = RequestMethod.GET)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> getProfile(@RequestBody String username) throws JsonProcessingException {
        ProfileDTO profileDTO = new ProfileDTO();
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());

        return ResponseEntity.ok(profileDTO);

    }


    @RequestMapping(value = "/1/tempUser", method = RequestMethod.POST)
    @Timed
    @CrossOrigin(origins = "*")

    public ResponseEntity<?> tempUser(HttpServletResponse response) throws JsonProcessingException {

        User user = new User();

        user.setLogin("dagala" + Constants.index.incrementAndGet());
        user.setPassword(user.getLogin());
        user.setGuestId(user.getLogin());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);
        user.setCreatedBy("system");
        user.setGuest(true);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityRepository.findOne(AuthoritiesConstants.USER));
//        user.setAuthorities(authoritie);
//        user.setMobile(passwordEncoder.encode(userDTO.getMobile()));
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
//        user.setMobile(userDTO.getMobile());
//        user.setGender(userDTO.getGender());

        user.setAvatar("img/default.png");
        userRepository.save(user);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(user.getLogin(), user.getLogin());
        HomeDTO guestDTO = new HomeDTO();
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        if (authentication.isAuthenticated()) {

            SecurityContextHolder.getContext().setAuthentication(authentication);
//                boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, true);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);


            guestDTO.token = jwt;
            guestDTO.guest = true;
            guestDTO.user = user.getLogin();
        }
        return ResponseEntity.ok(guestDTO);

    }


}
