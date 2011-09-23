/*
 * Copyright 2011 SUSE Linux Products GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package de.suse.logkeeper.plugins;

import de.suse.logkeeper.service.entities.LogEntry;
import java.util.Properties;

/**
 * STDERR appender.
 * 
 * @author bo
 */
public class STDERRLogger implements LogKeeperBackend {
    private boolean stderr = false;


    /**
     * Setup the backend.
     * 
     * @param definition
     * @param setup
     * @throws LogKeeperBackendException
     */
    public void setup(String definition, Properties setup) throws LogKeeperBackendException {
        this.stderr = setup.getProperty(definition + ".destination", "stderr").toLowerCase().equals("stderr");
    }


    /**
     * Fire the log entry.
     * 
     * @param entry
     * @throws LogKeeperBackendException
     */
    public void log(LogEntry entry) throws LogKeeperBackendException {
        String extmap = entry.renderExtMap();
        if (extmap != null && !extmap.equals("")) {
            extmap = " - (" + extmap + ")";
        }

        String message = entry.getISO8601Timestamp() + " " + entry.getUserName() + ": " + entry.getMessage() + extmap;

        if (this.stderr) {
            System.err.println(message);
        } else {
            System.out.println(message);
        }
    }
}
