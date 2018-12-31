package com.netflix.handles;

import com.netflix.Netflix;
import com.netflix.entities.Account;

public class PutData {

  DatabaseHandle con = Netflix.database;

  public void registerAccount(Account account) {
    throw new UnsupportedOperationException();
  }
}
