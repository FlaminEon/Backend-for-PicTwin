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

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;

/**
 * PicTwin's Operations.
 *
 * @author Cross.
 */
public interface PicTwin {

    /**
     * Create a user with a specific password.
     *
     * @param user to create.
     * @param password to hash.
     *
     * @return the user created.
     */
    User create(User user, String password);

    /**
     * Return the user with the email and password.
     *
     * @param email to search.
     * @param password to use.
     *
     * @return the user.
     */
    User authenticate(String email, String password);

    /**
     * Create a twin using the pic from the user.
     *
     * @param pic to use.
     * @param idUser who created the pic.
     *
     * @return the twin created.
     */
    Twin createTwin(Pic pic, Long idUser);

    /**
     * Dislike a pic in a twin.
     *
     * @param idTwin to dislike.
     * @param idUser who disliked the twin.
     */
    void dislike(Long idTwin, Long idUser);

    /**
     * Used to return the users currently in the system.
     *
     * @return the number of users.
     */
    Long getUserSize();
}
