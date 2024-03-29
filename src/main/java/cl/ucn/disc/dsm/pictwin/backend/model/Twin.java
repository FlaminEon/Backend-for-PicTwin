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

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Twin created from two Pics.
 *
 * @author Cross.
 */
@Entity
@Table(name = "twins")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Twin {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The dislike indicator.
     */
    @Getter
    @Setter
    @Builder.Default
    private Boolean dislike = Boolean.FALSE;

    /**
     * Pic tagged as "my".
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic my;

    /**
     * Pic tagged as "yours".
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    private Pic yours;

    /**
     * The owner of the twin.
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Getter
    @JsonBackReference
    private User owner;
}
