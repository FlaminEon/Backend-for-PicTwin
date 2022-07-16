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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Pic class.
 *
 * @author Cross.
 */
@Entity
@Table(name = "pics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Pic {

    /**
     * The ID of the Pic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * The moment (timestamp) the Pic was saved.
     */
    @Getter
    @Builder.Default
    private ZonedDateTime timestamp = ZonedDateTime.now();

    /**
     * The amount of dislikes a Pic has.
     */
    @Getter
    @Builder.Default
    private Integer dislikes = 0;

    /**
     * The geographical latitude of the Pic.
     */
    @Getter
    private Double latitude;

    /**
     * The geographical longitude of the Pic.
     */
    @Getter
    private Double longitude;

    /**
     * The GPS' error value when the Pic was saved.
     */
    @Getter
    private Double error;

    /**
     * The amount of views a Pic has.
     */
    @Getter
    @Builder.Default
    private Integer views = 0;

    /**
     * The name of the Pic.
     */
    @Getter
    private String name;

    /**
     * The stored picture in Bytes.
     */
    @Getter
    private byte[] picture;

    /**
     * The owner of the pic.
     */
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JsonBackReference
    private User owner;

    /**
     * Increment the amount of dislikes.
     *
     * @return the amount of dislikes.
     */
    public Integer incrementDislikes() {
        this.dislikes++;
        return this.dislikes;
    }

    /**
     * Increment the amount of views.
     *
     * @return the amount of views.
     */
    public Integer incrementViews() {
        this.views++;
        return this.views;
    }
}