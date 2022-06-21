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

package cl.ucn.disc.dsm.pictwin.backend.services;

import cl.ucn.disc.dsm.pictwin.backend.dao.PicRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backend.dao.UserRepository;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * THe {@link PicTwin} implementation
 *
 * @author Cross
 */

@Slf4j
@Service
public class PicTwinImpl implements PicTwin {

    /**
     * The Hasher
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    /**
     * The random
     */
    private final static Random RANDOM = new Random();

    /**
     * The pic repository
     */
    private final PicRepository picRepository;

    /**
     * The twin repository
     */
    private final TwinRepository twinRepository;

    /**
     * The user repository
     */
    private final UserRepository userRepository;

    /**
     * Build the PicTwinImplementation
     *
     * @param picRepository to use
     * @param twinRepository to use
     * @param userRepository to use
     */
    @Autowired
    public PicTwinImpl(PicRepository picRepository, TwinRepository twinRepository, UserRepository userRepository){
        this.picRepository = picRepository;
        this.twinRepository = twinRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a user with a specific, hashed password
     *
     * @param user to create
     * @param password to hash
     * @return the user created
     */
    @Override
    @Transactional
    public User create(@NonNull User user, @NonNull String password){

        // If the user already exists
        if (this.userRepository.findOneByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("The User with email <" + user.getEmail() + "> is already in the system");
        }

        // Using the password encoder to hash
        String passwdHash = PASSWORD_ENCODER.encode(password);

        // Replace the password with the hash
        user.setPassword(passwdHash);

        // Save into the repository
        return this.userRepository.save(user);
    }

    /**
     * Return the user with the email and password
     *
     * @param email to search
     * @param password to use
     * @return the user
     */
    @Override
    public User authenticate(@NonNull String email, @NonNull String password){

        // Find by email
        Optional<User> userOptional = this.userRepository.findOneByEmail(email);
        log.debug("User: {}", userOptional);

        return userOptional.orElseThrow(() -> new RuntimeException("Wrong Credentials or User Not Found"));
    }

    /**
     * Create a Twin using the pic from the user
     *
     * @param pic to use
     * @param idUser who created the pic
     * @return the twin created
     */
    @Override
    @Transactional
    public Twin createTwin(@NonNull Pic pic, @NonNull Long idUser) {

        //The user
        User owner = this.userRepository.findById(idUser).orElseThrow();

        log.debug("Pics: {} in User: {}", owner.getTwins().size(), owner.getEmail());

        //Set the user
        pic.setOwner(owner);

        //Store the pic
        this.picRepository.save(pic);

        //Get all the pics
        List<Pic> pics = this.picRepository.findAll();
        log.debug("Number of pics in the database: {}", pics.size());

        //Remove my own pics
        List<Pic> picsFiltered = pics.stream().filter(p -> !p.getOwner().getId().equals(idUser)).toList();
        if (picsFiltered.size() == 0){
            log.warn("Re-using Pics from database");
            picsFiltered = pics;
        }

        //Select a random pic
        //FIXME: Sort by views and select the least used
        Pic your = picsFiltered.size() == 0 ? pic : picsFiltered.get(RANDOM.nextInt(picsFiltered.size()));

        //Increment the views
        your.incrementViews();

        //Save the increment
        this.picRepository.save(your);

        //Store the twin
        Twin twin = Twin.builder()
                .my(pic)
                .yours(your)
                .owner(owner)
                .build();

        //Save the twin
        this.twinRepository.save(twin);

        //Add the twin to the user
        owner.add(twin);
        this.userRepository.save(owner);

        return twin;

    }

    /**
     * Dislike a pic in a twin
     *
     * @param idTwin to dislike
     * @param idUser who disliked the twin
     */
    @Override
    @Transactional
    public void dislike(@NonNull Long idTwin, @NonNull Long idUser){

        //Retrieve the twin
        Optional<Twin> optionalTwin = this.twinRepository.findById(idTwin);

        //Check if it exists
        Twin twin = optionalTwin.orElseThrow(() -> new RuntimeException("Cannot find twin with this id: " +idTwin));

        //Check the owner of the pic
        if (!idUser.equals(twin.getMy().getOwner().getId())){
            throw new RuntimeException("Twin id<" + idTwin + "> not owned by User id<" + idUser + ">!");
        }

        //Set the new dislike and save
        twin.setDislike(true);
        this.twinRepository.save(twin);

        //Increment the dislike of the twin and save
        Pic yours = twin.getYours();
        yours.incrementDislikes();
        this.picRepository.save(yours);

        //Increment the strikes in user and save
        User user = yours.getOwner();
        user.incrementStrikes();
        this.userRepository.save(user);
    }

    /**
     * @return the number of users in the system
     */
    @Override
    public Long getUserSize(){
        return this.userRepository.count();
    }

}
