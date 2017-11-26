package com.petrushin.task3.service;

import com.petrushin.task3.domain.Lot;
import com.petrushin.task3.domain.User;

public interface UserService {

    void makeBetForLot(User user, Lot lot);

}
