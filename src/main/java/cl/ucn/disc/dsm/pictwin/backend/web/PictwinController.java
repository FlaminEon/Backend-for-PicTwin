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

package cl.ucn.disc.dsm.pictwin.backend.web;

import cl.ucn.disc.dsm.pictwin.backend.Utils;
import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import cl.ucn.disc.dsm.pictwin.backend.model.Twin;
import cl.ucn.disc.dsm.pictwin.backend.model.User;
import cl.ucn.disc.dsm.pictwin.backend.services.PicTwin;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The Controller
 *
 * @author Cross
 */
@RestController
@Slf4j
public class PictwinController {

    /**
     * The PicTwin service
     */
    private final PicTwin picTwin;

    /**
     * The Constructor
     *
     * @param picTwin the service
     */
    @Autowired
    public PictwinController(PicTwin picTwin){
        this.picTwin = picTwin;
    }

    /**
     * Create a user
     *
     * @param user to create
     * @param password to use
     * @return the user
     */
    @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.POST)
    public User create(@Valid @RequestBody User user, @RequestParam String password){

        // Debug
        Utils.printObject("User", user);

        // Call the controller
        return this.picTwin.create(user, password);
    }

    /**
     * Authenticate a user
     *
     * @param email to use
     * @param password to use
     * @return the user
     */
    @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.GET)
    public User authenticate(@RequestParam String email, @RequestParam String password){
        return this.picTwin.authenticate(email, password);
    }

    /**
     * Create a twin
     *
     * @param pic to use
     * @param idUser to use
     * @return the twin
     */
    @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.POST)
    public Twin createTwin(@Valid @RequestBody Pic pic, @RequestParam Long idUser){
        return this.picTwin.createTwin(pic, idUser);
    }

    /**
     * Dislike a twin
     *
     * @param idTwin to use
     * @param idUser to use
     */
    @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.PATCH)
    public void dislike(@RequestParam Long idTwin, @RequestParam Long idUser){
        this.picTwin.dislike(idTwin, idUser);
    }

    /**
     * Show a message in {@link MethodArgumentNotValidException}
     *
     * @param ex to catch
     * @return the map of errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String > errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Show a message in {@link IllegalArgumentException}
     *
     * @param ex to catch
     * @return the message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidationExceptions(IllegalArgumentException ex){
        return ex.getMessage();
    }
}
