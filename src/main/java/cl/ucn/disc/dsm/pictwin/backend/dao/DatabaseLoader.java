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

package cl.ucn.disc.dsm.pictwin.backend.dao;

import cl.ucn.disc.dsm.pictwin.backend.Utils;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
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
     * The PicTwin implementation
     */
    private final PicTwin picTwin;

    /**
     * The constructor
     *
     * @param picTwin to use
     */
    public DatabaseLoader(@Autowired PicTwin picTwin){
        this.picTwin = picTwin;
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

        //check if the database is empty
        if (this.picTwin.getUserSize() != 0){
            log.info("Database already seeded, skipping!");
            return;
        }

        log.warn("No data found in database, seeding the database..");

        //The main user
        User user = User.builder()
                .email("ihikari@cnu.com")
                .strikes(0)
                .password("ihikari123")
                .build();
        this.picTwin.create(user, "Inoue Hikari");
        Utils.printObject("User created:", user);

        //Creating the first twin
       Twin twin1 = this.picTwin.createTwin(Pic.builder()
               .name("The first Pic: UCN")
               .latitude(-23.6806026)
               .longitude(-70.4121427)
               .error(3.5)
               .owner(user)
               .build(), user.getId());
       Utils.printObject("twin1 created: ", twin1);

        //Creating the second twin
        Twin twin2 = this.picTwin.createTwin(Pic.builder()
                .name("The second Pic: Parque de los Eventos")
                .latitude(-23.6281221)
                .longitude(-70.3952909)
                .error(5.7)
                .owner(user)
                .build(), user.getId());
        Utils.printObject("twin2 created: ", twin2);

        //Creating the third twin
        Twin twin3 = this.picTwin.createTwin(Pic.builder()
                .name("The third Pic: Quebrada Carrizo")
                .latitude(-23.6977891)
                .longitude(-70.4105903)
                .error(5.7)
                .owner(user)
                .build(), user.getId());
        Utils.printObject("twin3 created: ", twin3);

        //Creating the fourth twin
        Twin twin4 = this.picTwin.createTwin(Pic.builder()
                .name("The fourth Pic: Balneario Juan Lopez")
                .latitude(-23.5114433)
                .longitude(-70.5218646)
                .error(0.3)
                .owner(user)
                .build(), user.getId());
        Utils.printObject("twin4 created: ", twin4);

        log.info("Database DataLoader: Done.");
    }

}

