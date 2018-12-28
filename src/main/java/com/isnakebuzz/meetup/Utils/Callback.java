package com.isnakebuzz.meetup.Utils;

import java.sql.SQLException;

public interface Callback<T> {
    void done(T value);
}
