/*
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2006-2010.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.grid.storm.https.configuration;


import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Michele Dibenedetto
 */
public class Configurator
{


    private static Logger logger = LoggerFactory.getLogger(Configurator.class);
    /**
	 * 
	 */
    private static PropertiesConfiguration config = null;

    /**
     * @param propertiesFile
     * @throws ConfigurationException
     */
    public static void init(String propertiesFilePath) throws ConfigurationException
    {
        logger.debug("Configurator initialization from file " + propertiesFilePath);
        File propertiesFile = new File(propertiesFilePath);
        if (!propertiesFile.exists())
        {
            logger.error("The configuration file " + propertiesFile.getAbsolutePath() + " don't exists!");
            throw new ConfigurationException("The configuration file " + propertiesFile.getAbsolutePath() + " don't exists!");
        }
        config = new PropertiesConfiguration(propertiesFile);
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
        logger.debug("Testing the file for all required parameters");
        try
        {
            getServerUserUID();
            getServerUserGID();
        }
        catch (ConfigurationException e)
        {
            logger.error("Unable to find all required configuration parameters in the provided configuration file : " + e.getMessage());
            ConfigurationException exception = new ConfigurationException("Unable to find all required "
                    + "configuration parameters in the provided configuration file : " + e.getMessage());
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
        logger.debug("Configurator initialization completed");
    }


    /**
     * @throws ConfigurationException
     */
    private static void tryReinit() throws ConfigurationException
    {
        if (config != null)
        {
            if (config.getReloadingStrategy().reloadingRequired())
            {
                logger.info("Reinitializing the MyConfigurator from the stored configuration file " + config.getFile().getAbsolutePath());
                init(config.getFile().getAbsolutePath());
            }
        }
    }

    /**
     * @return
     * @throws ConfigurationException
     */
    public static int getServerUserUID() throws ConfigurationException
    {
        logger.debug("Requested the retrieving of the server user UID");
        Integer uid = null;
        if (config != null)
        {
            tryReinit();
            uid = getIntProperty("gridhttps.server.user.uid");
            if (uid < 0)
            {
                logger.error("Unable to retrieve a correct server user UID from the configuration file \'" + config.getFileName()
                        + "\'");
                throw new ConfigurationException("Unable to retrieve a correct server user UID from the configuration file \'"
                        + config.getFileName() + "\'");
            }
        }
        else
        {
            logger.error("Unable to retrieve the server user UID, Configurator"
                    + " not initialized! Do Configurator.init(File propertiesFile) first");
            throw new ConfigurationException("Unable to retrieve the server user UID, Configurator not"
                    + " initialized! Do Configurator.init(File propertiesFile) first");
        }
        return uid.intValue();
    }
    
    /**
     * @return
     * @throws ConfigurationException
     */
    public static int getServerUserGID() throws ConfigurationException
    {
        logger.debug("Requested the retrieving of the server user GID");
        Integer gid = null;
        if (config != null)
        {
            tryReinit();
            gid = getIntProperty("gridhttps.server.user.gid");
            if (gid < 0)
            {
                logger.error("Unable to retrieve a correct server user GID from the configuration file \'" + config.getFileName()
                        + "\'");
                throw new ConfigurationException("Unable to retrieve a correct server user GID from the configuration file \'"
                        + config.getFileName() + "\'");
            }
        }
        else
        {
            logger.error("Unable to retrieve the server user GID, Configurator"
                    + " not initialized! Do Configurator.init(File propertiesFile) first");
            throw new ConfigurationException("Unable to retrieve the server user GID, Configurator not"
                    + " initialized! Do Configurator.init(File propertiesFile) first");
        }
        return gid.intValue();
    }

    /**
     * Retrieves the value of the property specified as a String
     * 
     * @param property
     * @return
     * @throws ConfigurationException
     */
    private static String getStringProperty(String property) throws ConfigurationException
    {
        String value = null;
        if (config.containsKey(property))
        {
            try
            {
                value = config.getString(property);
            }
            catch (ConversionException e)
            {
                logger.error("The string property " + property + " is not correctly specified. ConversionException " + e.getMessage());
                throw new ConfigurationException("The string property " + property + " is not correctly specified");
            }
        }
        if (value == null || value.trim().equals(""))
        {
            logger.debug("No " + property + " specified in the configuration file \'" + config.getFileName() + "\'.");
            value = null;
        }
        else
        {
            logger.debug("The specified value for property " + property + " is " + value);
        }
        return value;
    }


    /**
     * Retrieves the value of the property specified as a List of String
     * 
     * @param property
     * @return
     * @throws ConfigurationException
     */
    private static List<String> getStringListProperty(String property) throws ConfigurationException
    {
        String[] value = null;
        List<String> list = null;
        if (config.containsKey(property))
        {
            try
            {
                value = config.getStringArray(property);
            }
            catch (ConversionException e)
            {
                logger.error("The string property " + property + " is not correctly specified");
                throw new ConfigurationException("The string property " + property + " is not correctly specified");
            }
        }
        list = new LinkedList<String>();
        if (value == null)
        {
            logger.info("No " + property + " specified in the configuration file \'" + config.getFileName() + "\'. Returning an empty list");
        }
        else
        {
            logger.debug("The specified value for property " + property + " is " + value);
            list = new LinkedList<String>();
            for (String element : value)
            {
                list.add(element);
            }
        }
        return list;
    }


    /**
     * Retrieves the value of the property specified as a long
     * 
     * @param property
     * @return
     * @throws ConfigurationException
     */
    private static int getIntProperty(String property) throws ConfigurationException
    {
        int value = -1;
        if (config.containsKey(property))
        {
            try
            {
                Object propertyValue = config.getProperty(property);
                if (propertyValue.getClass().equals(java.lang.String.class)
                        && (((String) propertyValue).trim().length() == 0 || ((String) propertyValue).trim().equals("")))
                {
                    logger.debug("The numeric string provided by user is an empty string");
                }
                else
                {
                    value = config.getInt(property);
                }
            }
            catch (ConversionException e)
            {
                logger.error("The int property " + property + " is not correctly specified. ConversionException : " + e.getMessage());
                throw new ConfigurationException("The int property " + property + " is not correctly specified");
            }
        }
        if (value < 0)
        {
            logger.info("No " + property + " specified in the configuration file \'" + config.getFileName() + "\'.");
        }
        else
        {
            logger.debug("The specified value for property " + property + " is " + value);
        }
        return value;
    }
}
