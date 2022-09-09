package kito.lab5.server.utils;


import kito.lab5.common.util.Response;

public class ResponseHandler {

    public static void handleResponse(Response response) {
        if (response.getMessage() != null) {
            System.out.println(response.getMessage());
        }
        if (response.getHuman().size() > 0) {
            for (int i = 0; i < response.getHuman().size(); i++) {
                System.out.println(response.getHuman().get(i).toString());
            }
        }

        if (response.getStrings().size() > 0) {
            for (int i = 0; i < response.getStrings().size(); i++) {
                System.out.println(response.getStrings().get(i));
            }
        }
    }
}
