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

import cl.ucn.disc.dsm.pictwin.backend.jpa.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The backend main class
 *
 * @author Cross
 */
@SpringBootApplication
@Slf4j
public class BackendApplication {

    /**
     * Main
     * @param args to use
     */
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     *
     * @param repository to use
     * @return the runner
     */
    @Bean
    public CommandLineRunner seed(PicRepository repository){
        return (args) -> {
            repository.save(new Pic());
        };
    }
}
