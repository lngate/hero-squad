package ke.co.safaricom.Models;


import ke.co.safaricom.Models.Hero;

import java.util.ArrayList;
import java.util.List;

public class Squad {
        private String name;
        private int maxSize;
        private String cause;
        private List<Hero> heroes;

        // Constructors, getters, and setters

        // Example constructor
        public Squad(String name, int maxSize, String cause) {
            this.name = name;
            this.maxSize = maxSize;
            this.cause = cause;
            this.heroes = new ArrayList<>();
        }

        public boolean addHeroToSquad(Hero hero) {
            if (heroes.size() < maxSize) {
                heroes.add(hero);
                return true;
            }
            return false;
        }
    }

