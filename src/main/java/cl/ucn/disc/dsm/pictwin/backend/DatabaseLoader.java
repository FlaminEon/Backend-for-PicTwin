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

package cl.ucn.disc.dsm.pictwin.backend;

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
}

