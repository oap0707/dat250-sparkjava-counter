package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		counters = new Counters();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		get("/hello", (req, res) -> "Hello World!");
		
        get("/counters", (req, res) -> counters.toJson());
 
        get("/counters/red", (req, res) -> counters.getRed());

        get("/counters/green", (req, res) -> counters.getGreen());

        // TODO: put for green/red and in JSON (done)
        // variant that returns link/references to red and green counter
        put("/counters", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	counters = gson.fromJson(req.body(), Counters.class);
        
            return counters.toJson();
        	
        });

		put("/counters/red", (req,res) -> {

			Gson gson = new Gson();

			counters = gson.fromJson(req.params("red"), Counters.class);

			return counters.toJson();

		});

		put("/counters/green", (req,res) -> {

			Gson gson = new Gson();

			counters = gson.fromJson(req.params("green"), Counters.class);

			return counters.toJson();

		});

    }
    
}
