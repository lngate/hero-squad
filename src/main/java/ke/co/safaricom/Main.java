package ke.co.safaricom;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ke.co.safaricom.Models.Hero;
import ke.co.safaricom.Models.Squad;


public class Main {

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        Spark.port(getHerokuAssignedPort());

        // Example squads
        Squad justiceLeague = new Squad("Justice League", 5, "Fighting injustice");
        Squad avengers = new Squad("Avengers", 4, "Saving the world");

        // Route to display squads and heroes
        Spark.get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", List.of(justiceLeague, avengers));
            return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/index.vm"));
        });

        // Route to display a form for adding heroes to squads
        Spark.get("/add-hero", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("squads", List.of(justiceLeague, avengers));
            return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/add-hero.vm"));
        });

        // Route to handle form submission and add hero to squad
        Spark.post("/add-hero", (req, res) -> {
            String heroName = req.queryParams("heroName");
            int heroAge = Integer.parseInt(req.queryParams("heroAge"));
            String specialPower = req.queryParams("specialPower");
            String weakness = req.queryParams("weakness");
            String squadName = req.queryParams("squadName");

            Hero hero = new Hero(heroName, heroAge, specialPower, weakness);

            // Add hero to the selected squad
            if ("Justice League".equals(squadName)) {
                justiceLeague.addHeroToSquad(hero);
            } else if ("Avengers".equals(squadName)) {
                avengers.addHeroToSquad(hero);
            }

            res.redirect("/");
            return "";
        });
    }

    // Helper method to get the assigned port on Heroku
    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // Default port if not on Heroku
    }
}
