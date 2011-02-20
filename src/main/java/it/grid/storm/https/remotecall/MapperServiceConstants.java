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
package it.grid.storm.https.remotecall;


import java.io.File;

/**
 * @author Michele Dibenedetto
 */
public class MapperServiceConstants
{


    public static final String ENCODING_SCHEME = "UTF-8";
    public static final String SERVICE_PATH = File.separator + "gridhttps" + File.separator + "resourceMapping";
    public static final String RESOURCE_MAPPING_PATH_KEY = "path";
    /*
     * Usage samples in an HTTP GET call
     * /SERVICE_PATH?RESOURCE_MAPPING_PATH_KEY=/storage/dteam/file.txt
     * Response sample
     * name=DTEAMT0D1-FS&root=/storage/dteamt0d1&stfnRoot=/dteamt0d1;/dteam:name=ATLAST0D1-FS&root=/storage/atlast0d1&stfnRoot=/atlast0d1
     */
}
