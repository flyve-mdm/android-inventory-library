/**
 *  LICENSE
 * 
 *  This file is part of Flyve MDM Inventory Library for Android.
 *
 *  Inventory Library for Android is a subproject of Flyve MDM.
 *  Flyve MDM is a mobile device management software.
 * 
 *  Flyve MDM is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  Flyve MDM is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  ---------------------------------------------------------------------
 *  @author    Rafael Hernandez - <rhernandez@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

public class Processes
        extends Category {

    public Processes(String xType, String tagName) {
        super(xType, tagName);
        // TODO review how to get all the processes
/*
        Category c = new Category(mCtx, "PROCESSES");
        ActivityManager  activityManager = (ActivityManager) mFusionApp.getSystemService(Service.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> ps = activityManager.getRunningAppProcesses();
        for(RunningAppProcessInfo process : ps) {
              content.add(new Processes(mFusionApp,process));
          }
          */
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5399654900099889897L;

   

}
