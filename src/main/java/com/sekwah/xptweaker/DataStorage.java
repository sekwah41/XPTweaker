package com.sekwah.xptweaker;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;

public class DataStorage {

    private Gson gson;

    public DataStorage() {
        gson = new Gson();
    }

    public InputStream loadResource(String location) {
        File inFile = new File(XPTweaker.configFolder, location);
        if (inFile.exists() && !inFile.isDirectory()) {
            try {
                return new FileInputStream(inFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                copyDefaultFile(location, false);
                return this.getClass().getClassLoader().getResourceAsStream(location);
            } catch (NullPointerException e) {
                e.printStackTrace();
                XPTweaker.logger.error("Could not load " + location + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return null;
            }
        }
    }

    public void storeJson(Object dataHolder, String location) {
        String json = gson.toJson(dataHolder);
        try {
            FileWriter fileWriter = new FileWriter(new File(XPTweaker.configFolder, location));
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T loadJson(Type dataHolder, String location) {
        InputStream jsonResource = this.loadResource(location);
        if(jsonResource == null) {
            return null;
        }
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(jsonResource));
        T object = gson.fromJson(bufReader, dataHolder);
        return object;
    }

    public boolean copyDefaultFile(String fileLoc, boolean overwrite) {
        File outFile = new File(XPTweaker.configFolder, fileLoc);
        if (!outFile.exists()) {
            outFile.getParentFile().mkdirs();
        }
        if (!outFile.exists() || overwrite) {
            try {
                InputStream inputStream = XPTweaker.class.getResourceAsStream("/assets/xptweaker/xpdata/" + fileLoc);
                if(inputStream == null) {
                    return false;
                }

                FileOutputStream outStream = new FileOutputStream(outFile);

                byte[] buf = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    outStream.write(buf, 0, len);
                }
                inputStream.close();
                outStream.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                XPTweaker.logger.error("Could not load " + fileLoc + ". The file does" +
                        "not exist or there has been an error reading the file.");
                return false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                XPTweaker.logger.error("Could not create " + fileLoc);
            } catch (IOException e) {
                e.printStackTrace();
                XPTweaker.logger.error("File error reading " + fileLoc);
            }
        }
        return true;
    }

}
