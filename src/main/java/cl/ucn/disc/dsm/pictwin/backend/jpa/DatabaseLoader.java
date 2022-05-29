/*
 * Copyright 2022 Tomas Venegas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cl.ucn.disc.dsm.pictwin.backend.jpa;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The Database loader
 *
 * @author Cross
 */
@Slf4j
@Component
public class DatabaseLoader implements CommandLineRunner {

    /**
     * The User repository
     */
    UserRepository userRepository;

    /**
     * The Twin repository
     */
    TwinRepository twinRepository;

    /**
     * The Pic repository
     */
    PicRepository picRepository;

    /**
     * The constructor
     *
     * @param userRepository a repository
     * @param twinRepository another repository
     * @param picRepository yet another repository
     */
    @Autowired
    public DatabaseLoader(UserRepository userRepository, TwinRepository twinRepository, PicRepository picRepository){
        this.userRepository = userRepository;
        this.twinRepository = twinRepository;
        this.picRepository = picRepository;
    }
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception an error
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Database DataLoader: Starting seeder ..");

        if (this.userRepository.count() == 0){
            log.warn("No data found in database");
        }

        User user = User.builder()
                .email("ihikari@cnu.com")
                .strikes(0)
                .password("ihikari123")
                .build();
        log.debug("Saving user:{}", user);
        this.userRepository.save(user);

        Pic p1 = Pic.builder()
                .name("Pic 1")
                .views(0)
                .dislikes(0)
                .build();
        log.debug("Saving Pic: {}", p1);
        this.picRepository.save(p1);

        Pic p2 = Pic.builder()
                .name("Pic 2")
                .views(0)
                .dislikes(0)
                .build();
        log.debug("Saving Pic: {}", p2);
        this.picRepository.save(p2);

        Twin twin = Twin.builder()
                .my(p1)
                .yours(p2)
                .build();
        log.debug("Saving Twin: {}", twin);
        this.twinRepository.save(twin);

        user.add(twin);
        log.debug("Re-saving user: {}", user);
        this.userRepository.save(user);

        log.info("Database Data Loader: Done.");
    }

}

