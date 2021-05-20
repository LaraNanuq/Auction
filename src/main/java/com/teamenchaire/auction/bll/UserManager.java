package com.teamenchaire.auction.bll;

import com.teamenchaire.auction.bo.User;

public final class UserManager {
    public User getUser(Integer id) {
        return new User(id, "surnom", "nom", "prénom", "mail", "mdp", "tel", "rue", "code", "ville", 0, false);
    }
}
