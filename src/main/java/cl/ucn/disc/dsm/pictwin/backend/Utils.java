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

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The Utils.
 *
 * @author Cross.
 */
@Slf4j
public final class Utils {

    /**
     * Print in log the internal of one object.
     *
     * @param objectName to print.
     * @param o to print.
     */
    public static void printObject(@NonNull String objectName, Object o) {
        if (o != null) {
            log.debug("{}: {}", objectName, ToStringBuilder.reflectionToString(
                    o, RecursiveToStringStyle.MULTI_LINE_STYLE));
        } else {
            log.debug("{}: {}", objectName, null);
        }
    }
}
