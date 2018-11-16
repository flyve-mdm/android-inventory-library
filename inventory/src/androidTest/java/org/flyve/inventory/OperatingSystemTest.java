package org.flyve.inventory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.OperatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

/*
 *   Copyright © 2017-2018 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android
 *
 * flyve-mdm-android is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Ivan Del Pino
 * @email     idelpino@teclib.com
 * @date      17/9/2018
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/android-inventory-library
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

@RunWith(AndroidJUnit4.class)
public class OperatingSystemTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getBootTime() {
        assertNotEquals("", new OperatingSystem(appContext).getBootTime());
    }

    @Test
    public void getKernelVersion() {
        assertNotEquals("", new OperatingSystem(appContext).getKernelVersion());
    }

    @Test
    public void getTimeZoneShortName() {
        assertNotEquals("", new OperatingSystem(appContext).getTimeZoneShortName());
    }

    @Test
    public void getCurrentTimezoneOffset() {
        assertNotEquals("", new OperatingSystem(appContext).getCurrentTimezoneOffset());
    }

    @Test
    public void getSSHKey() {
        assertNotEquals("", new OperatingSystem(appContext).getSSHKey());
    }
}