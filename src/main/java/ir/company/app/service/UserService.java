package ir.company.app.service;

import ir.company.app.config.Constants;
import ir.company.app.domain.entity.*;
import ir.company.app.repository.*;
import ir.company.app.service.dto.GameLowDTO;
import ir.company.app.service.dto.HomeDTO;
import ir.company.app.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private SocialService socialService;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;
    @Inject
    private GameRepository gameRepository;

    @Inject
    private CategoryRepository categoryRepository;
    @Inject
    private ModalRepository modalRepository;

    @Inject
    private LevelRepository levelRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @PersistenceContext
    private EntityManager em;



    private void fillFriendly(String username, HomeDTO homeDTO, List<Game> friendly) {
        friendly = friendly.stream().filter(a -> isFirst(a, username)).collect(Collectors.toList());
        for (Game game : friendly) {
            GameLowDTO gameLowDTO = new GameLowDTO();
            GameLowDTO.User firstUser = new GameLowDTO.User();
            GameLowDTO.User secondUser = new GameLowDTO.User();
            gameLowDTO.second = secondUser;
            gameLowDTO.first = firstUser;
            gameLowDTO.gameId = game.getId();

            fillLowUser(username, game, secondUser);
            homeDTO.friendly.add(gameLowDTO);
        }
    }

    private static boolean isFirst(Game game, String username) {

        return game.getSecond().getLogin().equalsIgnoreCase(username);
    }

    private void fillLowUser(String username, Game game, GameLowDTO.User secondUser) {
        if (game.getFirst().getLogin().equalsIgnoreCase(username)) {
            secondUser.user = game.getSecond().getLogin();
            secondUser.avatar = game.getSecond().getAvatar();
        } else {

            secondUser.user = game.getFirst().getLogin();
            secondUser.avatar = game.getFirst().getAvatar();
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return new BigDecimal(dist)
            .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
            .doubleValue();
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

//    public Optional<User> completePasswordReset(String newPassword, String key) {
//        log.debug("Reset user password for reset key {}", key);
//
//        return userRepository.findOneByResetKey(key)
//            .filter(user -> {
//                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
//                return user.getResetDate().isAfter(oneDayAgo);
//            })
//            .map(user -> {
//                user.setPassword(passwordEncoder.encode(newPassword));
//                user.setResetKey(null);
//                user.setResetDate(null);
//                userRepository.save(user);
//                return user;
//            });
//    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::isActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }


    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
//        return userRepository.findOneByLogin(login).map(u -> {
//            u.getAuthorities().size();
//            return u;
//        });
        return null;
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
//        user.getAuthorities().size(); // eagerly load the association
        return user;
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
}
