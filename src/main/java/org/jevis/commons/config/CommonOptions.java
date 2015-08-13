/**
 * Copyright (C) 2015 Envidatec GmbH <info@envidatec.com>
 *
 * This file is part of JECommons.
 *
 * JECommons is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation in version 3.
 *
 * JECommons is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * JECommons. If not, see <http://www.gnu.org/licenses/>.
 *
 * JECommons is part of the OpenJEVis project, further project information are
 * published at <http://www.OpenJEVis.org/>.
 */
package org.jevis.commons.config;

import org.jevis.api.JEVisOption;

/**
 * Static references to common options for the JEVisConfigurtaion
 *
 * @author Florian Simon
 */
public interface CommonOptions {

    /**
     * JEVisOptions relatet to the JEVisDataSource
     */
    public interface DataSoure {

        /**
         * Group of all datasource options. Will also be used as prefix for
         * parameters
         */
//        public final String GROUP = "datasource";
        public final JEVisOption DataSoure = OptionFactory.BuildOption(null, "datasource", "", "JEVis datasource option group", true);

        static final JEVisOption HOST = OptionFactory.BuildOption(DataSoure, "host", "openjevis.org", "Hostname URL of the data source", true);
        static final JEVisOption PORT = OptionFactory.BuildOption(DataSoure, "port", "3306", "Port of the data source", true);
        static final JEVisOption CLASS = OptionFactory.BuildOption(DataSoure, "class", "org.jevis.api.sql.JEVisDataSourceSQL", "Classname of the data source. e.g. 'org.jevis.api.sql.JEVisDataSourceSQL'", true);
        static final JEVisOption SCHEMA = OptionFactory.BuildOption(DataSoure, "schema", "jevis", "MySQL Schema of the data source", true);
        static final JEVisOption USERNAME = OptionFactory.BuildOption(DataSoure, "username", "jevis", "Username to the data source.", true);
        static final JEVisOption PASSWORD = OptionFactory.BuildOption(DataSoure, "password", "jevistest", "Password to the data source", true);

    }

    /**
     * JEVisOptions relatet to the JavaFX bases login GUI.
     */
    public interface FXLogin {

        /**
         * Group of all avaFX bases login GUI options. Will also be used as
         * prefix for parameters
         */
        public final JEVisOption FXLogin = OptionFactory.BuildOption(null, "fxlogin", "", "FXLogin option group", false);

        /**
         * Logo displayed into login form.
         */
        static final JEVisOption URL_LOGO = OptionFactory.BuildOption(FXLogin, "logo", "", "URL for the Logo fof the FXLogin", false);
        static final JEVisOption URL_CSS = OptionFactory.BuildOption(FXLogin, "css", "", "URL for the CSS customisaion of the FXLogin", false);
        static final JEVisOption URL_REGISTER = OptionFactory.BuildOption(FXLogin, "register", "", "URL ofthe Reegister link, set 'off' if it should be hinde ", false);

    }

}
