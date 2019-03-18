package com.gcats.cats.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import au.com.bytecode.opencsv.CSVReader;
import com.gcats.cats.model.User;

public class CsvReader
{
    private final int email = 0;
    private final int password = 1;
    private final int role = 2;

    @SuppressWarnings("resource")
    public Vector<User> read(File file) throws Exception
    {
        Vector<User> users = new Vector<User>();
        CSVReader reader = new CSVReader(new FileReader(file));

        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                User user = new User();
                user.setEmail(nextLine[email]);
                user.setPassword(nextLine[password]);
                user.setRole(nextLine[role]);
                user.setActive(1);
                users.add(user);            }
        }
        return users;
    }
}