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

package cl.ucn.disc.dsm.pictwin.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * The User of the application.
 *
 * @author Cross.
 */
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The user's email.
     */
    @Getter
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * The user's amount of strikes.
     */
    @Getter
    private Integer strikes;

    /**
     * The user's hashed password.
     */
    @Getter
    @Setter
    private String password;

    /**
     * The Twins.
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    @JsonManagedReference
    private List<Twin> twins = new ArrayList<>();

    /**
     * The State.
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Getter
    @Setter
    private State state = State.ACTIVE;

    /**
     * Increment the amount of strikes.
     *
     * @return the number of strikes.
     */
    public Integer incrementStrikes() {
        this.strikes++;
        return this.strikes;
    }

    /**
     * Insert a twin into the list.
     *
     * @param twin to add.
     */
    public void add(final Twin twin) {
        this.twins.add(twin);
    }
}
