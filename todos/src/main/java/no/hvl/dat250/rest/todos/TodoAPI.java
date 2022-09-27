package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static spark.Spark.*;


/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        final TodoDAO todosDAO = new TodoDAO();

        after((req, res) -> res.type("application/json"));

        // TODO: Implement API, such that the testcases succeed.

        //create(post) todos
        post("/todos", (req, res) -> {
            res.type("application/json");

            Todo todo = new Gson().fromJson(req.body(), Todo.class);

            todo = todosDAO.create(todo);

            return new Gson().toJson(todo);

        });

        //read(get) all todos
        get("/todos", (req, res) -> {
            res.type("application/json");
            return new Gson().toJson(todosDAO.all());
        });

        //read(get) one todo by id
        get("/todos/:id", (req, res) -> {
            res.type("application/json");

            String id = req.params(":id");

            if(isNumeric(id)) {
                Todo todo = todosDAO.read(Long.valueOf(id));
                if (todo != null) {
                    return new Gson().toJson(todo);
                } else {
                    return String.format("Todo with the id  \"%s\" not found!", id);
                }
            }
            else {
                return String.format("The id \"%s\" is not a number!", id);
            }
        });

        put("/todos/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");

            if(isNumeric(id)) {

                Todo toEdit = new Gson().fromJson(req.body(), Todo.class);
                Todo editedUser = todosDAO.update(toEdit, Long.parseLong(id));

                if (editedUser != null) {
                    return new Gson().toJson(editedUser);
                } else {
                    return new Gson().toJson("User not found or error in edit");
                }
            }
            else {
                return String.format("The id \"%s\" is not a number!", id);
            }

        });

        //delete(delete) todo by id
        delete("/todos/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params(":id");

            if(isNumeric(id)){
                todosDAO.delete(Long.valueOf(id));
                return new Gson().toJson("Todo deleted successfully");
            }
            else{
                return String.format("The id \"%s\" is not a number!", id);
            }

        });
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

}
