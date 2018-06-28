package com.wecyberstage.wecyberstage.util.character;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CharacterFactory {
    private Map<String, Character4Play> charactersMap = new HashMap<>();
    public enum USER_TYPE {
        UN_REGISTERED, REGISTERED, PLAYER, DIRECTOR
    }

    @Inject
    public CharacterFactory() {
    }

    public Character4Play getCharacter(USER_TYPE userType) {
        Character4Play character = null;
        switch (userType) {
            case UN_REGISTERED:
                if(charactersMap.containsKey(userType.toString())) {
                    character = charactersMap.get(userType.toString());
                } else {
                    character = new UnRegistered();
                    charactersMap.put(userType.toString(), character);
                }
                break;
            case REGISTERED:
                if(charactersMap.containsKey(userType.toString())) {
                    return charactersMap.get(userType.toString());
                } else {
                    character = new Registered();
                    charactersMap.put(userType.toString(), character);
                }
                break;
        }
        return character;
    }
}
